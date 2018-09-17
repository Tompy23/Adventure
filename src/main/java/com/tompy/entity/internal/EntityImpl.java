package com.tompy.entity.internal;

import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.response.api.Responsive;

import java.util.List;
import java.util.Objects;

/**
 * Provides basic implementation for Entity subclasses
 */
public abstract class EntityImpl extends Responsive implements Entity {
    protected final String name;
    protected final List<String> descriptors;
    protected final String description;
    protected final EntityService entityService;
    private final Long key;

    public EntityImpl(Long key, String name, List<String> descriptors, String description,
                      EntityService entityService) {
        this.key = Objects.requireNonNull(key, "Entity Key cannot be null.");
        this.name = Objects.requireNonNull(name, "Name cannot be null.");
        this.descriptors = Objects.requireNonNull(descriptors, "Descriptors can be empty, but not null.");
        this.description = Objects.requireNonNull(description, "Description cannot be null.");
        this.entityService = entityService;
    }

    @Override
    public Long getKey() {
        return key;
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

    @Override
    public boolean equals(Object other) {
        Entity otherEntity = other instanceof Entity ? ((Entity) other) : null;
        if (otherEntity != null) {
            return this.key == otherEntity.getKey();
        }
        return false;
    }
}
