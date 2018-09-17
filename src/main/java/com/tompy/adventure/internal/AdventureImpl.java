package com.tompy.adventure.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.ActionType;
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
        Area room1 = entityService.createAreaBuilder().name("Room1").description(
            "Well lit empty room with bright white walls and dark blue carpet.  In the center of the room sits a " +
            "large wooden box.")
            .searchDescription("There is nothing special about this room.  There is a door to the north.")
            .searchDirectionDescription(DIRECTION_NORTH, "An old wooden door.").build();

        Area room2 = entityService.createAreaBuilder().name("Room2").description(
            "A hallway that bends to the right.  It is well let with white walls and a dark blue well worn carpet" +
            ".").searchDescription("Along both sides of the hallway are portraits of previous tenants, some quite old.")
            .searchDirectionDescription(DIRECTION_SOUTH, "An old wooden door")
            .searchDirectionDescription(DIRECTION_EAST, "A rusty iron door.").build();

        Area room3 = entityService.createAreaBuilder().name("Room3")
            .description("This room has a single torch, making it smoky and dark.")
            .searchDescription("There is nothing to see, but the smoke seems to be building up.")
            .searchDirectionDescription(DIRECTION_WEST, "An iron door.")
            .searchDirectionDescription(DIRECTION_NORTH, "A dark curtain seems to cover something.").build();

        Area room4 = entityService.createAreaBuilder().name("Room4").description("You have made it outside").build();

        Event e1 = entityService.createEventBuilder().name("room1Describe").actionType(ActionType.DESCRIBE)
            .triggerType(TriggerType.ALWAYS).longName("describe event").memo("enter room2")
            .responses(new String[]{"this is line1", "and line 2"}).entity(room2).build();

        room2.insertEnterEvent(e1);


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
            entityService.createFeatureBuilder().type(FEATURE_CHEST).name("Chest").longName("dusty chest")
                .description("an old and dusty chest").build();
        Feature room2EastDoor =
            entityService.createFeatureBuilder().type(FEATURE_DOOR).name("Door").longName("iron door")
                .description("iron door").exit(exit2).build();
        Feature room2SouthDoor =
            entityService.createFeatureBuilder().type(FEATURE_DOOR).name("Door").longName("oak door")
                .description("oak door").exit(exit1).build();
        Feature room3NorthDoor =
            entityService.createFeatureBuilder().type(FEATURE_DOOR).name("Curtain").longName("dark curtain")
                .description("long dark curtain").exit(exit3).build();

        EntityFacade room1ChestLock = entityFacadeBuilderFactory.builder().entity(room1Chest).attribute(LOCKED).build();
        EntityFacade room2EastDoorLock =
            entityFacadeBuilderFactory.builder().entity(room2EastDoor).attribute(LOCKED).build();

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
