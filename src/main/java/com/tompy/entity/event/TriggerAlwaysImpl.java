package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TriggerAlwaysImpl extends TriggerImpl {
    public static final Logger LOGGER = LogManager.getLogger(TriggerAlwaysImpl.class);
    public TriggerAlwaysImpl(Entity entity) {
        super(entity);
    }

    @Override
    public boolean pull(Player player, Adventure adventure) {
        LOGGER.info("Checking trigger.");
        return true;
    }
}
