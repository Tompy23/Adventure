package com.tompy.entity.api;

import com.tompy.attribute.api.Attribute;
import com.tompy.common.Builder;

public interface EntityFacadeBuilder extends Builder<EntityFacade> {

    EntityFacadeBuilder entity(Entity entity);

    EntityFacadeBuilder attribute(Attribute attribute);
}
