package com.tompy.entity.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class EntityBuilderHelperImpl {
    protected final Long key;
    protected String name;
    protected String longName;
    protected String description;

    public EntityBuilderHelperImpl(Long key) {
        this.key = Objects.requireNonNull(key);
    }

    public List<String> buildDescriptors() {
        List<String> descriptors = new ArrayList<>();
        descriptors.addAll(Arrays.asList(longName.split(" ")));
        descriptors = descriptors.stream().filter(a -> !a.equals(name)).collect(Collectors.toList());

        return descriptors;
    }
}
