package com.tompy.exit.api;

import com.tompy.common.Builder;
import com.tompy.entity.area.api.Area;

public interface ExitBuilder extends Builder<Exit> {
    ExitBuilder area(Area area);

    ExitBuilder state(boolean state);
}
