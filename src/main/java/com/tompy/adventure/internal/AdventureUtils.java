package com.tompy.adventure.internal;

import com.tompy.attribute.api.Attribute;
import com.tompy.directive.CommandType;
import com.tompy.directive.Direction;
import com.tompy.directive.EventType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdventureUtils {
    private static Map<String, Direction> directionMap = new HashMap<>();
    private static Map<String, CommandType> commandTypeMap = new HashMap<>();
    private static Map<String, Attribute> attributeMap = new HashMap<>();

    static {
        for (Direction d : Direction.values()) {
            directionMap.put(d.getDescription().toUpperCase(), d);
        }
        for (CommandType ct : CommandType.values()) {
            commandTypeMap.put(ct.getDescription().toUpperCase(), ct);
        }
        for (Attribute a : Attribute.values()) {
            attributeMap.put(a.getName().toUpperCase(), a);
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

    public static Attribute getAttribute(String name) {
        return attributeMap.get(name.toUpperCase());
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
                return EventType.EVENT_AREA_NORTH_SEARCH;
            case DIRECTION_EAST:
                return EventType.EVENT_AREA_EAST_SEARCH;
            case DIRECTION_SOUTH:
                return EventType.EVENT_AREA_SOUTH_SEARCH;
            case DIRECTION_WEST:
                return EventType.EVENT_AREA_WEST_SEARCH;
            default:
                return null;
        }
    }

    public static EventType getAreaExitEventType(Direction direction) {
        switch (direction) {
            case DIRECTION_NORTH:
                return EventType.EVENT_AREA_EXIT_NORTH;
            case DIRECTION_EAST:
                return EventType.EVENT_AREA_EXIT_EAST;
            case DIRECTION_SOUTH:
                return EventType.EVENT_AREA_EXIT_SOUTH;
            case DIRECTION_WEST:
                return EventType.EVENT_AREA_EXIT_WEST;
            default:
                return null;
        }
    }

    public static EventType getAreaEnterEventType(Direction direction) {
        switch (direction) {
            case DIRECTION_NORTH:
                return EventType.EVENT_AREA_ENTER_NORTH;
            case DIRECTION_EAST:
                return EventType.EVENT_AREA_ENTER_EAST;
            case DIRECTION_SOUTH:
                return EventType.EVENT_AREA_ENTER_SOUTH;
            case DIRECTION_WEST:
                return EventType.EVENT_AREA_ENTER_WEST;
            default:
                return null;
        }
    }

    public static String[] parseCommand(String[] command, List<String> splits) {
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        int split = command.length + 1;

        splits = splits.stream().map(String::toUpperCase).collect(Collectors.toList());

        for (int i = 0; i < command.length; i++) {
            if (splits.contains(command[i].toUpperCase())) {
                split = i;
                break;
            }
        }

        String[] returnValue = new String[split < command.length + 1 ? 2 : 1];

        for (int i = 1; i < command.length; i++) {
            if (i < split) {
                first.append(command[i] + " ");
            } else if (i > split) {
                second.append(command[i] + " ");
            }
        }
        returnValue[0] = first.toString().trim();
        if (returnValue.length > 1) {
            returnValue[1] = second.toString().trim();
        }

        return returnValue;
    }

}
