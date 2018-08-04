package com.tompy.item.api;

import java.util.List;

/**
 * Service to handle Item actions
 */
public interface ItemService {

    /**
     * ???
     * @param descriptions
     * @return
     */
    List<Item> getItemsByDescription(List<String> descriptions);
}
