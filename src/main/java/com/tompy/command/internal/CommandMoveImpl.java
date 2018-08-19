package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.adventure.internal.AdventureUtils;
import com.tompy.area.api.Area;
import com.tompy.area.api.Exit;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.directive.Direction;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;

public class CommandMoveImpl extends CommandBasicImpl implements Command {
    protected Direction direction = Direction.DIRECTION_NORTH;

    private CommandMoveImpl(CommandType type, String dir) {
        super(type != null ? type : CommandType.COMMAND_MOVE);
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
        List<Response> returnValue = new ArrayList<>();
        Area currentArea = player.getArea();
        Exit targetExit = currentArea.getExitForDirection(direction);

        returnValue.add(
                responseFactory.createBuilder().text(type.getParticiple() + " " + direction.getDescription()).source(
                        type.getDescription()).build());

        if (null != targetExit) {
            if (targetExit.isOpen()) {
                returnValue.add(responseFactory.createBuilder().text("Success").source(type.getDescription()).build());
                returnValue.addAll(currentArea.exit(targetExit.getDirection(), player, adventure));
                returnValue.addAll(targetExit.passThru());
                returnValue.addAll(
                        targetExit.getArea().enter(AdventureUtils.getOppositeDirection(targetExit.getDirection()),
                                player, adventure));
                player.setArea(targetExit.getArea());
            } else {
                returnValue.add(responseFactory.createBuilder().text("Failure").source(type.getDescription()).build());
                returnValue.addAll(currentArea.exit(targetExit.getDirection(), player, adventure));
            }
        } else {
            returnValue.add(
                    responseFactory.createBuilder().text("Can't move that way").source(type.getDescription()).build());
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
            return new CommandMoveImpl(type, dir);
        }
    }
}
