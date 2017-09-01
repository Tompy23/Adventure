package com.tompy.feature.internal;

import com.tompy.directive.FeatureType;
import com.tompy.feature.api.Feature;
import com.tompy.feature.api.FeatureBuilder;
import com.tompy.feature.api.FeatureBuilderFactory;
import com.tompy.response.api.Response;
import com.tompy.response.api.Responsive;

import java.util.List;

public class FeatureBasicImpl extends Responsive implements Feature {
    protected String name;
    protected String description;
    protected boolean open;

    public FeatureBasicImpl(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Response> search() {
        return null;
    }

    @Override
    public List<Response> openClose() {
        return null;
    }

    @Override
    public List<Response> drink() {
        return null;
    }

    public static FeatureBuilderFactory createBuilderFactory() { return FeatureBasicImpl::createBuilder; }

    public static FeatureBuilder createBuilder() { return new FeatureBuilderImpl(); }

    public static final class FeatureBuilderImpl implements FeatureBuilder {
        private FeatureType type;
        private String name;
        private String description;

        @Override
        public FeatureBuilder type(FeatureType type) {
            this.type = type;
            return this;
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
        public Feature build() {
            switch (type) {
                case FEATURE_CHEST:
                    return new FeatureChestImpl(name, description);
            }
            return null;
        }
    }
}
