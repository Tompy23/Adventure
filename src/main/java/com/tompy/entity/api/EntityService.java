package com.tompy.entity.api;

import com.tompy.attribute.api.Attribute;
import com.tompy.entity.compartment.api.CompartmentBuilderFactory;
import com.tompy.entity.item.api.ItemBuilderFactory;

import java.util.OptionalInt;

/**
 * A service combining entities with various functions and states via an Attribute Manager
 */
public interface EntityService extends ItemBuilderFactory, CompartmentBuilderFactory {

    /**
     * Add Attribute to an entity
     *
     * @param key       - The entity
     * @param attribute - Attribute to add
     * @return - A reference to itself for chaining
     */
    EntityService add(Entity key, Attribute attribute);

    /**
     * Add Attribute to an entity with a starting value
     *
     * @param key       - The entity
     * @param attribute - Attribute to add
     * @param value     - Starting value
     * @return - A reference to itself for chaining
     */
    EntityService add(Entity key, Attribute attribute, Integer value);

    /**
     * Remove an Attribute from an entity
     *
     * @param key       - The entity
     * @param attribute - Attribute to remove
     */
    void remove(Entity key, Attribute attribute);

    /**
     * Reset an Attribute's value for an entity
     *
     * @param key       - The entity
     * @param attribute - Attribute to reset
     */
    void reset(Entity key, Attribute attribute);

    /**
     * Determine if this entity has this attribute
     *
     * @param key       - The entity
     * @param attribute - Attribute to check
     * @return - True if the entity has the Attribute
     */
    boolean is(Entity key, Attribute attribute);

    /**
     * Retreive the value for an entity if it has one
     *
     * @param key       - The entity
     * @param attribute - Attribute with the value
     * @return - An Optional with the value or an empty Optional if no value for the Attribute
     */
    OptionalInt valueFor(Entity key, Attribute attribute);
}
