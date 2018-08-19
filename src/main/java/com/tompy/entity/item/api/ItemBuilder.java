package com.tompy.entity.item.api;

import com.tompy.common.Builder;
import com.tompy.directive.ItemType;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityFacade;

/**
 * Interface used by Item Builder
 */
public interface ItemBuilder extends Builder<Item> {
    ItemBuilder name(String name);

    ItemBuilder longName(String longName);

    ItemBuilder description(String description);

    ItemBuilder type(ItemType type);

    ItemBuilder target(EntityFacade target);
}