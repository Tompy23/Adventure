package com.tompy.entity.item.internal;

import com.tompy.entity.api.EntityService;

import java.util.List;

public class ItemWeaponImpl extends ItemImpl {

    public ItemWeaponImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService) {
        super(key, name, descriptors, description, entityService);
    }
}
