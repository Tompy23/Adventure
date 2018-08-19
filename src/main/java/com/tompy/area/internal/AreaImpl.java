package com.tompy.area.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.adventure.internal.AdventureUtils;
import com.tompy.area.api.Area;
import com.tompy.area.api.AreaBuilder;
import com.tompy.area.api.Exit;
import com.tompy.directive.Direction;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.feature.api.Feature;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import com.tompy.response.api.Responsive;

import java.util.*;

public class AreaImpl extends Responsive implements Area {
    protected String name;
    protected String description;
    protected String searchDescription;
    protected String[] searchDirectionDescription = new String[]{"", "", "", ""};
    protected List<Exit> exits = new ArrayList<>();
    protected List<Feature> features = new ArrayList<>();
    protected Map<Direction, List<Feature>> directionFeatures = new HashMap<>();
    protected Compartment compartment;
    protected EntityService entityService;

    protected AreaImpl(String name, String description, String searchDescription, String[] searchDirectionDesription,
                       Compartment compartment, EntityService entityService) {
        this.name = name;
        this.description = description;
        this.searchDescription = searchDescription;
        this.searchDirectionDescription = searchDirectionDesription;
        this.compartment = compartment;
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
    }

    public static AreaBuilder createBuilder(EntityService entityService) {
        return new AreaImpl.AreaBuilderImpl(entityService);
    }

    @Override
    public void installExit(Exit exit) {
        Objects.requireNonNull(exit, "When initializing an exit to a room, the exit must not be null");

        // There can only be a single exit in a direction, so first we remove any existing exit
        removeExistingExit(exit.getDirection());

        // Next we add the exit
        exits.add(exit);

        // Finally we see if the connecting area has an exit in the opposite direction
        detectParallelExit(exit);

    }

    private void removeExistingExit(Direction direction) {
        Exit toBeRemoved = null;
        for (Exit e : exits) {
            if (e.getDirection() == direction) {
                toBeRemoved = e;
                break;
            }
        }
        if (null != toBeRemoved) {
            exits.remove(toBeRemoved);
        }
    }

    private void detectParallelExit(Exit exit) {
        Area otherArea = exit.getArea();
        if (null != otherArea) {
            Direction otherDirection = AdventureUtils.getOppositeDirection(exit.getDirection());
            if (null != otherDirection) {
                Exit otherExit = otherArea.getExitForDirection(otherDirection);
                if (null != otherExit) {
                    exit.setParallel(otherExit);
                    otherExit.setParallel(exit);
                }
            }
        }
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
    public String getName() {
        return name;
    }

    @Override
    public Exit getExitForDirection(Direction direction) {
        Exit returnValue = null;
        for (Exit e : exits) {
            if (e.getDirection() == direction) {
                returnValue = e;
                break;
            }
        }
        return returnValue;
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
        }

        player.searchArea(name);

        return returnValue;
    }

    @Override
    public List<Response> searchDirection(Direction direction, Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        if (searchDirectionDescription[direction.ordinal()] != null) {
            returnValue.add(
                    responseFactory.createBuilder().text(searchDirectionDescription[direction.ordinal()]).source(
                            name).build());
        } else {
            returnValue.add(
                    responseFactory.createBuilder().text("Nothing special to the " + direction.getDescription()).source(
                            name).build());
        }

        if (directionFeatures.containsKey(direction)) {
            returnValue.add(responseFactory.createBuilder().text("You find ").source(name).build());
            directionFeatures.get(direction).stream().forEach((f) -> returnValue.addAll(f.search()));
        }


        return returnValue;
    }

    public static class AreaBuilderImpl implements AreaBuilder {
        protected String name;
        protected String description;
        protected String searchDescription;
        protected String[] searchDirectionDescription = new String[4];
        protected Compartment compartment;
        protected EntityService entityService;

        public AreaBuilderImpl(EntityService entityService) {
            this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
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
        public AreaBuilder compartment(Compartment compartment) {
            this.compartment = compartment;
            return this;
        }

        @Override
        public Area build() {
            return new AreaImpl(name, description, searchDescription, searchDirectionDescription, compartment,
                    entityService);
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
