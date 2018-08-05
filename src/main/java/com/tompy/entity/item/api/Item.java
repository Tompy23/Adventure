package com.tompy.entity.item.api;

import com.tompy.entity.api.Entity;
import com.tompy.response.api.Response;

/**
 * Hold state for an item
 */
public interface Item extends Entity {
    Response use();
}
