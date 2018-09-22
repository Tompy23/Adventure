package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.item.api.Item;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CommandTakeImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandTakeImpl.class);
    protected final String target;

    protected CommandTakeImpl(CommandType type, EntityService entityService, String target) {
        super(type != null ? type : CommandType.COMMAND_TAKE, entityService);
        this.target = Objects.requireNonNull(target, "Target cannot be null.");
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandTakeImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandTakeImpl.CommandTakeBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Command Take");
        List<Response> returnValue = new ArrayList<>();

        Optional<Item> optObject =
            EntityUtil.findItemByDescription(player.getArea().getAllItems(), target, adventure.getUI());

        if (optObject.isPresent()) {
            Item object = optObject.get();
            if (entityService.is(object, Attribute.VISIBLE)) {
                if (player.addItem(object)) {
                    player.getArea().removeItem(object);
                    returnValue.add(responseFactory.createBuilder().source("CommandTake")
                        .text(String.format("%s is now in %s's inventory", object.getDescription(), player.getName()))
                        .build());
                } else {
                    // TODO Inventory full?  Or some other issue?
                    returnValue
                        .add(responseFactory.createBuilder().source("CommandTake").text("Inventory full.").build());
                }
            }
        } else {
            returnValue.add(responseFactory.createBuilder().source("CommandTake")
                .text(String.format("%s is not in %s's inventory", target.toLowerCase(), player.getName())).build());
        }

        return returnValue;
    }

    public static final class CommandTakeBuilderImpl extends CommandBuilderImpl {
        private String item;
        private String target;

        @Override
        public Command build() {
            switch (type) {
                case COMMAND_TAKE_FROM:
                    return new CommandTakeFromImpl(type, entityService, item, target);
                default:
                    return new CommandTakeImpl(type, entityService, target);
            }
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            int from = -1;
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equalsIgnoreCase("from")) {
                    from = i;
                    break;
                }
            }
            StringBuilder itemSb = new StringBuilder();
            StringBuilder targetSb = new StringBuilder();
            if (from > 0) {
                for (int i = 1; i < parts.length; i++) {
                    if (i < from) {
                        itemSb.append(parts[i] + " ");
                    } else if (i > from) {
                        targetSb.append(parts[i] + " ");
                    }
                }
                item = itemSb.toString().trim();
                target = targetSb.toString().trim();
                type = CommandType.COMMAND_TAKE_FROM;
            } else {
                for (int i = 1; i < parts.length; i++) {
                    targetSb.append(parts[i] + " ");
                }
                target = targetSb.toString().trim();
                type = CommandType.COMMAND_TAKE;
            }
            return this;
        }

        @Override
        public CommandBuilder type(CommandType type) {
            // type is defined when parsing parts
            return this;
        }
    }
}
