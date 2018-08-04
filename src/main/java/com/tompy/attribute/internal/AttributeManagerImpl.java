package com.tompy.attribute.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.attribute.api.AttributeManager;

import java.util.*;

public class AttributeManagerImpl implements AttributeManager {
    private Map<Attribute, Integer> managed;
    private Adventure adventure;

    public AttributeManagerImpl(Adventure adventure) {
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
        managed = new HashMap<>();
    }

    @Override
    public void clear() {
        managed.clear();
    }

    @Override
    public void add(Attribute attribute) {
        add(attribute, 1);
    }

    @Override
    public void add(Attribute attribute, Integer value) {
        if (managed.containsKey(attribute)) {
            if (attribute.stackable()) {
                managed.put(attribute, managed.get(attribute) + value);
            }
        } else {
            managed.put(attribute, attribute.hasValue() || attribute.stackable() ? value : null);
        }
    }

    @Override
    public void remove(Attribute attribute) {
        if (managed.containsKey(attribute)) {
            if (attribute.stackable()) {
                if (managed.get(attribute) > 1) {
                    managed.put(attribute, managed.get(attribute) - 1);
                } else {
                    managed.remove(attribute);
                }
            } else {
                managed.remove(attribute);
            }
        }
    }

    @Override
    public void reset(Attribute attribute) {
        if (managed.containsKey(attribute)) {
            managed.remove(attribute);
            managed.put(attribute, 0);
        }
    }

    @Override
    public Set<Attribute> getAll() {
        return Collections.unmodifiableSet(managed.keySet());
    }

    @Override
    public OptionalInt getValue(Attribute attribute) {
        if (managed.containsKey(attribute)) {
            return attribute.hasValue() || attribute.stackable() ? OptionalInt.of(
                    managed.get(attribute)) : OptionalInt.empty();
        } else {
            return OptionalInt.empty();
        }
    }

    @Override
    public boolean is(Attribute attribute) {
        return managed.keySet().contains(attribute);
    }
}
