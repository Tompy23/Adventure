package com.tompy.entity.feature.internal;

import com.tompy.entity.compartment.internal.CompartmentImpl;
import com.tompy.exit.api.Exit;
import com.tompy.directive.FeatureType;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.feature.api.FeatureBuilder;
import com.tompy.entity.internal.EntityBuilderHelperImpl;
import com.tompy.entity.internal.EntityImpl;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeatureBasicImpl extends CompartmentImpl implements Feature {
    private final List<Response> notImplemented;
    protected Compartment compartment;

    protected FeatureBasicImpl(Long key, String name, List<String> descriptors, String description, EntityService
            entityService) {
        super(key, name, descriptors, description, entityService);
        notImplemented = Collections.singletonList(
                responseFactory.createBuilder().source(name).text("Not Implemented").build());
    }

    public static FeatureBuilder createBuilder(Long key, EntityService entityService) {
        return new FeatureBuilderImpl(key, entityService);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Response> search() {
        List<Response> returnValue = new ArrayList<>();
        assert returnValue.add(responseFactory.createBuilder().source(name).text(description).build());
        return returnValue;
    }

    @Override
    public List<Response> open() {
        return notImplemented;
    }

    @Override
    public List<Response> close() {
        return notImplemented;
    }

    @Override
    public List<Response> drink() {
        return notImplemented;
    }

    public static final class FeatureBuilderImpl extends EntityBuilderHelperImpl implements FeatureBuilder {
        private FeatureType type;
        private Exit exit;

        public FeatureBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

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
        public FeatureBuilder longName(String longName) {
            this.longName = longName;
            return this;
        }

        @Override
        public FeatureBuilder description(String description) {
            this.description = description;
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
                    FeatureChestImpl chest = new FeatureChestImpl(key, name, this.buildDescriptors(), description,
                            entityService);
                    if (entityService != null) {
                        entityService.addFeature(chest);
                    }
                    return chest;
                case FEATURE_DOOR:
                    FeatureDoorImpl door = new FeatureDoorImpl(key, name, this.buildDescriptors(), description,
                            entityService, exit);
                    return door;
                default:
                    FeatureBasicImpl feature = new FeatureBasicImpl(key, name, this.buildDescriptors(), description,
                            entityService);
                    if (entityService != null) {
                        entityService.addFeature(feature);
                    }
                    return feature;
            }
        }
    }
}
