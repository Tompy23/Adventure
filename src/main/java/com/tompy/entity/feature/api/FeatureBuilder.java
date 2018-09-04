package com.tompy.entity.feature.api;

import com.tompy.common.Builder;
import com.tompy.directive.FeatureType;
import com.tompy.exit.api.Exit;

public interface FeatureBuilder extends Builder<Feature> {

    FeatureBuilder name(String name);

    FeatureBuilder longName(String longName);

    FeatureBuilder description(String description);

    FeatureBuilder type(FeatureType type);

    FeatureBuilder exit(Exit exit);
}
