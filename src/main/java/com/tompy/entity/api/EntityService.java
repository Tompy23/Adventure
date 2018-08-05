package com.tompy.entity.api;

import com.tompy.attribute.api.Attribute;

import java.util.OptionalInt;

/**
 * A service combining entities with various functions and states via an Attribute Manager
 */
public interface EntityService {

    /**
     * Add Attribute to an entity
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute to add
     * @return - A reference to itself for chaining
     */
    EntityService add(EntityKey key, Attribute attribute);

    /**
     * Add Attribute to an entity with a starting value
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute to add
     * @param value     - Starting value
     * @return - A reference to itself for chaining
     */
    EntityService add(EntityKey key, Attribute attribute, Integer value);

    /**
     * Remove an Attribute from an entity
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute to remove
     */
    void remove(EntityKey key, Attribute attribute);

    /**
     * Reset an Attribute's value for an entity
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute to reset
     */
    void reset(EntityKey key, Attribute attribute);

    /**
     * Determine if this entity has this attribute
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute to check
     * @return - True if the entity has the Attribute
     */
    boolean is(EntityKey key, Attribute attribute);

    /**
     * Retreive the value for an entity if it has one
     *
     * @param key       - Key to the entity
     * @param attribute - Attribute with the value
     * @return - An Optional with the value or an empty Optional if no value for the Attribute
     */
    OptionalInt valueFor(EntityKey key, Attribute attribute);
}
