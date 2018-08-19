package com.tompy.entity.compartment.api;

import com.tompy.common.Builder;
import com.tompy.entity.item.api.Item;

public interface CompartmentBuilder extends Builder<Compartment> {
    CompartmentBuilder name(String name);

    CompartmentBuilder longName(String longName);

    CompartmentBuilder description(String description);

    CompartmentBuilder items(Item... items);
}
