package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.directive.EventType;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionRemoveEvent extends ActionImpl {
    private final List<Event> events;
    private final EventType type;

    public ActionRemoveEvent(Entity entity, EntityService entityService, String[] responses, EventType type,
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
                entityService.remove(entity, type, event);
            }
            returnValue.addAll(responses.stream().
                    map((r) -> responseFactory.createBuilder().source(source).text(substitution(r)).build())
                    .collect(Collectors.toList()));
        }

        return returnValue;
    }
}
