package com.tompy.entity.area.api;

public interface AreaBuilderFactory {
    AreaBuilder createAreabuilder();

    void addArea(Area area);
}
