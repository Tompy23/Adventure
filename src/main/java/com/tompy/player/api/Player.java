package com.tompy.player.api;

import com.tompy.entity.area.api.Area;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.item.api.Item;

import java.util.List;

public interface Player {
    String getName();
    Area getArea();
    void setArea(Area area);
    void visitArea(String areaName);
    int areaVisitCount(String roomName);
    void searchArea(String roomName);
    int areaSearchCount(String roomName);
    boolean addItem(Item item);
    boolean dropItem(Item item, Compartment compartment);
    boolean equip(Item item);
    boolean unequip(Item item);
    List<Item> getInventory();
}
