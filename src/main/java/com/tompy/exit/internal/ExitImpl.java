package com.tompy.exit.internal;

import com.tompy.directive.Direction;
import com.tompy.entity.area.api.Area;
import com.tompy.exit.api.Exit;
import com.tompy.exit.api.ExitBuilder;
import com.tompy.response.api.Response;
import com.tompy.response.api.Responsive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ExitImpl extends Responsive implements Exit {
    private static final Logger LOGGER = LogManager.getLogger(ExitImpl.class);
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
        LOGGER.info("Passing thru exit [{}] connecting areas [{}] and [{}]",
                    new String[]{direction.name(), areas[0].getName(), areas[1].getName()});
        if (state) {
            returnValue.add(responseFactory.createBuilder().text(direction.getDescription()).source("Exit").build());
        } else {
            returnValue.add(
                responseFactory.createBuilder().text(String.format("Unable to move %s", direction.getDescription()))
                    .source("Exit").build());
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.areas[0].getName());
        sb.append(" - ");
        sb.append(this.areas[1].getName());
        sb.append(" - ");
        sb.append(this.isOpen());

        return sb.toString();
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
