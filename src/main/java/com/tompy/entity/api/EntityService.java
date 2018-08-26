package com.tompy.entity.api;

import com.tompy.attribute.api.Attribute;
import com.tompy.entity.area.api.Area;
import com.tompy.entity.area.api.AreaBuilderFactory;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.compartment.api.CompartmentBuilderFactory;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.item.api.Item;
import com.tompy.entity.item.api.ItemBuilderFactory;
import com.tompy.entity.feature.api.FeatureBuilderFactory;

import java.util.OptionalInt;

/**
 * A service combining entities with various functions and states via an Attribute Manager
 */
public interface EntityService extends ItemBuilderFactory, FeatureBuilderFactory, AreaBuilderFactory {

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

    Item getItemByDescription(String description);
    Feature getFeatureByDescription(String description);
    Area getAreaByName(String name);

    String getAttributeDescription(Entity key, Attribute attribute);


    // This group of functions represents the ability to build an item's attribute with attribute
    // specific responses based on whether the attribute exists or not.
    void addAttributeDoesApply(Entity key, Attribute attribute, String text);
    void addAttributeDoesNotApply(Entity key, Attribute attribute, String text);
    String getAttributeApplicationText(Entity key, Attribute attribute);


}
