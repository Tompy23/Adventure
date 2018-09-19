package com.tompy.entity.event.api;

import com.tompy.adventure.api.Adventure;
import com.tompy.player.api.Player;

public interface Trigger {
    boolean pull(Player player, Adventure adventure);
}
