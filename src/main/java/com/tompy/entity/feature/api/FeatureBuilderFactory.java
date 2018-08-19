package com.tompy.entity.feature.api;

public interface FeatureBuilderFactory {
    FeatureBuilder createFeatureBuilder();

    void addFeature(Feature feature);
}
