package com.tompy.entity.compartment.internal;

import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.compartment.api.CompartmentBuilder;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityBuilderHelperImpl;
import com.tompy.entity.api.EntityImpl;
import com.tompy.entity.item.api.Item;

import java.util.Arrays;
import java.util.List;

public class CompartmentImpl extends EntityImpl implements Compartment {
    private final List<Item> items;
    private final List<Compartment> on;
    private final List<Compartment> in;
    private final List<Compartment> under;
    private final List<Compartment> behind;

    private CompartmentImpl(Long key, String name, List<String> descriptors, String description, List<Item> items,
                            List<Compartment> on, List<Compartment> in, List<Compartment> under, List<Compartment>
                                    behind) {
        super(key, name, descriptors, description);
        this.items = items;
        this.on = on;
        this.in = in;
        this.under = under;
        this.behind = behind;
    }

    public static CompartmentBuilder createBuilder(Long key) {
        return new CompartmentBuilderImpl(key);
    }

    @Override
    public Entity fromIn() {
        return null;
    }

    @Override
    public Entity fromOn() {
        return null;
    }

    @Override
    public Entity fromUnder() {
        return null;
    }

    @Override
    public Entity fromBehind() {
        return null;
    }

    public static final class CompartmentBuilderImpl extends EntityBuilderHelperImpl implements CompartmentBuilder {
        private List<Item> items;
        private List<Compartment> on;
        private List<Compartment> in;
        private List<Compartment> under;
        private List<Compartment> behind;


        public CompartmentBuilderImpl(Long key) {
            super(key);
        }

        @Override
        public Compartment build() {
            return new CompartmentImpl(key, name, buildDescriptors(), description, items, on, in, under, behind);
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

        @Override
        public CompartmentBuilder on(Compartment... on) {
            this.on = Arrays.asList(on);
            return this;
        }

        @Override
        public CompartmentBuilder in(Compartment... in) {
            this.in = Arrays.asList(in);
            return this;
        }

        @Override
        public CompartmentBuilder under(Compartment... under) {
            this.under = Arrays.asList(under);
            return this;
        }

        @Override
        public CompartmentBuilder behind(Compartment... behind) {
            this.behind = Arrays.asList(behind);
            return this;
        }
    }
}
