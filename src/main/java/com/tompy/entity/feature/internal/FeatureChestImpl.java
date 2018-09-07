package com.tompy.entity.feature.internal;

import com.tompy.attribute.api.Attribute;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityFacade;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.internal.EntityFacadeImpl;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A Chest is a feature that can be open/close, locked, etc.  It normally has items in its compartment.
 * A Chest can be trapped.
 */
public class FeatureChestImpl extends FeatureBasicImpl {
    private static final Logger LOGGER = LogManager.getLogger(FeatureChestImpl.class);
    private final EntityFacade open;

    protected FeatureChestImpl(Long key, String name, List<String> descriptors, String description,
                               EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        open = EntityFacadeImpl.createBuilder(entityService).entity(this).attribute(Attribute.OPEN).build();
    }

    @Override
    public List<Response> search() {
        List<Response> returnValue = new ArrayList<>();

        LOGGER.info("Searching Chest [{} - {}]", new String[] {getName(), EntityUtil.is(open) ? "OPEN" : "CLOSED"});

        returnValue.add(responseFactory.createBuilder().source(name).text(
            description + " [" + (EntityUtil.is(open) ? "open" : "closed") + "]").build());

        return returnValue;
    }
}
