package com.tompy.attribute.api;

import java.util.Map;

/**
 * Factory for creating the elements of an Attribute Manager
 */
public interface AttributeManagerFactory {

    /**
     * Create an Attribute Manager for each entity
     *
     * @return - a new Attribute Manager
     */
    AttributeManager create();

    /**
     * Create a map of Attribute Managers keyed by Entity Key, this is a one to many relationship with
     * AttributeManagers created above.
     *
     * @return - a new Map of AttributeManagers
     */
    Map<Long, AttributeManager> createMap();
}
