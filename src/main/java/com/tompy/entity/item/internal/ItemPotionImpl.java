package com.tompy.entity.item.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.event.api.Event;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.tompy.directive.EventType.EVENT_AFTER_POTION;
import static com.tompy.directive.EventType.EVENT_BEFORE_POTION;
import static com.tompy.directive.EventType.EVENT_KEY_AFTER_USE;

public class ItemPotionImpl extends ItemImpl {
    private static final Logger LOGGER = LogManager.getLogger(ItemPotionImpl.class);
    private final Event event;

    public ItemPotionImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, Event event) {
        super(key, name, descriptors, description, entityService);
        this.event = event;
    }

    @Override
    public List<Response> use(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        returnValue.addAll(entityService.handle(this, EVENT_BEFORE_POTION, player, adventure));

        if (event.pull(player, adventure)) {
            returnValue.addAll(event.apply(player, adventure));
        }

        returnValue.addAll(entityService.handle(this, EVENT_AFTER_POTION, player, adventure));

        return returnValue;
    }
}
