package com.tompy.directive;

public enum ActionType {
    ACTION_DESCRIBE("describe"), ACTION_ENCOUNTER("encounter"), ACTION_EXPLORE("explore"),
    ACTION_HORRIBLE_DEATH("death"), ACTION_MAKE_VISIBLE("visible"), ACTION_ADD_EVENT("add_event"),
    ACTION_REMOVE_EVENT("remove_event");

    private String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
