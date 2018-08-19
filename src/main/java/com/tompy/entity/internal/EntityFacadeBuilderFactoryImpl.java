package com.tompy.entity.internal;

import com.tompy.entity.api.EntityFacadeBuilder;
import com.tompy.entity.api.EntityFacadeBuilderFactory;
import com.tompy.entity.api.EntityService;

import java.util.Objects;

public class EntityFacadeBuilderFactoryImpl implements EntityFacadeBuilderFactory {
    private final EntityService entityService;

    public EntityFacadeBuilderFactoryImpl(EntityService entityService) {
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
    }

    @Override
    public EntityFacadeBuilder builder() {
        return new EntityFacadeImpl.EntityFacadeBuilderImpl(entityService);
    }
}
