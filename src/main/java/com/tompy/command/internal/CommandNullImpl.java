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

public class CommandNullImpl extends CommandBasicImpl implements Command {

    private CommandNullImpl() {
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        returnValue.add(responseFactory.createBuilder().text("I do not understand").source("Unknown").build());
        return returnValue;
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandNullImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandNullImpl.CommandNullBuilderImpl();
    }

    public static final class CommandNullBuilderImpl implements CommandBuilder {

        @Override
        public CommandBuilder parts(String[] parts) {
            return this;
        }

        @Override
        public CommandBuilder type(CommandType type) {
            return this;
        }

        @Override
        public Command build() {
            return new CommandNullImpl();
        }
    }
}
