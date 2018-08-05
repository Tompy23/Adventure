package com.tompy.entity.api;

import java.util.List;

public interface Entity extends EntityKey {

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

    String getSource();

}
