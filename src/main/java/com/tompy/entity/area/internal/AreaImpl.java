package com.tompy.entity.area.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.adventure.internal.AdventureUtils;
import com.tompy.attribute.api.Attribute;
import com.tompy.directive.Direction;
import com.tompy.directive.EventType;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.area.api.Area;
import com.tompy.entity.area.api.AreaBuilder;
import com.tompy.entity.compartment.internal.CompartmentBuilderHelperImpl;
import com.tompy.entity.compartment.internal.CompartmentImpl;
import com.tompy.entity.feature.api.Feature;
import com.tompy.exit.api.Exit;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class AreaImpl extends CompartmentImpl implements Area {
    private static final Logger LOGGER = LogManager.getLogger(AreaImpl.class);
    protected final String searchDescription;
    protected Map<Direction, Exit> exits = new HashMap<>();
    protected List<Feature> features = new ArrayList<>();
    protected Map<Direction, List<Feature>> directionFeatures = new HashMap<>();

    protected AreaImpl(Long key, String name, List<String> descriptors, String description, String searchDescription,
        EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        this.searchDescription = searchDescription;
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

        returnValue.addAll(entityService.handle(this, EventType.EVENT_AREA_ENTER, player, adventure));
        returnValue
            .addAll(entityService.handle(this, AdventureUtils.getAreaEnterEventType(direction), player, adventure));

        player.visitArea(name);

        return returnValue;
    }

    @Override
    public List<Response> exit(Direction direction, Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Exiting room [{}] in direction [{}]", this.getName(), direction.name());

        returnValue.addAll(entityService.handle(this, EventType.EVENT_AREA_EXIT, player, adventure));
        returnValue
            .addAll(entityService.handle(this, AdventureUtils.getAreaExitEventType(direction), player, adventure));

        return returnValue;
    }

    @Override
    public List<Response> search(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Searching room [{}]", this.getName());

        returnValue.addAll(entityService.handle(this, EventType.EVENT_AREA_SEARCH, player, adventure));

        if (!features.isEmpty()) {
            returnValue.add(responseFactory.createBuilder().source(name).text("Also in the room...").build());
            features.stream().forEach((f) -> returnValue.addAll(f.search(player, adventure)));
        }

        if (!items.isEmpty()) {
            returnValue.add(responseFactory.createBuilder().source(name).text("Items in the room...").build());
            items.stream().forEach((i) -> {
                returnValue
                    .add(this.responseFactory.createBuilder().source(i.getName()).text(i.getDescription()).build());
                entityService.add(i, Attribute.VISIBLE);
            });
        }

        player.searchArea(name);

        return returnValue;
    }

    @Override
    public List<Response> searchDirection(Direction direction, Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        boolean nothing = true;
        LOGGER.info("Searching room [{}] in direction [{}]", this.getName(), direction.name());

        EventType type = AdventureUtils.getAreaSearchEventType(direction);
        if (!entityService.get(this, type).isEmpty()) {
            returnValue.addAll(entityService.handle(this, type, player, adventure));
        }

        if (directionFeatures.containsKey(direction)) {
            directionFeatures.get(direction).stream().forEach((f) -> returnValue.addAll(f.search(player, adventure)));
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
    public String getName() {
        return name;
    }

    public static class AreaBuilderImpl extends CompartmentBuilderHelperImpl implements AreaBuilder {
        protected String name;
        protected String description;
        protected String searchDescription;
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
            return new AreaImpl(key, name, this.buildDescriptors(), description, searchDescription, entityService);
        }
    }
}
