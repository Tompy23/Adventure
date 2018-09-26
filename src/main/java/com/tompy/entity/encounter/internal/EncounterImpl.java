package com.tompy.entity.encounter.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.EncounterType;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.encounter.api.Encounter;
import com.tompy.entity.encounter.api.EncounterBuilder;
import com.tompy.entity.event.api.Event;
import com.tompy.entity.internal.EntityBuilderHelperImpl;
import com.tompy.entity.internal.EntityImpl;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tompy.directive.EventType.ENCOUNTER;


public class EncounterImpl extends EntityImpl implements Encounter {
    private final Player player;
    private final Adventure adventure;

    public EncounterImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, Player player, Adventure adventure) {
        super(key, name, descriptors, description, entityService);
        this.player = player;
        this.adventure = adventure;
    }

    public static EncounterBuilder createBuilder(Long key, EntityService entityService, Player player,
            Adventure adventure) {
        return new EncounterBuilderImpl(key, entityService, player, adventure);
    }


    @Override public Map<Long, String> getOptions() {
        Map<Long, String> returnValue = new HashMap<>();
        for (Event event : entityService.get(this, ENCOUNTER)) {
            returnValue.put(event.getKey(), event.getDescription());
        }
        return returnValue;
    }

    @Override public List<Response> act(Long option) {
        for (Event event : entityService.get(this, ENCOUNTER)) {
            if (event.getKey() == option) {
                return event.apply(player, adventure);
            }
        }

        return Collections.emptyList();
    }

    public static final class EncounterBuilderImpl extends EntityBuilderHelperImpl implements EncounterBuilder {
        private final Player player;
        private final Adventure adventure;
        private EncounterType type;

        public EncounterBuilderImpl(Long key, EntityService entityService, Player player, Adventure adventure) {
            super(key, entityService);
            this.player = player;
            this.adventure = adventure;
        }

        @Override public EncounterBuilder type(EncounterType type) {
            this.type = type;
            return this;
        }

        @Override public Encounter build() {
            switch (type) {
                case ENVIRONMENT:
                    return new EncounterEnvironmentImpl(key, name, this.buildDescriptors(), description, entityService,
                            player, adventure);
            }
            return null;
        }
    }
}
