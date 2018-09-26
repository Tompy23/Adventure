package com.tompy.entity.encounter.api;

import com.tompy.common.Builder;
import com.tompy.directive.EncounterType;

public interface EncounterBuilder extends Builder<Encounter> {
    EncounterBuilder type(EncounterType type);
}
