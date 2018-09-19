package com.tompy.adventure.internal;

import com.tompy.directive.CommandType;
import com.tompy.directive.Direction;
import com.tompy.directive.EventType;

import java.util.HashMap;
import java.util.Map;

public class AdventureUtils {
    private static Map<String, Direction> directionMap = new HashMap<>();
    private static Map<String, CommandType> commandTypeMap = new HashMap<>();

    static {
        for (Direction d : Direction.values()) {
            directionMap.put(d.getDescription().toUpperCase(), d);
        }
        for (CommandType ct : CommandType.values()) {
            commandTypeMap.put(ct.getDescription().toUpperCase(), ct);
        }
    }

    public static boolean isDirection(String dir) {
        return (directionMap.containsKey(dir.toUpperCase()));
    }

    public static Direction getDirection(String dir) {
        return directionMap.get(dir.toUpperCase());
    }

    public static CommandType getCommandType(String type) {
        return commandTypeMap.get(type.toUpperCase());
    }

    public static Direction getOppositeDirection(Direction direction) {
        Direction returnValue = null;

        switch (direction) {
            case DIRECTION_NORTH:
                returnValue = Direction.DIRECTION_SOUTH;
                break;
            case DIRECTION_EAST:
                returnValue = Direction.DIRECTION_WEST;
                break;
            case DIRECTION_SOUTH:
                returnValue = Direction.DIRECTION_NORTH;
                break;
            case DIRECTION_WEST:
                returnValue = Direction.DIRECTION_EAST;
                break;
        }

        return returnValue;
    }

    public static EventType getAreaSearchEventType(Direction direction) {
        switch (direction) {
            case DIRECTION_NORTH:
                return EventType.AREA_NORTH_SEARCH;
            case DIRECTION_EAST:
                return EventType.AREA_EAST_SEARCH;
            case DIRECTION_SOUTH:
                return EventType.AREA_SOUTH_SEARCH;
            case DIRECTION_WEST:
                return EventType.AREA_WEST_SEARCH;
            default:
                return null;
        }
    }

    public static EventType getAreaExitEventType(Direction direction) {
        switch (direction) {
            case DIRECTION_NORTH:
                return EventType.AREA_EXIT_NORTH;
            case DIRECTION_EAST:
                return EventType.AREA_EXIT_EAST;
            case DIRECTION_SOUTH:
                return EventType.AREA_EXIT_SOUTH;
            case DIRECTION_WEST:
                return EventType.AREA_EXIT_WEST;
            default:
                return null;
        }
    }

    public static EventType getAreaEnterEventType(Direction direction) {
        switch (direction) {
            case DIRECTION_NORTH:
                return EventType.AREA_ENTER_NORTH;
            case DIRECTION_EAST:
                return EventType.AREA_ENTER_EAST;
            case DIRECTION_SOUTH:
                return EventType.AREA_ENTER_SOUTH;
            case DIRECTION_WEST:
                return EventType.AREA_ENTER_WEST;
            default:
                return null;
        }
    }
}
