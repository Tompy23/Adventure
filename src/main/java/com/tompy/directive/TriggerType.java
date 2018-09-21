package com.tompy.directive;

public enum TriggerType {
    ALWAYS("always"), ONCE("once"), ONCE_DELAY("once_delay"), ALWAYS_DELAY("always_delay");

    private String description;

    TriggerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
