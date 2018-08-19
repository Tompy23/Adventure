package com.tompy.area.api;

import com.tompy.common.Builder;
import com.tompy.directive.Direction;
import com.tompy.entity.compartment.api.Compartment;

public interface AreaBuilder extends Builder<Area> {
    AreaBuilder name(String name);

    AreaBuilder description(String description);

    AreaBuilder searchDescription(String searchDesription);

    AreaBuilder searchDirectionDescription(Direction direction, String searchDirectionDescription);

    AreaBuilder compartment(Compartment compartment);
}
