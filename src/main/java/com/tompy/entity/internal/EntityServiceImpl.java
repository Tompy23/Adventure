package com.tompy.entity.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.attribute.api.AttributeManager;
import com.tompy.attribute.api.AttributeManagerFactory;
import com.tompy.directive.EventType;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.area.api.Area;
import com.tompy.entity.area.api.AreaBuilder;
import com.tompy.entity.area.internal.AreaImpl;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.event.api.Event;
import com.tompy.entity.event.api.EventBuilder;
import com.tompy.entity.event.api.EventManager;
import com.tompy.entity.event.api.EventManagerFactory;
import com.tompy.entity.event.internal.EventImpl;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.feature.api.FeatureBuilder;
import com.tompy.entity.feature.internal.FeatureBasicImpl;
import com.tompy.entity.item.api.Item;
import com.tompy.entity.item.api.ItemBuilder;
import com.tompy.entity.item.internal.ItemImpl;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class EntityServiceImpl implements EntityService {
    private static final Logger LOGGER = LogManager.getLogger(EntityServiceImpl.class);
    private final AttributeManagerFactory attributeManagerFactory;
    private final EventManagerFactory eventManagerFactory;
    private Map<Long, AttributeManager> attributeManagers;
    private Map<Long, EventManager> eventManagers;
    private List<Item> items;
    private List<Feature> features;
    private List<Area> areas;
    private List<Event> events;
    private Long entityKey;
    private Map<String, Entity> entityMap;

    public EntityServiceImpl(AttributeManagerFactory attributeManagerFactory, EventManagerFactory eventManagerFactory) {
        this.attributeManagerFactory =
            Objects.requireNonNull(attributeManagerFactory, "Attribute Manager Factory cannot be null.");
        this.eventManagerFactory = Objects.requireNonNull(eventManagerFactory, "Event Manager Factory cannot be null>");
        attributeManagers = new HashMap<>();
        eventManagers = new HashMap<>();
        items = new ArrayList<>();
        features = new ArrayList<>();
        areas = new ArrayList<>();
        events = new ArrayList<>();
        entityKey = 0L;
        entityMap = new HashMap<>();
    }

    @Override
    public ItemBuilder createItemBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        eventManagers.put(entityKey, eventManagerFactory.create());
        return ItemImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
            entityMap.put(item.getName(), item);
        }
    }

    @Override
    public EntityService add(Entity entity, Attribute attribute) {
        attributeManagers.get(entity.getKey()).add(attribute);
        return this;
    }

    @Override
    public EntityService add(Entity entity, Attribute attribute, Integer value) {
        attributeManagers.get(entity.getKey()).add(attribute, value);
        return this;
    }

    @Override
    public void remove(Entity entity, Attribute attribute) {
        attributeManagers.get(entity.getKey()).remove(attribute);
    }

    @Override
    public void reset(Entity entity, Attribute attribute) {
        attributeManagers.get(entity.getKey()).reset(attribute);
    }

    @Override
    public boolean is(Entity entity, Attribute attribute) {
        return attributeManagers.get(entity.getKey()).is(attribute);
    }

    @Override
    public OptionalInt valueFor(Entity entity, Attribute attribute) {
        return attributeManagers.get(entity.getKey()).getValue(attribute);
    }

    @Override
    public Entity getEntityByName(String name) {
        return entityMap.get(name);
    }

    @Override
    public Item getItemByDescription(String description) {
        String[] descriptors = description.split(" ");
        for (Item item : items) {

        }
        return null;
    }

    @Override
    public Feature getFeatureByDescription(String description) {
        return null;
    }

    @Override
    public Area getAreaByName(String name) {
        return areas.stream().filter(a -> name.equals(a.getName())).findAny().get();
    }

    @Override
    public String getAttributeDescription(Entity key, Attribute attribute) {
        if (is(key, attribute)) {
            return attribute.getDoesApply();
        } else {
            return attribute.getDoesNotApply();
        }
    }

    @Override
    public void addAttributeDoesApply(Entity key, Attribute attribute, String text) {
        attributeManagers.get(key).addApply(attribute, text);
    }

    @Override
    public void addAttributeDoesNotApply(Entity key, Attribute attribute, String text) {
        attributeManagers.get(key).addDesNotApply(attribute, text);
    }

    @Override
    public String getAttributeApplicationText(Entity key, Attribute attribute) {
        return attributeManagers.get(key).getApplication(attribute, is(key, attribute));
    }

    @Override
    public EntityService add(Entity entity, EventType type, Event event) {
        eventManagers.get(entity.getKey()).add(type, event);
        return this;
    }

    @Override
    public void remove(Entity entity, EventType type, Event event) {
        eventManagers.get(entity.getKey()).remove(type, event);
    }

    @Override
    public List<Event> get(Entity entity, EventType type) {
        return eventManagers.get(entity.getKey()).getAllOfType(type);
    }

    @Override
    public List<Response> handle(Entity entity, EventType type, Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        eventManagers.get(entity.getKey()).getAllOfType(type).stream().filter((e) -> e.pull(player, adventure))
            .forEach((e) -> returnValue.addAll(e.apply(player, adventure)));
        return returnValue;
    }

    @Override
    public FeatureBuilder createFeatureBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        eventManagers.put(entityKey, eventManagerFactory.create());
        return FeatureBasicImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addFeature(Feature feature) {
        if (feature != null) {
            features.add(feature);
            entityMap.put(feature.getName(), feature);
        }
    }

    @Override
    public AreaBuilder createAreaBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        eventManagers.put(entityKey, eventManagerFactory.create());
        return AreaImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addArea(Area area) {
        if (area != null) {
            areas.add(area);
            entityMap.put(area.getName(), area);
        }
    }

    @Override
    public EventBuilder createEventBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        eventManagers.put(entityKey, eventManagerFactory.create());
        return EventImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addEvent(Event event) {
        if (event != null) {
            events.add(event);
            entityMap.put(event.getName(), event);
        }
    }
}
