package com.tompy.entity.item.interna;

import com.tompy.entity.api.EntityBuilderHelperImpl;
import com.tompy.entity.api.EntityImpl;
import com.tompy.entity.item.api.Item;
import com.tompy.entity.item.api.ItemBuilder;
import com.tompy.response.api.Response;

import java.util.List;

public class ItemImpl extends EntityImpl implements Item {
    private ItemImpl(Long key, String name, List<String> descriptors, String description) {
        super(key, name, descriptors, description);
    }

    public static ItemBuilder createBuilder(Long key) {
        return new ItemBuilderImpl(key);
    }

    @Override
    public Response use() {
        // TODO placeholder so Item isn't so lonely
        // From this it appears Item may become abstract and its use(), etc. will be overloaded.
        return this.responseFactory.createBuilder().source(getSource()).text("Using " + getName()).build();
    }

    public static class ItemBuilderImpl extends EntityBuilderHelperImpl implements ItemBuilder {

        public ItemBuilderImpl(Long key) {
            super(key);
        }

        @Override
        public Item build() {
            return new ItemImpl(key, name, buildDescriptors(), description);
        }

        @Override
        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public ItemBuilder longName(String longName) {
            this.longName = longName;
            return this;
        }

        @Override
        public ItemBuilder description(String description) {
            this.description = description;
            return this;
        }
    }
}
