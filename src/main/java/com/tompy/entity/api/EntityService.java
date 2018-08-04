package com.tompy.entity.api;

import com.tompy.attribute.api.Attribute;
import com.tompy.item.api.Item;

import java.util.OptionalInt;

/**
 * A service combining entities with various functions and states
 */
public interface EntityService {

    /**
     * Create an Item
     *
     * @param name        - Name of the item
     * @param longName    - Descriptive name
     * @param description - Text description
     * @return - The new name
     */
    Item createItem(String name, String longName, String description);

    /**
     * Add Attribute to an entity
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute to add
     */
    void add(Long key, Attribute attribute);

    /**
     * Add Attribute to an entity with a starting value
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute to add
     * @param value     - Starting value
     */
    void add(Long key, Attribute attribute, Integer value);

    /**
     * Remove an Attribute from an entity
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute to remove
     */
    void remove(Long key, Attribute attribute);

    /**
     * Reset an Attribute's value for an entity
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute to reset
     */
    void reset(Long key, Attribute attribute);

    /**
     * Determine if this entity has this attribute
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute to check
     * @return - True if the entity has the Attribute
     */
    boolean is(Long key, Attribute attribute);

    /**
     * Retreive the value for an entity if it has one
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute with the value
     * @return - An Optional with the value or an empty Optional if no value for the Attribute
     */
    OptionalInt valueFor(Long key, Attribute attribute);
}
