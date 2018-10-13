package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.player.Player;

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
