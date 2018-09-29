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

public class CommandInventoryImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandInventoryImpl.class);

    private CommandInventoryImpl(CommandType type, EntityService entityService) {
        super(type, entityService);
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandInventoryImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandInventoryBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Command Inventory");
        List<Response> returnValue = new ArrayList<>();

        if (!player.getInventory().isEmpty()) {
            player.getInventory().stream().forEach((i) -> returnValue
                    .add(responseFactory.createBuilder().source(player.getName()).text(i.getDescription()).build()));
        } else {
            returnValue.add(responseFactory.createBuilder().source(player.getName()).text("Inventory Empty").build());
        }
        returnValue.add(responseFactory.createBuilder().source(player.getName())
                .text(String.format("$%d", player.moneyValue())).build());

        return returnValue;
    }

    public static final class CommandInventoryBuilderImpl extends CommandBuilderImpl {

        @Override
        public CommandBuilder parts(String[] parts) {
            return this;
        }

        @Override
        public Command build() {
            return new CommandInventoryImpl(type, entityService);
        }
    }
}
