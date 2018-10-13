package com.tompy.entity;

import com.tompy.attribute.Attribute;

public interface EntityFacade {
    EntityService getService();

    Entity getEntity();

    Attribute getAttribute();
}
