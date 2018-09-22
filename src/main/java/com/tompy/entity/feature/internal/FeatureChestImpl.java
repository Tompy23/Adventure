package com.tompy.entity.feature.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.directive.EventType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityService;
import com.tompy.player.api.Player;
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
    public List<Response> search(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Searching Feature [{}]", getName());

        EntityUtil.add(visible);
        returnValue.addAll(entityService.handle(this, EventType.FEATURE_SEARCH, player, adventure));

        return returnValue;
    }

    @Override
    public List<Response> open(Player player, Adventure adventure) {
        LOGGER.info("Opening [{}]", this.getName());
        List<Response> returnValue = new ArrayList<>();

        if (EntityUtil.is(visible)) {
            if (!EntityUtil.is(open) && !EntityUtil.is(locked)) {
                EntityUtil.add(open);
                returnValue.addAll(entityService.handle(this, EventType.FEATURE_OPEN, player, adventure));
                items.stream().forEach((i) -> entityService.add(i, Attribute.VISIBLE));
            } else if (EntityUtil.is(locked)) {
                returnValue.add(responseFactory.createBuilder().source(name).text("It is locked.").build());
            }
        } else {
            returnValue.add(responseFactory.createBuilder().source(name).text("I do not see that.").build());
        }

        return returnValue;
    }

    @Override
    public List<Response> close(Player player, Adventure adventure) {
        LOGGER.info("Closing [{}]", this.getName());
        List<Response> returnValue = new ArrayList<>();

        if (EntityUtil.is(visible)) {
            if (EntityUtil.is(open)) {
                EntityUtil.remove(open);
                returnValue.addAll(entityService.handle(this, EventType.FEATURE_CLOSE, player, adventure));
            }
            returnValue.add(responseFactory.createBuilder().source(this.getName()).text("I do not see that.").build());
        }

        return returnValue;
    }

    @Override
    public List<Response> lock(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Locking [{}]", this.getName());

        if (EntityUtil.is(visible)) {
            if (!EntityUtil.is(open) && !EntityUtil.is(locked)) {
                EntityUtil.add(locked);
                returnValue.addAll(entityService.handle(this, EventType.FEATURE_LOCK, player, adventure));
            } else {
                returnValue
                    .add(responseFactory.createBuilder().source(this.getName()).text("Unable to lock that.").build());
            }
        } else {
            returnValue.add(responseFactory.createBuilder().source(this.getName()).text("I do not see that.").build());
        }

        return returnValue;
    }

    @Override
    public List<Response> unlock(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Unlocking [{}]", this.getName());

        if (EntityUtil.is(visible)) {
            if (!EntityUtil.is(open) && EntityUtil.is(locked)) {
                EntityUtil.remove(locked);
                returnValue.addAll(entityService.handle(this, EventType.FEATURE_UNLOCK, player, adventure));
            } else {
                returnValue
                    .add(responseFactory.createBuilder().source(this.getName()).text("Unable to unlock that.").build());
            }
        } else {
            returnValue.add(responseFactory.createBuilder().source(this.getName()).text("I do not see that.").build());
        }


        return returnValue;
    }
}
