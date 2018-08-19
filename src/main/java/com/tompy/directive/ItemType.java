package com.tompy.directive;

/**
 *
 */
public enum ItemType {
    ITEM_TEST("test"), ITEM_KEY("Key");

    private String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
