package com.tompy.entity.item.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.directive.EventType;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.feature.api.Feature;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ItemWeaponImpl extends ItemImpl {
    private static final Logger LOGGER = LogManager.getLogger(ItemWeaponImpl.class);
    private final Feature target;

    public ItemWeaponImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, Feature target) {
        super(key, name, descriptors, description, entityService);
        this.target = target;

    }

    @Override
    public List<Response> use(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        if (entityService.valueFor(this, Attribute.VALUE).getAsInt() >
                entityService.valueFor(target, Attribute.VALUE).getAsInt()) {
            returnValue.add(responseFactory.createBuilder().source(name).text("Attack success!").build());
            returnValue.addAll(entityService.handle(this, EventType.EVENT_ATTACK_SUCCESS, player, adventure));

        } else {
            returnValue.add(responseFactory.createBuilder().source(name).text("Attack failed!").build());
            returnValue.addAll(entityService.handle(this, EventType.EVENT_ATTACK_FAILURE, player, adventure));

        }
        return returnValue;
    }

    @Override
    public boolean hasTarget(Entity entity) {
        return entity.getKey().equals(target.getKey());
    }
}
