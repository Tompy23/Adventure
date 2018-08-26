package com.tompy.entity.compartment.internal;

import com.tompy.entity.api.EntityService;
import com.tompy.entity.internal.EntityBuilderHelperImpl;
import com.tompy.entity.item.api.Item;

import java.util.ArrayList;
import java.util.List;

public class CompartmentBuilderHelperImpl extends EntityBuilderHelperImpl {
    protected List<Item> items;

    protected CompartmentBuilderHelperImpl(Long key, EntityService entityService) {
        super(key, entityService);
        this.items = new ArrayList<>();
    }
}
