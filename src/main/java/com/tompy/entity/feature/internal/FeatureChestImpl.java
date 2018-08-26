package com.tompy.entity.feature.internal;

import com.tompy.attribute.api.Attribute;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.feature.api.Feature;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * A Chest is a feature that can be open/close, locked, etc.  It normally has items in its compartment.
 * A Chest can be trapped.
 */
public class FeatureChestImpl extends FeatureBasicImpl {

    protected FeatureChestImpl(Long key, String name, List<String> descriptors, String description, EntityService
            entityService) {
        super(key, name, descriptors, description, entityService);
    }

    @Override
    public List<Response> search() {
        List<Response> returnValue = new ArrayList<>();

        returnValue.add(responseFactory.createBuilder().source(name).text(
                description + " [" + (entityService.is(this, Attribute.OPEN) ? "OPEN" : "CLOSED") + "]").build());

        return returnValue;
    }
}
