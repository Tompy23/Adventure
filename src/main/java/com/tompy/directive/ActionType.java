package com.tompy.directive;

public enum ActionType {
    DESCRIBE("describe");

    private String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
