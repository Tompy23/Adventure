package com.tompy.entity.item.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.ItemType;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityFacade;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.internal.EntityBuilderHelperImpl;
import com.tompy.entity.internal.EntityImpl;
import com.tompy.entity.item.api.Item;
import com.tompy.entity.item.api.ItemBuilder;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ItemImpl extends EntityImpl implements Item {
    private static final Logger LOGGER = LogManager.getLogger(ItemImpl.class);
    private int hands;
    private int encumbrance;

    protected ItemImpl(Long key, String name, List<String> descriptors, String description,
                       EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        hands = 1;
        encumbrance = 0;
    }

    public static ItemBuilder createBuilder(Long key, EntityService entityService) {
        return new ItemBuilderImpl(key, entityService);
    }

    @Override
    public List<Response> use(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        LOGGER.info("Using [{}]", getName());

        // TODO placeholder so Item isn't so lonely
        // From this it appears Item may become abstract and its use(), etc. will be overloaded.
        returnValue.add(this.responseFactory.createBuilder().source(getSource()).text("Using " + getName()).build());

        return returnValue;
    }

    @Override
    public boolean hasTarget(Entity entity) {
        // TODO Looks like this might be overloaded as well for each item type.  Should ItemImpl be abstract?
        return false;
    }

    @Override
    public int hands() {
        return hands;
    }

    @Override
    public int encumbrance() {
        return encumbrance;
    }

    public static class ItemBuilderImpl extends EntityBuilderHelperImpl implements ItemBuilder {
        private ItemType type;
        private EntityFacade target;
        private Feature targetFeature;

        public ItemBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        @Override
        public Item build() {
            Item item;
            switch (type) {
                case ITEM_KEY:
                    item = new ItemKeyImpl(key, name, buildDescriptors(), description, entityService, targetFeature);
                    if (entityService != null) {
                        entityService.addItem(item);
                    }
                    break;
                case ITEM_GEM:
                    item = new ItemGemImpl(key, name, buildDescriptors(), description, entityService);
                    if (entityService != null) {
                        entityService.addItem(item);
                    }
                default:
                    item = new ItemImpl(key, name, buildDescriptors(), description, entityService);
                    if (entityService != null) {
                        entityService.addItem(item);
                    }
            }

            return item;
        }

        @Override
        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public ItemBuilder description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public ItemBuilder type(ItemType type) {
            this.type = type;
            return this;
        }

        @Override
        public ItemBuilder target(EntityFacade target) {
            this.target = target;
            return this;
        }

        @Override
        public ItemBuilder targetFeature(Feature targetFeature) {
            this.targetFeature = targetFeature;
            return this;
        }
    }
}
