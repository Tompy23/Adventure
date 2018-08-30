package com.tompy.entity.item.interna;

import com.tompy.entity.api.EntityService;

import java.util.List;

public class ItemGemImpl extends ItemImpl {

    public ItemGemImpl(Long key, String name, List<String> descriptors, String description, EntityService
            entityService) {
        super(key, name, descriptors, description, entityService);
    }
}
