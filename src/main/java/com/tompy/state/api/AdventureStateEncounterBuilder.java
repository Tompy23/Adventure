package com.tompy.state.api;

import com.tompy.common.Builder;
import com.tompy.entity.encounter.api.Encounter;

public interface AdventureStateEncounterBuilder extends Builder<AdventureState> {
    AdventureStateEncounterBuilder encounter(Encounter encounter);
}
