package com.tompy.item.api;

import com.tompy.entity.api.EntityKey;

import java.util.List;

/**
 * Hold state for an item
 */
public interface Item extends EntityKey {

    /**
     * Get the full descriptive name of the Item
     *
     * @return - Item's long name
     */
    String getName();

    /**
     * Get the simple name of the Item
     *
     * @return - Item's simple name
     */
    String getShortName();

    /**
     * Get the full description of the Item
     *
     * @return - Item's description
     */
    String getDetailDescription();

    /**
     * The descriptive words taken from the name
     *
     * @return - List of descriptive words for Item
     */
    List<String> getDescriptionWords();
}
