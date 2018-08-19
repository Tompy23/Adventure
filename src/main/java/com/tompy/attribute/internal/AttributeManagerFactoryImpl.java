package com.tompy.attribute.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.AttributeManager;
import com.tompy.attribute.api.AttributeManagerFactory;

import java.util.Objects;

public class AttributeManagerFactoryImpl implements AttributeManagerFactory {

    @Override
    public AttributeManager create() {
        return new AttributeManagerImpl();
    }
}
