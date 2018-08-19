package com.tompy.entity.internal;

import com.tompy.attribute.api.Attribute;
import com.tompy.attribute.api.AttributeManager;
import com.tompy.attribute.api.AttributeManagerFactory;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.compartment.api.CompartmentBuilder;
import com.tompy.entity.compartment.internal.CompartmentImpl;
import com.tompy.entity.item.api.Item;
import com.tompy.entity.item.api.ItemBuilder;
import com.tompy.entity.item.interna.ItemImpl;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.feature.api.FeatureBuilder;
import com.tompy.entity.feature.internal.FeatureBasicImpl;

import java.util.*;

public class EntityServiceImpl implements EntityService {
    private final AttributeManagerFactory attributeManagerFactory;
    private Map<Long, AttributeManager> attributeManagers;
    private List<Item> items;
    private List<Compartment> compartments;
    private List<Feature> features;
    private Long entityKey;

    public EntityServiceImpl(AttributeManagerFactory attributeManagerFactory) {
        this.attributeManagerFactory = Objects.requireNonNull(attributeManagerFactory,
                "Attribute Manager Factory cannot be null.");
        attributeManagers = new HashMap<>();
        items = new ArrayList<>();
        compartments = new ArrayList<>();
        features = new ArrayList<>();
        entityKey = 0L;
    }

    @Override
    public ItemBuilder createItemBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        return ItemImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    @Override
    public CompartmentBuilder createCompartmentBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        return CompartmentImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addCompartment(Compartment compartment) {
        if (compartment != null) {
            compartments.add(compartment);
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
    public Item getItemByDescription(String description) {
        String[] descriptors = description.split(" ");
        for (Item item : items) {

        }
        return null;
    }

    @Override
    public Compartment getCompartmentByDescription(String description) {
        return null;
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
    public FeatureBuilder createFeatureBuilder() {
        entityKey++;
        attributeManagers.put(entityKey, attributeManagerFactory.create());
        return FeatureBasicImpl.createBuilder(entityKey, this);
    }

    @Override
    public void addFeature(Feature feature) {
        if (feature != null) {
            features.add(feature);
        }
    }
}
