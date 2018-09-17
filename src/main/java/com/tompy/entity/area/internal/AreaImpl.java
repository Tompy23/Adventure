package com.tompy.entity.area.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.directive.Direction;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.area.api.Area;
import com.tompy.entity.area.api.AreaBuilder;
import com.tompy.entity.compartment.internal.CompartmentBuilderHelperImpl;
import com.tompy.entity.compartment.internal.CompartmentImpl;
import com.tompy.entity.event.api.Event;
import com.tompy.entity.event.api.EventBuilder;
import com.tompy.entity.feature.api.Feature;
import com.tompy.exit.api.Exit;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class AreaImpl extends CompartmentImpl implements Area {
    private static final Logger LOGGER = LogManager.getLogger(AreaImpl.class);
    protected final String searchDescription;
    protected String[] searchDirectionDescription = new String[]{"", "", "", ""};
    protected Map<Direction, Exit> exits = new HashMap<>();
    protected List<Feature> features = new ArrayList<>();
    protected Map<Direction, List<Feature>> directionFeatures = new HashMap<>();
    protected List<Event> enterEvents = new ArrayList<>();
    protected List<Event> exitEvents = new ArrayList<>();
    protected Map<Direction, List<Event>> enterDirectionEvents = new HashMap<>();
    protected Map<Direction, List<Event>> exitDirectionEvents = new HashMap<>();


    protected AreaImpl(Long key, String name, List<String> descriptors, String description, String searchDescription,
        String[] searchDirectionDescription, EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        this.searchDescription = searchDescription;
        this.searchDirectionDescription = searchDirectionDescription;
        enterDirectionEvents.put(Direction.DIRECTION_NORTH, new ArrayList<>());
        enterDirectionEvents.put(Direction.DIRECTION_EAST, new ArrayList<>());
        enterDirectionEvents.put(Direction.DIRECTION_SOUTH, new ArrayList<>());
        enterDirectionEvents.put(Direction.DIRECTION_WEST, new ArrayList<>());
        exitDirectionEvents.put(Direction.DIRECTION_NORTH, new ArrayList<>());
        exitDirectionEvents.put(Direction.DIRECTION_EAST, new ArrayList<>());
        exitDirectionEvents.put(Direction.DIRECTION_SOUTH, new ArrayList<>());
        exitDirectionEvents.put(Direction.DIRECTION_WEST, new ArrayList<>());
    }


    public static AreaBuilder createBuilder(Long key, EntityService entityService) {
        return new AreaBuilderImpl(key, entityService);
    }

    @Override
    public void installExit(Direction direction, Exit exit) {
        Objects.requireNonNull(exit, "When initializing an exit to a room, the exit must not be null");

        LOGGER.info("Installing Exit from [{}] to area [{}]",
            new String[]{this.getName(), exit.getConnectedArea(this).getName()});

        // Next we add the exit
        exits.put(direction, exit);

    }

    @Override
    public void installFeature(Feature feature, Direction direction) {
        LOGGER.info("Installing feature [{}] in [{}]", new String[]{feature.getName(), this.getName()});
        if (null != direction) {
            LOGGER.info("Installing feature in direction [{}]", direction.name());
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
        LOGGER.info("Entering room [{}]", this.getName());

        for (Event event : enterEvents) {
            returnValue.addAll(event.apply());
        }
        for (Event event : enterDirectionEvents.get(direction)) {
            returnValue.addAll(event.apply());
        }

        returnValue.add(responseFactory.createBuilder().text(description).source(name).build());
        player.visitArea(name);

        return returnValue;
    }

    @Override
    public List<Response> exit(Direction direction, Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Exiting room [{}] in direction [{}]", this.getName(), direction.name());

        for (Event event : exitEvents) {
            returnValue.addAll(event.apply());
        }
        for (Event event : exitDirectionEvents.get(direction)) {
            returnValue.addAll(event.apply());
        }

        return returnValue;
    }

    @Override
    public List<Response> search(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Searching room [{}]", this.getName());

        returnValue.add(responseFactory.createBuilder().text(searchDescription).source(name).build());

        if (!features.isEmpty()) {
            features.stream().forEach((f) -> returnValue.addAll(f.search()));
        }

        if (!items.isEmpty()) {
            items.stream().forEach((i) -> {
                returnValue.add(
                    this.responseFactory.createBuilder().source(i.getName()).text(i.getDetailDescription()).build());
                entityService.add(i, Attribute.VISIBLE);
            });
        }

        player.searchArea(name);

        return returnValue;
    }

    @Override
    public List<Response> searchDirection(Direction direction, Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Searching room [{}] in direction [{}]", this.getName(), direction.name());

        if (searchDirectionDescription[direction.ordinal()] != null) {
            returnValue.add(
                responseFactory.createBuilder().text(searchDirectionDescription[direction.ordinal()]).source(name)
                    .build());
        } else {
            returnValue.add(responseFactory.createBuilder().text("Nothing special to the " + direction.getDescription())
                .source(name).build());
        }

        if (directionFeatures.containsKey(direction)) {
            directionFeatures.get(direction).stream().forEach((f) -> returnValue.addAll(f.search()));
        }

        return returnValue;
    }

    @Override
    public List<Feature> getAllFeatures() {
        LOGGER.info("Retrieving all features from room [{}]", this.getName());
        List<Feature> returnValue = new ArrayList<>();
        returnValue.addAll(features);
        directionFeatures.values().stream().forEach(returnValue::addAll);

        return returnValue;
    }

    @Override
    public void insertEnterEvent(Event event) {
        enterEvents.add(Objects.requireNonNull(event, "Event cannot be null."));
    }

    @Override
    public void insertEnterNorthEvent(Event event) {
        enterDirectionEvents.get(Direction.DIRECTION_NORTH).add(Objects.requireNonNull(event, "Event cannot be null."));
    }

    @Override
    public void insertEnterEastEvent(Event event) {
        enterDirectionEvents.get(Direction.DIRECTION_EAST).add(Objects.requireNonNull(event, "Event cannot be null."));
    }

    @Override
    public void insertEnterSouthEvent(Event event) {
        enterDirectionEvents.get(Direction.DIRECTION_SOUTH).add(Objects.requireNonNull(event, "Event cannot be null."));
    }

    @Override
    public void insertEnterWestEvent(Event event) {
        enterDirectionEvents.get(Direction.DIRECTION_WEST).add(Objects.requireNonNull(event, "Event cannot be null."));
    }

    @Override
    public void insertExitEvent(Event event) {
        exitEvents.add(Objects.requireNonNull(event, "Event cannot be null."));
    }

    @Override
    public void insertExitNorthEvent(Event event) {
        exitDirectionEvents.get(Direction.DIRECTION_NORTH).add(Objects.requireNonNull(event, "Event cannot be null."));
    }

    @Override
    public void insertExitEastEvent(Event event) {
        exitDirectionEvents.get(Direction.DIRECTION_EAST).add(Objects.requireNonNull(event, "Event cannot be null."));
    }

    @Override
    public void insertExitSouthEvent(Event event) {
        exitDirectionEvents.get(Direction.DIRECTION_SOUTH).add(Objects.requireNonNull(event, "Event cannot be null."));
    }

    @Override
    public void insertExitWestEvent(Event event) {
        exitDirectionEvents.get(Direction.DIRECTION_WEST).add(Objects.requireNonNull(event, "Event cannot be null."));
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
    }
}
