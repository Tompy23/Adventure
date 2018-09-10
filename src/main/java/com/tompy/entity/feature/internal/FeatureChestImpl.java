package com.tompy.entity.feature.internal;

import com.tompy.attribute.api.Attribute;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityService;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A Chest is a feature that can be open/close, locked, etc.
 */
public class FeatureChestImpl extends FeatureBasicImpl {
    private static final Logger LOGGER = LogManager.getLogger(FeatureChestImpl.class);

    protected FeatureChestImpl(Long key, String name, List<String> descriptors, String description,
        EntityService entityService) {
        super(key, name, descriptors, description, entityService);
    }

    @Override
    public List<Response> search() {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Searching Chest [{}]", getName());

        returnValue.add(responseFactory.createBuilder().source(name).text(String
            .format("%s [%s] [%s]", description, (EntityUtil.is(open) ? "open" : "closed"),
                EntityUtil.is(locked) ? "locked" : "unlocked")).build());

        EntityUtil.add(visible);

        return returnValue;
    }

    @Override
    public List<Response> open() {
        LOGGER.info("Opening [{}]", this.getName());
        List<Response> returnValue = new ArrayList<>();

        if (EntityUtil.is(visible)) {
            if (!EntityUtil.is(open) && !EntityUtil.is(locked)) {
                EntityUtil.add(open);
                returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("%s opens", this.getName())).build());
                items.stream().forEach((i) -> entityService.add(i, Attribute.VISIBLE));
            } else {
                returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("%s does not open", this.getName())).build());
            }
        }

        return returnValue;
    }

    @Override
    public List<Response> close() {
        LOGGER.info("Closing [{}]", this.getName());
        List<Response> returnValue = new ArrayList<>();

        if (EntityUtil.is(visible)) {
            if (EntityUtil.is(open)) {
                EntityUtil.remove(open);
                returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("%s closes", this.getName())).build());
            } else {
                returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("%s does not close", this.getName())).build());
            }
        }

        return returnValue;
    }

    @Override
    public List<Response> lock() {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Locking [{}]", this.getName());

        if (EntityUtil.is(visible)) {
            if (!EntityUtil.is(open) && !EntityUtil.is(locked)) {
                EntityUtil.add(locked);
                returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("%s is locked", this.getName())).build());
            } else {
                returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("%s is not locked", this.getName())).build());
            }
        }

        return returnValue;
    }

    @Override
    public List<Response> unlock() {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Unlocking [{}]", this.getName());

        if (EntityUtil.is(visible)) {
            if (!EntityUtil.is(open) && EntityUtil.is(locked)) {
                EntityUtil.remove(locked);
                returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("%s is unlocked", this.getName())).build());
            } else {
                returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("%s is unable to be locked", this.getName())).build());
            }
        }

        return returnValue;
    }
}
