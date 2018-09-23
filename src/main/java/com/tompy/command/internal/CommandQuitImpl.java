package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.entity.api.EntityService;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CommandQuitImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandQuitImpl.class);

    private CommandQuitImpl(EntityService entityService) {
        super(CommandType.COMMAND_QUIT, entityService);
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandQuitImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandQuitImpl.CommandQuitBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Command Quit");
        List<Response> returnValue = new ArrayList<>();

        returnValue.add(responseFactory.createBuilder().source(type.getDescription()).text("Goodbye").build());

        adventure.stop();

        return returnValue;
    }

    public static final class CommandQuitBuilderImpl extends CommandBuilderImpl {
        @Override
        public Command build() {
            return new CommandQuitImpl(entityService);
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
