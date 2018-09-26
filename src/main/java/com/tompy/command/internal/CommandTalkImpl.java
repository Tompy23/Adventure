package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.entity.api.EntityService;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.List;
import java.util.Objects;

import static com.tompy.directive.CommandType.COMMAND_TALK;

public class CommandTalkImpl extends CommandBasicImpl implements Command {
    private final String target;

    protected CommandTalkImpl(CommandType type, String target, EntityService entityService) {
        super(type, entityService);
        this.target = Objects.requireNonNull(target, "Target cannot be null.");
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandTalkImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandTalkImpl.CommandTalkBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        //TODO Starts an encounter with the target
        // This is done by firing an "Encounter" type action event
        // The event will then change the state appropriately and handle any other setup
        return null;
    }

    public static final class CommandTalkBuilderImpl extends CommandBuilderImpl {
        private String target;

        @Override
        public CommandBuilder parts(String[] parts) {
            return null;
        }

        @Override
        public Command build() {
            return new CommandTalkImpl(COMMAND_TALK, target, entityService);
        }
    }
}