package com.tompy.entity.event.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.encounter.api.Encounter;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import com.tompy.state.api.AdventureStateFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ActionEncounterImpl extends ActionImpl {
    private final Encounter encounter;
    private final AdventureStateFactory stateFactory;

    public ActionEncounterImpl(Entity entity, EntityService entityService, String[] responses, Encounter encounter, AdventureStateFactory stateFactory) {
        super(entity, entityService, responses);
        this.encounter = Objects.requireNonNull(encounter, "Encounter List cannot be null.");
        this.stateFactory = Objects.requireNonNull(stateFactory, "State Factory cannot be null.");
    }

    @Override public List<Response> apply(Player player, Adventure adventure) {
        adventure.changeState(stateFactory.createEncounterState().encounter(encounter).build());
        List<Response> returnValue = new ArrayList<>();
        returnValue.addAll(responses.stream().
                map((r) -> responseFactory.createBuilder().source(source).text(substitution(r)).build())
                .collect(Collectors.toList()));
        return returnValue;
    }
}
