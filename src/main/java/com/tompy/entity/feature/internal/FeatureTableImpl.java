package com.tompy.entity.feature.internal;

import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityService;

import java.util.List;

public class FeatureTableImpl extends FeatureBasicImpl {
    protected FeatureTableImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        EntityUtil.add(open);
        EntityUtil.add(visible);
    }
}
