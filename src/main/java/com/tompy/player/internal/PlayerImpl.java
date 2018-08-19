package com.tompy.player.internal;

import com.tompy.area.api.Area;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.item.api.Item;
import com.tompy.player.api.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tompy.player.internal.PlayerInfo.SEARCHES;
import static com.tompy.player.internal.PlayerInfo.VISITS;

public class PlayerImpl implements Player {
    private String name;
    private Area currentArea;
    private Map<String, PlayerInfo> areaInfoMap = new HashMap<>();
    private List<Item> inventory = new ArrayList<>();
    private List<Item> equipped = new ArrayList<>();

    public PlayerImpl(String name, Area area) {
        this.name = name;
        currentArea = area;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Area getArea() {
        return currentArea;
    }

    @Override
    public void setArea(Area area) {
        currentArea = area;
    }

    @Override
    public void visitArea(String areaName) {
        if (!areaInfoMap.containsKey(areaName)) {
            areaInfoMap.put(areaName, new PlayerInfo());
        }
        areaInfoMap.get(areaName).incrementCount(VISITS);
    }

    @Override
    public int areaVisitCount(String areaName) {
        int visits = 0;
        if (!areaInfoMap.containsKey(areaName)) {
            visits = areaInfoMap.get(areaName).getCount(VISITS);
        }
        return visits;
    }

    @Override
    public void searchArea(String roomName) {
        if (!areaInfoMap.containsKey(roomName)) {
            areaInfoMap.put(roomName, new PlayerInfo());
        }
        areaInfoMap.get(roomName).incrementCount(SEARCHES);
    }

    @Override
    public int areaSearchCount(String roomName) {
        int searches = 0;
        if (!areaInfoMap.containsKey(roomName)) {
            searches = areaInfoMap.get(roomName).getCount(SEARCHES);
        }
        return searches;
    }

    @Override
    public boolean addItem(Item item) {
        if (!inventory.contains(item)) {
            inventory.add(item);
            return true;
        }
        return false;
    }

    @Override
    public boolean dropItem(Item item, Compartment compartment) {
        if (inventory.contains(item)) {
            // TODO When Compartment is fleshed out, we'll deal with this
            compartment.addItem(item);
            return true;
        }
        return false;
    }

    @Override
    public boolean equip(Item item) {
        if (inventory.contains(item)) {
            if (equipped.stream().mapToInt(Item::hands).sum() < 2) {
                inventory.remove(item);
                equipped.add(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unequip(Item item) {
        if (equipped.contains(item)) {
            equipped.remove(item);
            inventory.add(item);
            return true;
        }
        return false;
    }
}
