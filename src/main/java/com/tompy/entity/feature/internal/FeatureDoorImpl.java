package com.tompy.entity.feature.internal;

import com.tompy.entity.api.EntityService;
import com.tompy.exit.api.Exit;
import com.tompy.response.api.Response;

import java.util.Collections;
import java.util.List;

/**
 * A Door is something that can be hidden, open/closed, locked, etc.
 * Normally a door will interact with an Exit, providing passage or not depending on if the Door Feature is Open
 * A Door rarely has anything in its compartment.
 * A Door can be trapped.
 */
public class FeatureDoorImpl extends FeatureBasicImpl {
    private Exit exit;

    protected FeatureDoorImpl(Long key, String name, List<String> descriptors, String description,
                              EntityService entityService, Exit exit) {
        super(key, name, descriptors, description, entityService);
        this.exit = exit;
    }

    @Override
    public List<Response> open() {
        exit.open();
        return Collections.emptyList();
    }

    @Override
    public List<Response> close() {
        exit.close();
        return Collections.emptyList();
    }
}
