package com.tompy.entity.internal;

import com.tompy.attribute.api.Attribute;
import com.tompy.attribute.api.AttributeManager;
import com.tompy.attribute.api.AttributeManagerFactory;
import com.tompy.entity.api.EntityService;
import com.tompy.item.api.Item;
import com.tompy.item.api.ItemFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;

public class EntityServiceImpl implements EntityService {
    private final AttributeManagerFactory attributeManagerFactory;
    private final ItemFactory itemFactory;
    private Map<Long, AttributeManager> attributeManagers;
    private Long entityKey;

    public EntityServiceImpl(AttributeManagerFactory attributeManagerFactory, ItemFactory itemFactory) {
        this.attributeManagerFactory = Objects.requireNonNull(attributeManagerFactory,
                "Attribute Manager Factory cannot be null.");
        this.itemFactory = Objects.requireNonNull(itemFactory, "Item Factory cannot be null.");
        attributeManagers = attributeManagerFactory.createMap();
        entityKey = 0L;
    }

    @Override
    public Item createItem(String name, String longName, String description) {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        Item newItem = itemFactory.create(entityKey, name, longName, description);
        return newItem;
    }

    @Override
    public void add(Long key, Attribute attribute) {
        if (attributeManagers.containsKey(key)) {
            attributeManagers.get(key).add(attribute);
        }
    }

    @Override
    public void add(Long key, Attribute attribute, Integer value) {
        if (attributeManagers.containsKey(key)) {
            attributeManagers.get(key).add(attribute, value);
        }
    }

    @Override
    public void remove(Long key, Attribute attribute) {
        if (attributeManagers.containsKey(key)) {
            attributeManagers.get(key).remove(attribute);
        }
    }

    @Override
    public void reset(Long key, Attribute attribute) {
        if (attributeManagers.containsKey(key)) {
            attributeManagers.get(key).reset(attribute);
        }
    }

    @Override
    public boolean is(Long key, Attribute attribute) {
        if (attributeManagers.containsKey(key)) {
            return attributeManagers.get(key).is(attribute);
        } else {
            return false;
        }
    }

    @Override
    public OptionalInt valueFor(Long key, Attribute attribute) {
        if (attributeManagers.containsKey(key)) {
            return attributeManagers.get(key).getValue(attribute);
        } else {
            return OptionalInt.empty();
        }
    }
}
