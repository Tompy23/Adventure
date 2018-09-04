package com.tompy.directive;

public enum FeatureType {
    FEATURE_BASIC("Feature"), FEATURE_CHEST("Chest"), FEATURE_DOOR("Door");

    private String description;

    FeatureType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
