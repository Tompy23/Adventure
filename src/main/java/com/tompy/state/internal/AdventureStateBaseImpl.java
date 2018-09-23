package com.tompy.state.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.player.api.Player;

import java.util.Objects;

public abstract class AdventureStateBaseImpl {
    protected final Player player;
    protected final Adventure adventure;

    public AdventureStateBaseImpl(Player player, Adventure adventure) {
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
    }
}
