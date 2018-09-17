package com.tompy.entity.area.api;

public interface AreaBuilderFactory {
    AreaBuilder createAreaBuilder();

    void addArea(Area area);
}
