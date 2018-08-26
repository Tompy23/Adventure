package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.item.api.Item;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandTakeImpl extends CommandBasicImpl implements Command {
    private final String target;

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
        List<Response> returnValue = new ArrayList<>();
        List<Item> items = player.getArea().getAllItems();
        Long objectKey = EntityUtil.findEntityByDescription(items, target, adventure.getUI());
        Item object = items.stream().filter((i) -> i.getKey().equals(objectKey)).findFirst().get();
        if (object != null && player.addItem(object)) {
            player.getArea().removeItem(object);
            returnValue.add(responseFactory.createBuilder().source("CommandTake").text(
                    String.format("%s is now in %s's inventory", object.getName(), player.getName())).build());
        } else {
            returnValue.add(responseFactory.createBuilder().source("CommandTake").text(
                    String.format("%s is not in %s's inventory", target, player.getName())).build());
        }

        return returnValue;
    }

    public static final class CommandTakeBuilderImpl extends CommandBuilderImpl {
        private String target;

        @Override
        public Command build() {
            return new CommandTakeImpl(type, entityService, target);
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            this.target = parts[1];
            return this;
        }
    }
}
