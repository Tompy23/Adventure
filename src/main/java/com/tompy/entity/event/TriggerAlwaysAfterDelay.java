package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.player.Player;

public class TriggerAlwaysAfterDelay extends TriggerImpl {
    private final int delay;
    private int counter = 0;

    public TriggerAlwaysAfterDelay(Entity entity, int delay) {
        super(entity);
        this.delay = delay;
    }

    @Override
    public boolean pull(Player player, Adventure adventure) {
        if (counter >= delay) {
            return true;
        }
        counter++;
        return false;
    }
}
