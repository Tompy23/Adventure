package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.player.Player;

public interface Trigger {
    boolean pull(Player player, Adventure adventure);
}
