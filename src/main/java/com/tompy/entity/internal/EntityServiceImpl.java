package com.tompy.entity.internal;

import com.tompy.attribute.api.Attribute;
import com.tompy.attribute.api.AttributeManager;
import com.tompy.attribute.api.AttributeManagerFactory;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.compartment.api.CompartmentBuilder;
import com.tompy.entity.compartment.internal.CompartmentImpl;
import com.tompy.entity.item.api.ItemBuilder;
import com.tompy.entity.item.interna.ItemImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;

public class EntityServiceImpl implements EntityService {
    private final AttributeManagerFactory attributeManagerFactory;
    private Map<Long, AttributeManager> attributeManagers;
    private Long entityKey;

    public EntityServiceImpl(AttributeManagerFactory attributeManagerFactory) {
        this.attributeManagerFactory = Objects.requireNonNull(attributeManagerFactory,
                "Attribute Manager Factory cannot be null.");
        attributeManagers = new HashMap<>();
        entityKey = 0L;
    }

    @Override
    public ItemBuilder createItemBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        return ItemImpl.createBuilder(entityKey);
    }

    @Override
    public CompartmentBuilder createCompartmentBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        return CompartmentImpl.createBuilder(entityKey);
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
}
