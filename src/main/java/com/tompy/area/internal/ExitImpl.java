package com.tompy.area.internal;

import com.tompy.area.api.Area;
import com.tompy.area.api.Exit;
import com.tompy.area.api.ExitBuilder;
import com.tompy.directive.Direction;
import com.tompy.response.api.Response;
import com.tompy.response.api.Responsive;

import java.util.ArrayList;
import java.util.List;

public class ExitImpl extends Responsive implements Exit {
    private Direction direction;
    private Area area;
    private Exit parallel;
    private boolean state;

    private ExitImpl(Area area, Direction direction, boolean state) {
        this.area = area;
        this.direction = direction;
        this.state = state;
    }

    public static ExitBuilder createBuilder() {
        return new ExitBuilderImpl();
    }

    @Override
    public List<Response> passThru() {
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
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Area getArea() {
        return area;
    }

    @Override
    public Exit getParallel() {
        return parallel;
    }

    @Override
    public void setParallel(Exit exit) {
        parallel = exit;
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
        private Area area;
        private Direction direction;

        @Override
        public Exit build() {
            return new ExitImpl(area, direction, true);
        }

        @Override
        public ExitBuilder area(Area area) {
            this.area = area;
            return this;
        }

        @Override
        public ExitBuilder direction(Direction direction) {
            this.direction = direction;
            return this;
        }
    }
}
