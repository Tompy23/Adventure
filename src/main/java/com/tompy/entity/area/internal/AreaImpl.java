package com.tompy.entity.area.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.Direction;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.area.api.Area;
import com.tompy.entity.area.api.AreaBuilder;
import com.tompy.entity.compartment.internal.CompartmentBuilderHelperImpl;
import com.tompy.entity.compartment.internal.CompartmentImpl;
import com.tompy.entity.feature.api.Feature;
import com.tompy.exit.api.Exit;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.*;

public class AreaImpl extends CompartmentImpl implements Area {
    protected final String searchDescription;
    protected String[] searchDirectionDescription = new String[]{"", "", "", ""};
    protected Map<Direction, Exit> exits = new HashMap<>();
    protected List<Feature> features = new ArrayList<>();
    protected Map<Direction, List<Feature>> directionFeatures = new HashMap<>();

    protected AreaImpl(Long key, String name, List<String> descriptors, String description, String searchDescription,
                       String[] searchDirectionDescription, EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        this.searchDescription = searchDescription;
        this.searchDirectionDescription = searchDirectionDescription;
    }


    public static AreaBuilder createBuilder(Long key, EntityService entityService) {
        return new AreaBuilderImpl(key, entityService);
    }

    @Override
    public void installExit(Direction direction, Exit exit) {
        Objects.requireNonNull(exit, "When initializing an exit to a room, the exit must not be null");

        // Next we add the exit
        exits.put(direction, exit);

    }

    @Override
    public void installFeature(Feature feature, Direction direction) {
        if (null != direction) {
            if (!directionFeatures.containsKey(direction)) {
                directionFeatures.put(direction, new ArrayList<>());
            }
            directionFeatures.get(direction).add(feature);
        } else {
            features.add(feature);
        }
    }

    @Override
    public Exit getExitForDirection(Direction direction) {
        return exits.get(direction);
    }

    @Override
    public List<Response> enter(Direction direction, Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        returnValue.add(responseFactory.createBuilder().text(description).source(name).build());
        player.visitArea(name);

        return returnValue;
    }

    @Override
    public List<Response> exit(Direction direction, Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        returnValue.add(responseFactory.createBuilder().text("bye bye").source(name).build());

        return returnValue;
    }

    @Override
    public List<Response> search(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        returnValue.add(responseFactory.createBuilder().text(searchDescription).source(name).build());

        if (!features.isEmpty()) {
            returnValue.add(responseFactory.createBuilder().text("You find ").source(name).build());
            features.stream().forEach((f) -> returnValue.addAll(f.search()));
            items.stream().forEach((i) -> returnValue
                .add(this.responseFactory.createBuilder().source(i.getName()).text(i.getDetailDescription()).build()));
        }

        player.searchArea(name);

        return returnValue;
    }

    @Override
    public List<Response> searchDirection(Direction direction, Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        if (searchDirectionDescription[direction.ordinal()] != null) {
            returnValue.add(
                responseFactory.createBuilder().text(searchDirectionDescription[direction.ordinal()]).source(name)
                    .build());
        } else {
            returnValue.add(responseFactory.createBuilder().text("Nothing special to the " + direction.getDescription())
                                .source(name).build());
        }

        if (directionFeatures.containsKey(direction)) {
            returnValue.add(responseFactory.createBuilder().text("You find ").source(name).build());
            directionFeatures.get(direction).stream().forEach((f) -> returnValue.addAll(f.search()));
        }

        return returnValue;
    }

    @Override
    public List<Feature> getAllFeatures() {
        List<Feature> returnValue = new ArrayList<>();
        returnValue.addAll(features);
        directionFeatures.values().stream().forEach((f) -> returnValue.addAll(f));

        return returnValue;
    }

    @Override
    public String getName() {
        return name;
    }

    public static class AreaBuilderImpl extends CompartmentBuilderHelperImpl implements AreaBuilder {
        protected String name;
        protected String description;
        protected String searchDescription;
        protected String[] searchDirectionDescription = new String[4];
        protected String compartmentName;
        protected String compartmentDescription;

        public AreaBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        @Override
        public AreaBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public AreaBuilder description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public AreaBuilder searchDescription(String searchDescription) {
            this.searchDescription = searchDescription;
            return this;
        }

        @Override
        public AreaBuilder searchDirectionDescription(Direction direction, String searchDirectionDescription) {
            this.searchDirectionDescription[direction.ordinal()] = searchDirectionDescription;
            return this;
        }

        @Override
        public AreaBuilder compartmentName(String compartmentName) {
            this.compartmentName = compartmentName;
            return this;
        }

        @Override
        public AreaBuilder compartmentDescription(String compartmentDescription) {
            this.compartmentDescription = compartmentDescription;
            return this;
        }

        @Override
        public Area build() {
            return new AreaImpl(key, name, this.buildDescriptors(), description, searchDescription,
                                searchDirectionDescription, entityService);
        }

        @Override
        public boolean equals(Object other) {
            if (null == other || !(other instanceof AreaImpl)) {
                return false;
            } else {
                AreaImpl otherArea = (AreaImpl) other;
                return (this.name.equals(otherArea.name));
            }
        }
    }
}
