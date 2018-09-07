package com.tompy.entity.compartment.api;

import com.tompy.entity.api.Entity;
import com.tompy.entity.item.api.Item;

import java.util.List;

public interface Compartment extends Entity {
    List<Item> getAllItems();

    void addItem(Item item);

    void removeItem(Item item);

}
