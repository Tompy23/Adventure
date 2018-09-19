package com.tompy.entity.event.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.api.Entity;
import com.tompy.player.api.Player;

public class TriggerAlwaysImpl extends TriggerImpl {

    public TriggerAlwaysImpl(Entity entity) {
        super(entity);
    }

    @Override
    public boolean pull(Player player, Adventure adventure) {
        return true;
    }
}
