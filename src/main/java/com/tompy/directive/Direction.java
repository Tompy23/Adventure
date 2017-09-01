package com.tompy.directive;

public enum Direction {
    DIRECTION_NORTH("North"), DIRECTION_EAST("East"), DIRECTION_SOUTH("South"), DIRECTION_WEST("West");

    private String description;

    Direction(String description) {this.description = description; }

    public String getDescription() { return description; }
}
