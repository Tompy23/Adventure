package com.tompy.area.api;

import com.tompy.common.Builder;
import com.tompy.directive.Direction;

public interface ExitBuilder extends Builder<Exit> {
    ExitBuilder direction(Direction direction);

    ExitBuilder area(Area area);
}
