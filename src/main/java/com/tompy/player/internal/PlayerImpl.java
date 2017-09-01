package com.tompy.player.internal;

import com.tompy.area.api.Area;
import com.tompy.player.api.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerImpl implements Player {
    private Area currentArea;
    private Map<String, PlayerInfo> areaInfoMap = new HashMap<>();

    public PlayerImpl(Area area) { currentArea = area; }

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
        areaInfoMap.get(areaName).incrementCount(PlayerInfoKeys.VISITS);
    }

    @Override
    public int areaVisitCount(String areaName) {
        int visits = 0;
        if(!areaInfoMap.containsKey(areaName)) {
            visits = areaInfoMap.get(areaName).getCount(PlayerInfoKeys.VISITS);
        }
        return visits;
    }

    @Override
    public void searchArea(String roomName) {
        if (!areaInfoMap.containsKey(roomName)) {
            areaInfoMap.put(roomName, new PlayerInfo());
        }
        areaInfoMap.get(roomName).incrementCount(PlayerInfoKeys.SEARCHES);
    }

    @Override
    public int areaSearchCount(String roomName) {
        int searches = 0;
        if(!areaInfoMap.containsKey(roomName)) {
            searches = areaInfoMap.get(roomName).getCount(PlayerInfoKeys.SEARCHES);
        }
        return searches;
    }
}
