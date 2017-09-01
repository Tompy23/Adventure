package com.tompy.area.api;

import com.tompy.common.Builder;
import com.tompy.directive.Direction;

public interface ExitBuilder extends Builder<Exit> {
    public ExitBuilder direction(Direction direction);
    public ExitBuilder area(Area area);
}
