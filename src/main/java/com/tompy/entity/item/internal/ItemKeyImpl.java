package com.tompy.entity.item.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.feature.api.Feature;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A key is used to lock/unlock a feature.
 */
public class ItemKeyImpl extends ItemImpl {
    private static final Logger LOGGER = LogManager.getLogger(ItemKeyImpl.class);
    private final Feature target;

    public ItemKeyImpl(Long key, String name, List<String> descriptors, String description, EntityService entityService,
        Feature target) {
        super(key, name, descriptors, description, entityService);
        this.target = target;
    }

    @Override
    public List<Response> use(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        LOGGER.info("Using key [{}] on [{}]", getName(), target.getName());

        returnValue.add(this.responseFactory.createBuilder().source(getSource())
            .text(String.format("Using %s on %s", getName(), target.getName())).build());

        if (entityService.is(target, Attribute.LOCKED)) {
            target.unlock(player, adventure);
        } else {
            target.lock(player, adventure);
        }

        return returnValue;
    }

    @Override
    public boolean hasTarget(Entity entity) {
        return entity.getKey().equals(target.getKey());
    }

}
