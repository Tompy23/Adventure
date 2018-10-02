package com.tompy.entity.event.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.EventType;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.event.api.Event;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionAddEventImpl extends ActionImpl {
    private final List<Event> events;
    private final EventType type;

    public ActionAddEventImpl(Entity entity, EntityService entityService, String[] responses, EventType type,
            List<Event> events) {
        super(entity, entityService, responses);
        this.events = events;
        this.type = type;
    }

    @Override
    public List<Response> apply(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        if (events != null) {
            for (Event event : events) {
                entityService.add(entity, type, event);
            }
            returnValue.addAll(responses.stream().
                    map((r) -> responseFactory.createBuilder().source(source).text(substitution(r)).build())
                    .collect(Collectors.toList()));
        }

        return returnValue;
    }
}
