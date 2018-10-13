package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.player.Player;

public class TriggerAlwaysImpl extends TriggerImpl {

    public TriggerAlwaysImpl(Entity entity) {
        super(entity);
    }

    @Override
    public boolean pull(Player player, Adventure adventure) {
        return true;
    }
}
