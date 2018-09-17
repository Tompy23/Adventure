package com.tompy.entity.event.internal;

import com.tompy.entity.api.Entity;
import com.tompy.entity.event.api.Action;
import com.tompy.response.api.Responsive;

import java.util.Objects;

public abstract class ActionImpl extends Responsive implements Action {
    protected final String source;
    protected final Entity entity;

    public ActionImpl(Entity entity) {
        this.entity = Objects.requireNonNull(entity, "Entity cannot be null.");
        this.source = entity.getName();
    }
}
