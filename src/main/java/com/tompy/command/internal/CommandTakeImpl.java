package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
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
        super(type, entityService);
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
        //TODO Convert target to an entity and then place in player's inventory.
        // This means that "take" does not equip and does not change the currently equipped item.
        // I like this because it is nice and simple.
        Item object = null;
        // Convert target to object
        if (player.addItem(object)) {
            returnValue.add(responseFactory.createBuilder().source("CommandTake").text(
                    String.format("%s is now in %s's inventory", target, player.getName())).build());
        } else {
            returnValue.add(responseFactory.createBuilder().source("CommandTake").text(
                    String.format("%s is not in %s's inventory", target, player.getName())).build());
        }
        return null;
    }

    public static final class CommandTakeBuilderImpl implements CommandBuilder {
        private String target;

        @Override
        public Command build() {
            return null;
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            return null;
        }

        @Override
        public CommandBuilder type(CommandType type) {
            return null;
        }

        @Override
        public CommandBuilder entityService(EntityService entityService) {
            return null;
        }
    }
}
