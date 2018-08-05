package com.tompy.attribute.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.AttributeManager;
import com.tompy.attribute.api.AttributeManagerFactory;

import java.util.Objects;

public class AttributeManagerFactoryImpl implements AttributeManagerFactory {
    private final Adventure adventure;

    public AttributeManagerFactoryImpl(Adventure adventure) {
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
    }

    @Override
    public AttributeManager create() {
        return new AttributeManagerImpl(adventure);
    }
}
