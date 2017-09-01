package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.area.api.Area;
import com.tompy.area.api.Exit;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.directive.Direction;
import com.tompy.adventure.internal.AdventureUtils;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;

public class CommandMoveImpl extends CommandBasicImpl implements Command {
    protected Direction direction = Direction.DIRECTION_NORTH;

    private CommandMoveImpl(CommandType type, String dir) {
        if (null != type) {
            this.type = type;
        }
        else {
            this.type = CommandType.COMMAND_MOVE;
        }
        if (AdventureUtils.isDirection(dir)) {
            direction = AdventureUtils.getDirection(dir);
        }
        else {
            direction = null;
        }
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        Area currentArea = player.getArea();
        Exit targetExit = currentArea.getExitForDirection(direction);

        returnValue.add(responseFactory.createBuilder().text(type.getParticiple() + " " + direction.getDescription()).source(type.getDescription()).build());

        if (null != targetExit) {
            returnValue.add(responseFactory.createBuilder().text("Success").source(type.getDescription()).build());
            returnValue.addAll(currentArea.exit(targetExit.getDirection(), player, adventure));
            returnValue.addAll(targetExit.passThru());
            returnValue.addAll(targetExit.getArea().enter(AdventureUtils.getOppositeDirection(targetExit.getDirection()), player, adventure));
            player.setArea(targetExit.getArea());
        }
        else {
            returnValue.add(responseFactory.createBuilder().text("Can't move that way").source(type.getDescription()).build());
        }

        return returnValue;
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandMoveImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandMoveBuilderImpl();
    }

    public static final class CommandMoveBuilderImpl implements CommandBuilder {
        private String dir;
        private CommandType type;

        @Override
        public CommandBuilder parts(String[] parts) {
            this.dir = parts[1];
            return this;
        }

        @Override
        public CommandBuilder type(CommandType type) {
            this.type = type;
            return this;
        }

        public Command build() {
            return new CommandMoveImpl(type, dir);
        }
    }
}
