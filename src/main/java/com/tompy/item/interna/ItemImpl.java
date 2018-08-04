package com.tompy.item.interna;

import com.tompy.item.api.Item;

import java.util.List;
import java.util.Objects;

public class ItemImpl implements Item {
    private final String name;
    private final List<String> descriptors;
    private final String description;
    private final Long key;

    public ItemImpl(Long key, String name, List<String> descriptors, String description) {
        this.name = Objects.requireNonNull(name, "Name cannot be null.");
        this.descriptors = descriptors;
        this.description = Objects.requireNonNull(description, "Description cannot be null.");
        this.key = Objects.requireNonNull(key, "Key cannot be null.");
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
    public Long getEntityKey() {
        return key;
    }
}
