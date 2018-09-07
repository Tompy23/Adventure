package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.CommandType;
import com.tompy.entity.api.EntityService;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CommandSearchInImpl extends CommandSearchImpl {
    private static final Logger LOGGER = LogManager.getLogger(CommandSearchInImpl.class);

    public CommandSearchInImpl(CommandType type, EntityService entityService, String target, String secondaryTarget) {
        super(type, entityService, target, secondaryTarget);
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Search In");
        List<Response> returnValue = new ArrayList<>();

        returnValue
            .add(responseFactory.createBuilder().text("not implemented").source(this.type.getDescription()).build());

        return returnValue;
    }
}