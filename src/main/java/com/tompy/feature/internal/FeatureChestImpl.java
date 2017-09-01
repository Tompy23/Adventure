package com.tompy.feature.internal;

import com.tompy.common.Builder;
import com.tompy.directive.FeatureType;
import com.tompy.feature.api.Feature;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;

public class FeatureChestImpl extends FeatureBasicImpl implements Feature {

    public FeatureChestImpl(String name, String description) {
        super(name, description);
        open = false;
    }

    @Override
    public List<Response> search() {
        List<Response> returnValue = new ArrayList<>();

        returnValue.add(responseFactory.createBuilder()
                .source(name)
                .text(description + " [" + (open ? "OPEN" : "CLOSED") + "]")
                .source(name)
                .build());

        return returnValue;
    }
}
