package com.tompy.adventure.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.ActionType;
import com.tompy.directive.EventType;
import com.tompy.directive.TriggerType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityFacade;
import com.tompy.entity.api.EntityFacadeBuilderFactory;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.area.api.Area;
import com.tompy.entity.event.api.Event;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.item.api.Item;
import com.tompy.exit.api.Exit;
import com.tompy.exit.api.ExitBuilderFactory;
import com.tompy.io.UserInput;
import com.tompy.player.api.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

import static com.tompy.attribute.api.Attribute.LOCKED;
import static com.tompy.attribute.api.Attribute.VALUE;
import static com.tompy.directive.Direction.*;
import static com.tompy.directive.EventType.FEATURE_SEARCH;
import static com.tompy.directive.FeatureType.FEATURE_CHEST;
import static com.tompy.directive.FeatureType.FEATURE_DOOR;
import static com.tompy.directive.ItemType.ITEM_GEM;
import static com.tompy.directive.ItemType.ITEM_KEY;

public class AdventureImpl implements Adventure {
    private static final Logger LOGGER = LogManager.getLogger(AdventureImpl.class);
    protected final EntityService entityService;
    protected final EntityFacadeBuilderFactory entityFacadeBuilderFactory;
    protected final ExitBuilderFactory exitBuilderFactory;
    protected final UserInput userInput;
    protected boolean proceed = false;

    public AdventureImpl(EntityService entityService, EntityFacadeBuilderFactory entityFacadeBuilderFactory,
        ExitBuilderFactory exitBuilderFactory, UserInput userInput) {
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        this.entityFacadeBuilderFactory =
            Objects.requireNonNull(entityFacadeBuilderFactory, "Entity Facade Builder Factory cannot be null.");
        this.exitBuilderFactory = Objects.requireNonNull(exitBuilderFactory, "Exit Builder Factory cannot be null.");
        this.userInput = Objects.requireNonNull(userInput, "User Input cannot be null.");
    }

    @Override
    public void create() {
        LOGGER.info("Creating adventure...");
        // Areas
        Area room1 = entityService.createAreaBuilder().name("Room1").build();

        Area room2 = entityService.createAreaBuilder().name("Room2").build();

        Area room3 = entityService.createAreaBuilder().name("Room3").build();

        Area room4 = entityService.createAreaBuilder().name("Room4").build();


        // Events
        Event room1Enter = entityService.createEventBuilder().name("room1Describe").actionType(ActionType.DESCRIBE)
            .triggerType(TriggerType.ALWAYS).longName("describe event").responses(
                "Well lit empty room with bright white walls and dark blue carpet.  In the center of the room " +
                "sits large wooden box.").entity(room1).build();

        Event room1Search = entityService.createEventBuilder().name("room1Search").actionType(ActionType.DESCRIBE)
            .triggerType(TriggerType.ALWAYS).longName("")
            .responses("There is nothing special about this room.  There is a door to the north.").entity(room1)
            .build();

        Event room1SearchNorth =
            entityService.createEventBuilder().name("room1SearchNorth").actionType(ActionType.DESCRIBE)
                .triggerType(TriggerType.ALWAYS).longName("").responses("An old wooden door").entity(room1).build();

        entityService.add(room1, EventType.AREA_ENTER, room1Enter);
        entityService.add(room1, EventType.AREA_SEARCH, room1Search);
        entityService.add(room1, EventType.AREA_NORTH_SEARCH, room1SearchNorth);

        Event room2Enter = entityService.createEventBuilder().name("room2Enter").actionType(ActionType.DESCRIBE)
            .triggerType(TriggerType.ALWAYS).longName("").responses(
                "A hallway that bends to the right.  It is well let with white walls and a dark blue well worn carpet" +
                ".").entity(room2).build();

        Event room2Search = entityService.createEventBuilder().name("room2Search").actionType(ActionType.DESCRIBE)
            .triggerType(TriggerType.ALWAYS).longName("")
            .responses("Along both sides of the hallway are portraits of previous tenants, some quite old.")
            .entity(room2).build();

        Event room2SearchSouth =
            entityService.createEventBuilder().name("room2SearchSouth").actionType(ActionType.DESCRIBE)
                .triggerType(TriggerType.ALWAYS).longName("").responses("An old wooden door").entity(room2).build();

        Event room2SearchEast =
            entityService.createEventBuilder().name("room2SearchEast").actionType(ActionType.DESCRIBE)
                .triggerType(TriggerType.ALWAYS).longName("").responses("A rusty iron door").entity(room2).build();

        entityService.add(room2, EventType.AREA_ENTER, room2Enter);
        entityService.add(room2, EventType.AREA_SEARCH, room2Search);
        entityService.add(room2, EventType.AREA_SOUTH_SEARCH, room2SearchSouth);
        entityService.add(room2, EventType.AREA_EAST_SEARCH, room2SearchEast);

        Event room3Enter = entityService.createEventBuilder().name("room3Enter").actionType(ActionType.DESCRIBE)
            .triggerType(TriggerType.ALWAYS).longName("")
            .responses("This room has a single torch, making it smoky and dark.").entity(room3).build();

        Event room3Search = entityService.createEventBuilder().name("room3Search").actionType(ActionType.DESCRIBE)
            .triggerType(TriggerType.ALWAYS).longName("")
            .responses("There is nothing to see, but the smoke seems to be building up.").entity(room3).build();

        Event room3SearchWest =
            entityService.createEventBuilder().name("room3SearchSouth").actionType(ActionType.DESCRIBE)
                .triggerType(TriggerType.ALWAYS).longName("").responses("An iron door").entity(room3).build();

        Event room3SearchNorth =
            entityService.createEventBuilder().name("room3SearchNorth").actionType(ActionType.DESCRIBE)
                .triggerType(TriggerType.ALWAYS).longName("").responses("A dark curtain seems to cover something.")
                .entity(room3).build();

        entityService.add(room3, EventType.AREA_ENTER, room3Enter);
        entityService.add(room3, EventType.AREA_SEARCH, room3Search);
        entityService.add(room3, EventType.AREA_WEST_SEARCH, room3SearchWest);
        entityService.add(room3, EventType.AREA_NORTH_SEARCH, room3SearchNorth);

        Event room4Enter = entityService.createEventBuilder().name("room4Enter").actionType(ActionType.DESCRIBE)
            .triggerType(TriggerType.ALWAYS).longName("").responses("You have made it outside").entity(room4).build();

        entityService.add(room4, EventType.AREA_ENTER, room4Enter);


        // Exits
        Exit exit1 = exitBuilderFactory.builder().area(room2).area(room1).state(false).build();
        Exit exit2 = exitBuilderFactory.builder().area(room3).area(room2).state(false).build();
        Exit exit3 = exitBuilderFactory.builder().area(room3).area(room4).state(false).build();

        room1.installExit(DIRECTION_NORTH, exit1);
        room2.installExit(DIRECTION_SOUTH, exit1);
        room2.installExit(DIRECTION_EAST, exit2);
        room3.installExit(DIRECTION_WEST, exit2);
        room3.installExit(DIRECTION_NORTH, exit3);


        // Features
        Feature room1Chest =
            entityService.createFeatureBuilder().type(FEATURE_CHEST).name("Chest").longName("dusty chest").build();
        Feature room2EastDoor =
            entityService.createFeatureBuilder().type(FEATURE_DOOR).name("Door").longName("iron door").exit(exit2)
                .build();
        Feature room2SouthDoor =
            entityService.createFeatureBuilder().type(FEATURE_DOOR).name("Door").longName("oak door").exit(exit1)
                .build();
        Feature room3NorthDoor =
            entityService.createFeatureBuilder().type(FEATURE_DOOR).name("Curtain").longName("dark curtain").exit(exit3)
                .build();

        EntityFacade room1ChestLock = entityFacadeBuilderFactory.builder().entity(room1Chest).attribute(LOCKED).build();
        EntityFacade room2EastDoorLock =
            entityFacadeBuilderFactory.builder().entity(room2EastDoor).attribute(LOCKED).build();

        Event room1ChestSearch =
            entityService.createEventBuilder().name("room1ChestSearch").actionType(ActionType.DESCRIBE)
                .triggerType(TriggerType.ALWAYS).longName("").responses("an old and dusty chest").entity(room1Chest)
                .build();
        entityService.add(room1Chest, FEATURE_SEARCH, room1ChestSearch);

        Event room2EastDoorSearch =
            entityService.createEventBuilder().name("room2EastDoorSearch").actionType(ActionType.DESCRIBE)
                .triggerType(TriggerType.ALWAYS).longName("").responses("iron door").entity(room2EastDoor).build();
        entityService.add(room2EastDoor, FEATURE_SEARCH, room2EastDoorSearch);

        Event room2SouthDoorSearch =
            entityService.createEventBuilder().name("room2SouthDoorSearch").actionType(ActionType.DESCRIBE)
                .triggerType(TriggerType.ALWAYS).longName("").responses("oak door").entity(room2SouthDoor).build();
        entityService.add(room2SouthDoor, FEATURE_SEARCH, room2SouthDoorSearch);

        Event room3NorthDoorSearch =
            entityService.createEventBuilder().name("room3NorthDoorSearch").actionType(ActionType.DESCRIBE)
                .triggerType(TriggerType.ALWAYS).longName("").responses("long dark curtain").entity(room3NorthDoor)
                .build();
        entityService.add(room3NorthDoor, FEATURE_SEARCH, room3NorthDoorSearch);


        EntityUtil.add(room1ChestLock);
        EntityUtil.add(room2EastDoorLock);

        room1.installFeature(room1Chest, null);
        room1.installFeature(room2SouthDoor, DIRECTION_NORTH);
        room2.installFeature(room2EastDoor, DIRECTION_EAST);
        room2.installFeature(room2SouthDoor, DIRECTION_SOUTH);
        room3.installFeature(room2EastDoor, DIRECTION_WEST);
        room3.installFeature(room3NorthDoor, DIRECTION_NORTH);


        // Items.
        Item key1 = entityService.createItemBuilder().type(ITEM_KEY).name("key").longName("blue key")
            .description("shiny blue key").targetFeature(room2EastDoor).build();
        Item key2 = entityService.createItemBuilder().type(ITEM_KEY).name("key").longName("iron key")
            .description("dull iron key").targetFeature(room1Chest).build();
        Item gem1 = entityService.createItemBuilder().type(ITEM_GEM).name("Ruby").longName("red ruby")
            .description("sparkling red ruby").build();

        EntityFacade gem1Value = entityFacadeBuilderFactory.builder().entity(gem1).attribute(VALUE).build();
        EntityUtil.add(gem1Value, 5);

        room1Chest.addItem(gem1);
        room1.addItem(key1);
        room3.addItem(key2);

        // Summary
        entityService.addArea(room1);
        entityService.addArea(room2);
        entityService.addArea(room3);
        entityService.addArea(room4);

        LOGGER.info("...complete.");
    }

    @Override
    public void start(Player player) {
        LOGGER.info("Starting the adventure.");
        player.setArea(entityService.getAreaByName("Room1"));
        proceed = true;
    }

    @Override
    public void stop(Player player) {
        LOGGER.info("Stopping the adventure.");
        //TODO Persist
        proceed = false;
    }

    @Override
    public boolean proceed() {
        return proceed;
    }

    @Override
    public UserInput getUI() {
        return userInput;
    }
}
