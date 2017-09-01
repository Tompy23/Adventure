package com.tompy.area.api;

import com.tompy.common.Builder;
import com.tompy.directive.Direction;

public interface AreaBuilder extends Builder<Area> {
    public AreaBuilder name(String name);
    public AreaBuilder description(String description);
    public AreaBuilder searchDescription(String searchDesription);
    public AreaBuilder searchDirectionDescription(Direction direction, String searchDirectionDescription);
}
