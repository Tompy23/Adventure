package com.tompy.entity.compartment.internal;

import com.tompy.entity.api.EntityService;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.internal.EntityImpl;
import com.tompy.entity.item.api.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CompartmentImpl extends EntityImpl implements Compartment {
    protected final List<Item> items;

    protected CompartmentImpl(Long key, String name, List<String> descriptors, String description,
                              EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        items = new ArrayList<>();
    }

    @Override
    public Item getItem() {
        return null;
    }

    @Override
    public List<Item> getAllItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public void removeItem(Item item) {
        items.remove(item);
    }
}
