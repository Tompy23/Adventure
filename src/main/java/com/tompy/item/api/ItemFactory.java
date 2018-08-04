package com.tompy.item.api;

/**
 * Factor for Items
 */
public interface ItemFactory {

    /**
     * Create an Item
     *
     * @param key         - Entity Key
     * @param name        - Simple Name
     * @param longName    - Full Name
     * @param description - Descriptive text
     * @return - the new Item
     */
    Item create(Long key, String name, String longName, String description);
}
