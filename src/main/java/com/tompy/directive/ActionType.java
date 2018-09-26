package com.tompy.directive;

public enum ActionType {
    DESCRIBE("describe"), ENCOUNTER("encounter"), EXPLORE("explore"), HORRIBLE_DEATH("death");

    private String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
