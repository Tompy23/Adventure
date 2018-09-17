package com.tompy.entity.event.internal;

import com.tompy.entity.api.Entity;
import com.tompy.entity.event.api.Trigger;

public class TriggerOnceImpl extends TriggerImpl {


    public TriggerOnceImpl(Entity entity) {
        super(entity);
        trigger = true;
    }

    @Override
    public boolean pull() {
        boolean returnValue = trigger;
        trigger = false;
        return returnValue;
    }
}
