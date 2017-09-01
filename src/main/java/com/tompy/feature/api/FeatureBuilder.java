package com.tompy.feature.api;

import com.tompy.common.Builder;
import com.tompy.directive.FeatureType;

public interface FeatureBuilder extends Builder<Feature> {
    FeatureBuilder type(FeatureType type);
    FeatureBuilder name(String name);
    FeatureBuilder description(String description);
}
