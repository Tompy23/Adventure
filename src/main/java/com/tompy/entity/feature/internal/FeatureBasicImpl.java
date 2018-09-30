package com.tompy.entity.feature.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.directive.EventType;
import com.tompy.directive.FeatureType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityFacade;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.compartment.internal.CompartmentImpl;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.feature.api.FeatureBuilder;
import com.tompy.entity.internal.EntityBuilderHelperImpl;
import com.tompy.entity.internal.EntityFacadeImpl;
import com.tompy.exit.api.Exit;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeatureBasicImpl extends CompartmentImpl implements Feature {
    private static final Logger LOGGER = LogManager.getLogger(FeatureBasicImpl.class);
    protected final List<Response> notImplemented;
    protected final EntityFacade open;
    protected final EntityFacade locked;
    protected final EntityFacade visible;

    protected FeatureBasicImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        notImplemented =
                Collections.singletonList(responseFactory.createBuilder().source(name).text("Not Implemented").build());
        open = EntityFacadeImpl.createBuilder(entityService).entity(this).attribute(Attribute.OPEN).build();
        locked = EntityFacadeImpl.createBuilder(entityService).entity(this).attribute(Attribute.LOCKED).build();
        visible = EntityFacadeImpl.createBuilder(entityService).entity(this).attribute(Attribute.VISIBLE).build();
    }

    public static FeatureBuilder createBuilder(Long key, EntityService entityService) {
        return new FeatureBuilderImpl(key, entityService);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Response> search(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Searching Feature [{}]", getName());

        returnValue.addAll(entityService.handle(this, EventType.FEATURE_SEARCH, player, adventure));

        return returnValue;
    }

    @Override
    public List<Response> open(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Opening [{}]", this.getName());

        if (!EntityUtil.is(open) && !EntityUtil.is(locked)) {
            EntityUtil.add(open);
            returnValue.addAll(entityService.handle(this, EventType.FEATURE_OPEN, player, adventure));
        } else {
            returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("THe %s does not open.", this.getDescription())).build());
        }


        return returnValue;
    }

    @Override
    public List<Response> close(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Closing [{}]", this.getName());

        if (EntityUtil.is(open)) {
            EntityUtil.remove(open);
            returnValue.addAll(entityService.handle(this, EventType.FEATURE_CLOSE, player, adventure));
        } else {
            returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("The %s does not close", this.getDescription())).build());
        }

        return returnValue;
    }

    @Override
    public List<Response> lock(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Locking [{}]", this.getName());

        if (!EntityUtil.is(open) && !EntityUtil.is(locked)) {
            EntityUtil.add(locked);
            returnValue.addAll(entityService.handle(this, EventType.FEATURE_LOCK, player, adventure));
        } else {
            returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("The %s is not locked", this.getDescription())).build());
        }


        return returnValue;
    }

    @Override
    public List<Response> unlock(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Unlocking [{}]", this.getName());

        if (EntityUtil.is(locked)) {
            EntityUtil.remove(locked);
            returnValue.addAll(entityService.handle(this, EventType.FEATURE_UNLOCK, player, adventure));
        } else {
            returnValue.add(responseFactory.createBuilder().source(this.getName())
                    .text(String.format("The %s is still locked", this.getName())).build());
        }

        return returnValue;
    }

    @Override
    public List<Response> drink(Player player, Adventure adventure) {
        return notImplemented;
    }

    public static final class FeatureBuilderImpl extends EntityBuilderHelperImpl implements FeatureBuilder {
        private FeatureType type;
        private Exit exit;

        public FeatureBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        @Override
        public FeatureBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public FeatureBuilder description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public FeatureBuilder type(FeatureType type) {
            this.type = type;
            return this;
        }

        @Override
        public FeatureBuilder exit(Exit exit) {
            this.exit = exit;
            return this;
        }

        @Override
        public Feature build() {
            switch (type) {
                case FEATURE_CHEST:
                    FeatureChestImpl chest =
                            new FeatureChestImpl(key, name, this.buildDescriptors(), description, entityService);
                    if (entityService != null) {
                        entityService.addFeature(chest);
                    }
                    return chest;
                case FEATURE_DOOR:
                    FeatureDoorImpl door =
                            new FeatureDoorImpl(key, name, this.buildDescriptors(), description, entityService, exit);
                    if (entityService != null) {
                        entityService.addFeature(door);
                    }
                    return door;
//                case FEATURE_MONSTER:
//                    FeatureMonsterImpl monster =
//                            new FeatureMonsterImpl(key, name, this.buildDescriptors(), description, entityService);
//                    if (entityService != null) {
//                        entityService.addFeature(monster);
//                    }
//                    return monster;
                default:
                    FeatureBasicImpl feature =
                            new FeatureBasicImpl(key, name, this.buildDescriptors(), description, entityService);
                    if (entityService != null) {
                        entityService.addFeature(feature);
                    }
                    return feature;
            }
        }
    }
}
