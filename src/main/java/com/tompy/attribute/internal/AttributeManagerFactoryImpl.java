package com.tompy.attribute.internal;

import com.tompy.attribute.api.AttributeManager;
import com.tompy.attribute.api.AttributeManagerFactory;

public class AttributeManagerFactoryImpl implements AttributeManagerFactory {

    @Override
    public AttributeManager create() {
        return new AttributeManagerImpl();
    }
}
