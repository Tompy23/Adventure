package com.tompy.entity.feature.api;

import com.tompy.area.api.Exit;
import com.tompy.common.Builder;
import com.tompy.directive.FeatureType;
import com.tompy.entity.compartment.api.Compartment;

public interface FeatureBuilder extends Builder<Feature> {

    FeatureBuilder name(String name);

    FeatureBuilder longName(String longName);

    FeatureBuilder description(String description);

    FeatureBuilder type(FeatureType type);

    FeatureBuilder compartment(Compartment compartment);

    FeatureBuilder exit(Exit exit);
}
