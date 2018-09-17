package com.tompy.entity.event.internal;

import com.tompy.entity.api.Entity;
import com.tompy.entity.event.api.Trigger;

import java.util.Objects;

public abstract class TriggerImpl implements Trigger {
    protected boolean trigger;
    protected final Entity entity;

    public TriggerImpl(Entity entity) {
        this.entity = Objects.requireNonNull(entity, "Entity cannot be null.");
    }
}
