package com.tompy.entity.api;

import java.util.List;
import java.util.Objects;

public abstract class EntityImpl extends EntityKeyImpl implements Entity {
    protected final String name;
    protected final List<String> descriptors;
    protected final String description;

    public EntityImpl(Long key, String name, List<String> descriptors, String description) {
        super(key);
        this.name = Objects.requireNonNull(name, "Name cannot be null.");
        this.descriptors = descriptors;
        this.description = Objects.requireNonNull(description, "Description cannot be null.");
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        descriptors.stream().forEach(a -> sb.append(a + " "));
        return sb.toString() + name;
    }

    @Override
    public String getShortName() {
        return name;
    }

    @Override
    public String getDetailDescription() {
        return description;
    }

    @Override
    public List<String> getDescriptionWords() {
        return descriptors;
    }

    @Override
    public String getSource() {
        return name + "[" + getKey() + "]";
    }

}
