package com.tompy.entity.encounter.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.encounter.api.Encounter;
import com.tompy.player.api.Player;

import java.util.List;

public class EncounterEnvironmentImpl extends EncounterImpl implements Encounter {

    public EncounterEnvironmentImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, Player player, Adventure adventure) {
        super(key, name, descriptors, description, entityService, player, adventure);
    }
}
