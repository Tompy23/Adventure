package com.tompy.area.internal;

import com.tompy.response.internal.ResponseImpl;
import com.tompy.area.api.Area;
import com.tompy.area.api.Exit;
import com.tompy.area.api.ExitBuilder;
import com.tompy.area.api.ExitBuilderFactory;
import com.tompy.directive.Direction;
import com.tompy.response.api.Response;
import com.tompy.response.api.ResponseBuilderFactory;

import java.util.ArrayList;
import java.util.List;

public class ExitImpl implements Exit {
    private Direction direction;
    private Area area;
    private Exit parallel;
    private ResponseBuilderFactory responseFactory = ResponseImpl.createBuilderFactory();

    private ExitImpl(Area area, Direction direction) {
        this.area = area;
        this.direction = direction;
    }

    @Override
    public List<Response> passThru() {
        List<Response> returnValue = new ArrayList<>();

        returnValue.add(responseFactory.createBuilder().text(direction.getDescription()).source("Exit").build());

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
    public void setParallel(Exit exit) {
        parallel = exit;
    }

    @Override
    public Exit getParallel() {
        return parallel;
    }

    public static ExitBuilderFactory createBuilderFactory() { return ExitImpl::createBuilder; }

    public static ExitBuilder createBuilder() { return new ExitBuilderImpl(); }

    public static class ExitBuilderImpl implements ExitBuilder {
        private Area area;
        private Direction direction;

        @Override
        public Exit build() { return new ExitImpl(area, direction); }

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
