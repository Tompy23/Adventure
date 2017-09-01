package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.adventure.internal.AdventureUtils;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;

public class CommandSearchImpl extends CommandBasicImpl implements Command {
    protected String target;
    protected String secondaryTarget;

    protected CommandSearchImpl(CommandType type, String target, String secondaryTarget) {
        this.type = type;
        this.target = target;
        this.secondaryTarget = secondaryTarget;
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        returnValue.addAll(player.getArea().search(player, adventure));

        return returnValue;
    }

    public static CommandBuilderFactory createBuilderFactory() { return CommandSearchImpl::createBuilder; }

    public static CommandBuilder createBuilder() {
        return new CommandSearchImpl.CommandSearchBuilderImpl();
    }

    public static final class CommandSearchBuilderImpl implements CommandBuilder {
        private CommandType type = null;
        private String target = null;
        private String secondaryTarget = null;

        @Override
        public Command build() {
            switch (type) {
                case COMMAND_SEARCH_DIRECTION:
                    return new CommandSearchDirectionImpl(type, target, secondaryTarget);
                case COMMAND_SEARCH_IN:
                    return new CommandSearchInImpl(type, target, secondaryTarget);
                case COMMAND_SEARCH_ON:
                    return new CommandSearchOnImpl(type, target, secondaryTarget);
                case COMMAND_SEARCH_FEATURE:
                    return new CommandSearchFeatureImpl(type, target, secondaryTarget);
                default:
                    return new CommandSearchImpl(type, target, secondaryTarget);
            }
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            if (parts.length > 1) {
                if(AdventureUtils.isDirection(parts[1])) {
                    target = parts[1];
                    type = CommandType.COMMAND_SEARCH_DIRECTION;
                }
                else if (parts.length > 3 && parts[2].equals("ON")) {
                    target = parts[1];
                    secondaryTarget = parts[3];
                    type = CommandType.COMMAND_SEARCH_ON;
                }
                else if (parts.length > 2 && parts[1].equals("IN")) {
                    target = parts[2];
                    type = CommandType.COMMAND_SEARCH_IN;
                }
                else {
                    target = parts[1];
                    type = CommandType.COMMAND_SEARCH_FEATURE;
                }
            }
            else {
                type = CommandType.COMMAND_SEARCH;
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
