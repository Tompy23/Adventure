package com.tompy.entity.api;

import java.util.List;

public interface Entity {

    /**
     * Retrieve the Entity Key
     *
     * @return - the value of the Key
     */
    Long getKey();

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

    /**
     * Helper function for creating messages about the Entity
     *
     * @return - Representation of the source entity
     */
    String getSource();

}
