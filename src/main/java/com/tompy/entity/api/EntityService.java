package com.tompy.entity.api;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.directive.EventType;
import com.tompy.entity.area.api.Area;
import com.tompy.entity.area.api.AreaBuilderFactory;
import com.tompy.entity.encounter.api.EncounterBuilderFactory;
import com.tompy.entity.event.api.Event;
import com.tompy.entity.event.api.EventBuilderFactory;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.feature.api.FeatureBuilderFactory;
import com.tompy.entity.item.api.Item;
import com.tompy.entity.item.api.ItemBuilderFactory;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.List;
import java.util.OptionalInt;

/**
 * A service combining entities with various functions and states via an Attribute Manager
 */
public interface EntityService
        extends ItemBuilderFactory, FeatureBuilderFactory, AreaBuilderFactory, EventBuilderFactory,
        EncounterBuilderFactory {

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

    Entity getEntityByName(String name);

    Item getItemByDescription(String description);

    Feature getFeatureByDescription(String description);

    Area getAreaByName(String name);

    String getAttributeDescription(Entity key, Attribute attribute);


    // This group of functions represents the ability to build an item's attribute with attribute
    // specific responses based on whether the attribute exists or not.
    void addAttributeDoesApply(Entity key, Attribute attribute, String text);

    void addAttributeDoesNotApply(Entity key, Attribute attribute, String text);

    String getAttributeApplicationText(Entity key, Attribute attribute);

    // These are functions associated with Event Managers

    EntityService add(Entity entity, EventType type, Event event);

    void remove(Entity entity, EventType type, Event event);

    List<Event> get(Entity entity, EventType type);

    List<Response> handle(Entity entity, EventType type, Player player, Adventure adventure);
}
