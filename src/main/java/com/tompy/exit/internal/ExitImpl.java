package com.tompy.exit.internal;

import com.tompy.directive.Direction;
import com.tompy.entity.area.api.Area;
import com.tompy.exit.api.Exit;
import com.tompy.exit.api.ExitBuilder;
import com.tompy.response.api.Response;
import com.tompy.response.api.Responsive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExitImpl extends Responsive implements Exit {
    private final Area[] areas;
    private boolean state;

    private ExitImpl(Area[] areas, boolean state) {
        this.areas = areas;
        this.state = state;
    }

    public static ExitBuilder createBuilder() {
        return new ExitBuilderImpl();
    }

    @Override
    public List<Response> passThru(Direction direction) {
        List<Response> returnValue = new ArrayList<>();
        if (state) {
            returnValue.add(responseFactory.createBuilder().text(direction.getDescription()).source("Exit").build());
        } else {
            returnValue.add(responseFactory.createBuilder().text(
                    String.format("Unable to move %s", direction.getDescription())).source("Exit").build());
        }
        return returnValue;
    }

    @Override
    public Area getConnectedArea(Area area) {
        if (area.getKey() == areas[0].getKey()) {
            return areas[1];
        } else if (area.getKey() == areas[1].getKey()) {
            return areas[0];
        }
        return null;
    }

    @Override
    public void open() {
        state = true;
    }

    @Override
    public void close() {
        state = false;
    }

    @Override
    public boolean isOpen() {
        return state;
    }

    public static class ExitBuilderImpl implements ExitBuilder {
        private List<Area> areas = new ArrayList<>();
        private boolean state;

        @Override
        public Exit build() {
            if (areas.size() == 2) {
                return new ExitImpl(areas.toArray(new Area[2]), state);
            } else {
                return null;
            }
        }

        @Override
        public ExitBuilder area(Area area) {
            this.areas.add(area);
            return this;
        }

        @Override
        public ExitBuilder state(boolean state) {
            this.state = state;
            return this;
        }
    }
}
