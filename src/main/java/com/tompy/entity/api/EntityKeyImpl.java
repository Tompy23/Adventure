package com.tompy.entity.api;

import com.tompy.response.api.Responsive;

import java.util.Objects;

public abstract class EntityKeyImpl extends Responsive implements EntityKey {
    private final Long key;

    public EntityKeyImpl(Long key) {
        this.key = Objects.requireNonNull(key, "Entity Key cannot be null.");
    }


    @Override
    public Long getKey() {
        return key;
    }
}
