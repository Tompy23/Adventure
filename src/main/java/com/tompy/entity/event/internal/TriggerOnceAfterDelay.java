package com.tompy.entity.event.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.api.Entity;
import com.tompy.entity.event.api.Trigger;
import com.tompy.player.api.Player;

public class TriggerOnceAfterDelay extends TriggerImpl {
    private final int delay;
    private int counter = 0;

    public TriggerOnceAfterDelay(Entity entity, int delay) {
        super(entity);
        this.delay = delay;
    }

    @Override
    public boolean pull(Player player, Adventure adventure) {
        boolean returnValue = false;
        if (counter == delay) {
            returnValue = true;
        }
        counter++;
        return returnValue;
    }
}
