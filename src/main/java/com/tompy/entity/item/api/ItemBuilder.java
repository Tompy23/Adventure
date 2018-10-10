package com.tompy.entity.item.api;

import com.tompy.common.Builder;
import com.tompy.directive.EventType;
import com.tompy.directive.ItemType;
import com.tompy.entity.api.EntityFacade;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.event.api.Event;

/**
 * Interface used by Item Builder
 */
public interface ItemBuilder extends Builder<Item> {
    ItemBuilder name(String name);

    ItemBuilder description(String description);

    ItemBuilder type(ItemType type);

    ItemBuilder target(EntityFacade target);

    ItemBuilder targetFeature(Feature targetFeature);

    ItemBuilder event(Event event);

    ItemBuilder eventType(EventType eventType);
}
