package com.tompy.entity.internal;

import com.tompy.attribute.api.Attribute;
import com.tompy.attribute.api.AttributeManager;
import com.tompy.attribute.api.AttributeManagerFactory;
import com.tompy.entity.compartment.api.CompartmentBuilder;
import com.tompy.entity.compartment.api.CompartmentBuilderFactory;
import com.tompy.entity.compartment.internal.CompartmentImpl;
import com.tompy.entity.api.EntityKey;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.item.api.ItemBuilder;
import com.tompy.entity.item.api.ItemBuilderFactory;
import com.tompy.entity.item.interna.ItemImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;

public class EntityServiceImpl implements EntityService, ItemBuilderFactory, CompartmentBuilderFactory {
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
        return ItemImpl.createBuilder(entityKey);
    }

    @Override
    public CompartmentBuilder createCompartmentBuilder() {
        entityKey++;
        return CompartmentImpl.createBuilder(entityKey);
    }

    @Override
    public EntityService add(EntityKey entity, Attribute attribute) {
        if (attributeManagers.containsKey(entity.getKey())) {
            attributeManagers.get(entity.getKey()).add(attribute);
        }
        return this;
    }

    @Override
    public EntityService add(EntityKey entity, Attribute attribute, Integer value) {
        if (attributeManagers.containsKey(entity.getKey())) {
            attributeManagers.get(entity.getKey()).add(attribute, value);
        }
        return this;
    }

    @Override
    public void remove(EntityKey entity, Attribute attribute) {
        if (attributeManagers.containsKey(entity.getKey())) {
            attributeManagers.get(entity.getKey()).remove(attribute);
        }
    }

    @Override
    public void reset(EntityKey entity, Attribute attribute) {
        if (attributeManagers.containsKey(entity.getKey())) {
            attributeManagers.get(entity.getKey()).reset(attribute);
        }
    }

    @Override
    public boolean is(EntityKey entity, Attribute attribute) {
        if (attributeManagers.containsKey(entity.getKey())) {
            return attributeManagers.get(entity.getKey()).is(attribute);
        } else {
            return false;
        }
    }

    @Override
    public OptionalInt valueFor(EntityKey entity, Attribute attribute) {
        if (attributeManagers.containsKey(entity.getKey())) {
            return attributeManagers.get(entity.getKey()).getValue(attribute);
        } else {
            return OptionalInt.empty();
        }
    }
}
