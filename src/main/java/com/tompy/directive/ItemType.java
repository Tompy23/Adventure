package com.tompy.directive;

/**
 *
 */
public enum ItemType {
    ITEM_TEST("test"), ITEM_GEM("Gem"), ITEM_KEY("Key"), ITEM_WEAPON("Weapon");

    private String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
