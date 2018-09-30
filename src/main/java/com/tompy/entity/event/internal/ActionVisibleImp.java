package com.tompy.entity.event.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionVisibleImp extends ActionImpl {
    public ActionVisibleImp(Entity entity, EntityService entityService, String[] responses) {
        super(entity, entityService, responses);
    }

    @Override public List<Response> apply(Player player, Adventure adventure) {
        entityService.add(entity, Attribute.VISIBLE);
        List<Response> returnValue = new ArrayList<>();
        returnValue.addAll(responses.stream().
                map((r) -> responseFactory.createBuilder().source(source).text(substitution(r)).build())
                .collect(Collectors.toList()));
        return returnValue;

    }
}
