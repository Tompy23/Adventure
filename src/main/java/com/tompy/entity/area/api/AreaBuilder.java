package com.tompy.entity.area.api;

import com.tompy.common.Builder;
import com.tompy.directive.Direction;

public interface AreaBuilder extends Builder<Area> {
    AreaBuilder name(String name);

    AreaBuilder description(String description);

    AreaBuilder searchDescription(String searchDesription);

    AreaBuilder searchDirectionDescription(Direction direction, String searchDirectionDescription);

    AreaBuilder compartmentName(String compartmentName);

    AreaBuilder compartmentDescription(String compartmentDescription);
}
