package com.tompy.entity.compartment.internal;

import com.tompy.entity.internal.EntityBuilderHelperImpl;
import com.tompy.entity.internal.EntityImpl;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.compartment.api.CompartmentBuilder;
import com.tompy.entity.item.api.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CompartmentImpl extends EntityImpl implements Compartment {
    private final List<Item> items;

    private CompartmentImpl(Long key, String name, List<String> descriptors, String description, List<Item> items,
                            EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        this.items = items;

    }

    public static CompartmentBuilder createBuilder(Long key, EntityService entityService) {
        return new CompartmentBuilderImpl(key, entityService);
    }

    @Override
    public Item getItem() {
        return null;
    }

    @Override
    public List<Item> getAllItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public void removeItem(Item item) {
        items.remove(item);
    }

    public static final class CompartmentBuilderImpl extends EntityBuilderHelperImpl implements CompartmentBuilder {
        private List<Item> items;

        public CompartmentBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        @Override
        public Compartment build() {
            Compartment compartment = new CompartmentImpl(key, name, buildDescriptors(), description, items,
                    entityService);
            if (entityService != null) {
                entityService.addCompartment(compartment);
            }
            return compartment;
        }

        @Override
        public CompartmentBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public CompartmentBuilder longName(String longName) {
            this.longName = longName;
            return this;
        }

        @Override
        public CompartmentBuilder description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public CompartmentBuilder items(Item... items) {
            this.items = Arrays.asList(items);
            return this;
        }
    }
}
