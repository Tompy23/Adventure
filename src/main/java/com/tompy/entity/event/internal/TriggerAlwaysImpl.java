package com.tompy.entity.event.internal;

import com.tompy.entity.api.Entity;

public class TriggerAlwaysImpl extends TriggerImpl {

    public TriggerAlwaysImpl(Entity entity) {
        super(entity);
    }

    @Override
    public boolean pull() {
        return true;
    }
}
