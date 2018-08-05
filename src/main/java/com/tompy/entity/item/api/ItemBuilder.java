package com.tompy.entity.item.api;

import com.tompy.common.Builder;

/**
 * Interface used by Item Builder
 */
public interface ItemBuilder extends Builder<Item> {
    ItemBuilder name(String name);

    ItemBuilder longName(String longName);

    ItemBuilder description(String description);
}
