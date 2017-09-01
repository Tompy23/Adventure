package com.tompy.player.api;

import com.tompy.area.api.Area;

public interface Player {
    public Area getArea();
    public void setArea(Area area);
    public void visitArea(String areaName);
    public int areaVisitCount(String roomName);
    public void searchArea(String roomName);
    public int areaSearchCount(String roomName);
}
