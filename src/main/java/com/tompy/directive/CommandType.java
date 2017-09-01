package com.tompy.directive;

public enum CommandType {
    COMMAND_CLOSE("Close", "Closing"), // TODO
    COMMAND_EQUIP("Equip", "Equipping"), // TODO (Must be in inventory)
    COMMAND_INSPECT_ITEM("Inspect", "Inspecting"), // TODO (Must be in inventory)
    COMMAND_MOVE("Move", "Moving"),
    COMMAND_OPEN("Open", "Opening"), // TODO
    COMMAND_QUIT("Quit", "Quitting"),
    COMMAND_RUN("Run", "Running"), // TODO
    COMMAND_SEARCH("Search", "Searching"),
    COMMAND_SEARCH_DIRECTION("Search", "Searching"),
    COMMAND_SEARCH_FEATURE("Search", "Searching"), // TODO
    COMMAND_SEARCH_ON("Search on", "Searching on"), // TODO
    COMMAND_SEARCH_IN("Search in", "Searching in"), // TODO
    COMMAND_TAKE("Take", "Taking"), // TODO
    COMMAND_USE("Use", "Using"); // TODO

    private String description;
    private String participle;

    CommandType(String description, String participle) {
        this.description = description;
        this.participle = participle;
    }

    public String getDescription() {
        return description;
    }

    public String getParticiple() {
        return participle;
    }
}
