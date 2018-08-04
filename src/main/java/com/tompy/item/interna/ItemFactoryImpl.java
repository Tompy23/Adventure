package com.tompy.item.interna;

import com.tompy.adventure.api.Adventure;
import com.tompy.item.api.Item;
import com.tompy.item.api.ItemFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemFactoryImpl implements ItemFactory {
    private final Adventure adventure;

    public ItemFactoryImpl(Adventure adventure) {
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot " + "be null.");
    }

    public Item create(Long key, String name, String longName, String description) {
        List<String> descriptors = new ArrayList<>();
        descriptors.addAll(Arrays.asList(longName.split(" ")));
        descriptors = descriptors.stream().filter(a -> !a.equals(name)).collect(Collectors.toList());
        return new ItemImpl(key, name, descriptors, description);
    }
}
