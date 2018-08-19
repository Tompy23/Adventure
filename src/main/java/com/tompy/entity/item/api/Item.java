package com.tompy.entity.item.api;

import com.tompy.entity.api.Entity;
import com.tompy.response.api.Response;

import java.util.List;

/**
 * Hold state for an item
 * Funcionaly an Item is USED on a TARGET (another Item or Feature) and the action is taken with in the Use function.
 * <p>
 * NOTE
 * Ok, let's try this, let's have a public interface Use functional interface that can be applied to Items (along
 * with other
 * functional interfaces).  This way we can add the actual functionality at run time.
 * NOTE
 */
public interface Item extends Entity {
    List<Response> use();
    boolean hasTarget(Entity entity);
    int hands();
    int encumbrance();
}