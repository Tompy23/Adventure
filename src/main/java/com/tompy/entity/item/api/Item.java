package com.tompy.entity.item.api;

import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityKey;
import com.tompy.response.api.Response;

import java.util.List;

/**
 * Hold state for an item
 */
public interface Item extends Entity {
    Response use();
}
