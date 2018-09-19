package com.tompy.entity.event.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.api.Entity;
import com.tompy.entity.event.api.Trigger;
import com.tompy.player.api.Player;

public class TriggerOnceImpl extends TriggerImpl {


    public TriggerOnceImpl(Entity entity) {
        super(entity);
        trigger = true;
    }

    @Override
    public boolean pull(Player player, Adventure adventure) {
        boolean returnValue = trigger;
        trigger = false;
        return returnValue;
    }
}
