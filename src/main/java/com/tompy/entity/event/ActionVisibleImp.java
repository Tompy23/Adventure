package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.attribute.Attribute;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;
import com.tompy.response.Response;

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
