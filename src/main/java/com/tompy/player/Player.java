package com.tompy.player;

import com.tompy.entity.area.Area;
import com.tompy.entity.compartment.Compartment;
import com.tompy.entity.item.Item;

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

    void removeItem(Item item);

    boolean dropItem(Item item, Compartment compartment);

    boolean equip(Item item);

    boolean unequip(Item item);

    List<Item> getInventory();

    int moneyValue();

    void addMoney(int value);

    boolean pay(int value);
}
