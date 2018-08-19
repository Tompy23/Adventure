package com.tompy.entity.api;

import com.tompy.attribute.api.Attribute;

public interface EntityFacade {
    EntityService getService();

    Entity getEntity();

    Attribute getAttribute();
}
