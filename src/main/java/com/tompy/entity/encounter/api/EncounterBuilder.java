package com.tompy.entity.encounter.api;

import com.tompy.common.Builder;
import com.tompy.directive.EncounterType;
import com.tompy.entity.item.api.Item;

public interface EncounterBuilder extends Builder<Encounter> {
    EncounterBuilder type(EncounterType type);

    EncounterBuilder items(Item[] items);

    EncounterBuilder buyRate(double buyRate);

    EncounterBuilder sellRate(double sellRate);
}
