package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.adventure.internal.AdventureUtils;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.directive.Direction;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.area.api.Area;
import com.tompy.exit.api.Exit;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CommandMoveImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandMoveImpl.class);
    protected Direction direction = Direction.DIRECTION_NORTH;

    private CommandMoveImpl(CommandType type, String dir, EntityService entityService) {
        super(type != null ? type : CommandType.COMMAND_MOVE, entityService);
        if (AdventureUtils.isDirection(dir)) {
            direction = AdventureUtils.getDirection(dir);
        } else {
            direction = null;
        }
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandMoveImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandMoveBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Command Move.  direction: {}", direction.getDescription());
        List<Response> returnValue = new ArrayList<>();
        Area currentArea = player.getArea();
        Exit targetExit = currentArea.getExitForDirection(direction);

        if (direction == null) {
            returnValue.add(responseFactory.createBuilder().source("MOVE").text("Unknown direction").build());
        }

        if (null != targetExit) {
            if (targetExit.isOpen()) {
                Area targetArea = targetExit.getConnectedArea(currentArea);
                returnValue.addAll(currentArea.exit(direction, player, adventure));
                returnValue.addAll(targetExit.passThru(direction));
                player.setArea(targetArea);
                returnValue.addAll(targetArea.enter(AdventureUtils.getOppositeDirection(direction), player, adventure));
            }
        }

        return returnValue;
    }

    public static final class CommandMoveBuilderImpl extends CommandBuilderImpl {
        private String dir;

        @Override
        public CommandBuilder parts(String[] parts) {
            this.dir = parts[1];
            return this;
        }

        public Command build() {
            return new CommandMoveImpl(type, dir, entityService);
        }
    }
}
