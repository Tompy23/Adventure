package com.tompy.entity.encounter.api;

import com.tompy.adventure.api.Adventure;
import com.tompy.player.api.Player;

public interface EncounterBuilderFactory {
    EncounterBuilder createEncounterBuilder(Player player, Adventure adventure);

    void addEncounter(Encounter encounter);
}
