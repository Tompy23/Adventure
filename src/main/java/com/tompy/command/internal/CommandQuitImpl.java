package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;

public class CommandQuitImpl extends CommandBasicImpl implements Command {

    private CommandQuitImpl() {
        super(CommandType.COMMAND_QUIT);
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandQuitImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandQuitImpl.CommandQuitBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        returnValue.add(responseFactory.createBuilder().source(type.getDescription()).text("Goodbye").build());

        adventure.stop(player);

        return returnValue;
    }

    public static final class CommandQuitBuilderImpl extends CommandBuilderImpl {
        @Override
        public Command build() {
            return new CommandQuitImpl();
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            return this;
        }

        @Override
        public CommandBuilder type(CommandType type) {
            return this;
        }
    }
}