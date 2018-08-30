package com.tompy.adventure.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityFacade;
import com.tompy.entity.api.EntityFacadeBuilderFactory;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.area.api.Area;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.item.api.Item;
import com.tompy.exit.api.Exit;
import com.tompy.exit.api.ExitBuilderFactory;
import com.tompy.io.UserInput;
import com.tompy.player.api.Player;

import java.util.Objects;

import static com.tompy.attribute.api.Attribute.*;
import static com.tompy.directive.Direction.*;
import static com.tompy.directive.FeatureType.FEATURE_CHEST;
import static com.tompy.directive.FeatureType.FEATURE_DOOR;
import static com.tompy.directive.ItemType.ITEM_GEM;
import static com.tompy.directive.ItemType.ITEM_KEY;

public class AdventureImpl implements Adventure {
    private final EntityService entityService;
    private final EntityFacadeBuilderFactory entityFacadeBuilderFactory;
    private final ExitBuilderFactory exitBuilderFactory;
    private final UserInput userInput;
    private boolean proceed = false;

    public AdventureImpl(EntityService entityService, EntityFacadeBuilderFactory entityFacadeBuilderFactory,
                         ExitBuilderFactory exitBuilderFactory, UserInput userInput) {
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        this.entityFacadeBuilderFactory = Objects.requireNonNull(entityFacadeBuilderFactory,
                "Entity Facade Builder Factory cannot be null.");
        this.exitBuilderFactory = Objects.requireNonNull(exitBuilderFactory, "Exit Builder Factory cannot be null.");
        this.userInput = Objects.requireNonNull(userInput, "User Input cannot be null.");
    }

    /**
     * README README README
     * <p>
     * New way for Doors/Exits
     * <p>
     * There will now be a single Exit between 2 areas.  The Exit will have no concept of direction in it.
     * <p>
     * Each Area will have a size 4 array (representing direction like Search Direction array) containing an Exit if
     * appropriate.  It is likely that an exit will
     * be shared by 2 Areas.
     * <p>
     * An Exit can still be open or closed, and thus will be open or closed for both directions.
     * <p>
     * An Exit may contain a single FeatureDoorImpl (create a FeatureDoorInterface?).  When either Area is searched
     * in the direction of the exit this FeatureDoorImpl Description will be contained in Search Response
     * <p>
     * README README README
     */

    @Override
    public void create() {
        // Areas
        Area room3 = entityService.createAreabuilder().searchDescription("you find a portal to where yo are now").name(
                "Room3").description("A big empty room, how disappointing").compartmentName(
                "nothing").compartmentDescription("nothing").build();
        Area room2 = entityService.createAreabuilder().searchDescription("Nothing special").name("Room2").description(
                "Smoke is making it difficult to breath").searchDirectionDescription(DIRECTION_NORTH,
                "A crack in the wall, this is where the smoke must be coming from").build();
        Area room1 = entityService.createAreabuilder().searchDescription("You discover that you are in this room").name(
                "Room1").description("Look around, this room is exactly like that").build();

        // Exits
        Exit exit1 = exitBuilderFactory.builder().area(room2).area(room1).state(true).build();
        Exit exit2 = exitBuilderFactory.builder().area(room3).area(room2).state(false).build();

        room1.installExit(DIRECTION_NORTH, exit1);
        room2.installExit(DIRECTION_SOUTH, exit1);
        room2.installExit(DIRECTION_EAST, exit2);
        room3.installExit(DIRECTION_WEST, exit2);

        // Features
        Feature room2Chest = entityService.createFeatureBuilder().type(FEATURE_CHEST).name("Chest").longName(
                "dusty chest").description("an old and dusty chest").build();
        Feature room1Desk = entityService.createFeatureBuilder().type(FEATURE_CHEST).name("Desk").longName(
                "roll top desk").description("an old roll top desk").build();
        Feature room2EastDoor = entityService.createFeatureBuilder().type(FEATURE_DOOR).name("Door").longName(
                "north door").description("oak door").build();
        Feature room2SouthDoor = entityService.createFeatureBuilder().type(FEATURE_DOOR).name("Door").longName(
                "south door").description("oak door").build();

        EntityFacade room2ChestLock = entityFacadeBuilderFactory.builder().entity(room2Chest).attribute(LOCKED).build();
        EntityFacade room2EastDoorLock = entityFacadeBuilderFactory.builder().entity(room2EastDoor).attribute(
                LOCKED).build();
        EntityFacade room1NorthDoorOpen = entityFacadeBuilderFactory.builder().entity(room2EastDoor).attribute(
                OPEN).build();
        EntityFacade room2SouthDoorOpen = entityFacadeBuilderFactory.builder().entity(room2SouthDoor).attribute(
                OPEN).build();

        EntityUtil.add(room2ChestLock);
        EntityUtil.add(room2EastDoorLock);
        EntityUtil.add(room1NorthDoorOpen);
        EntityUtil.add(room2SouthDoorOpen);

        room2.installFeature(room2Chest, DIRECTION_NORTH);
        room2.installFeature(room2EastDoor, null);
        room1.installFeature(room1Desk, null);


        // Items.
        Item key1 = entityService.createItemBuilder().type(ITEM_KEY).name("key").longName("blue key").description(
                "a shiny blue key").target(room2ChestLock).build();
        Item gem1 = entityService.createItemBuilder().type(ITEM_GEM).name("Ruby").longName("red ruby").description(
                "sparkling red ruby").build();

        EntityFacade gem1Value = entityFacadeBuilderFactory.builder().entity(gem1).attribute(VALUE).build();
        EntityUtil.add(gem1Value, 5);

        room1.addItem(key1);
        room2Chest.addItem(gem1);


        // Summary
        entityService.addArea(room1);
        entityService.addArea(room2);
        entityService.addArea(room3);
    }

    @Override
    public boolean proceed() {
        return proceed;
    }

    @Override
    public void start(Player player) {
        player.setArea(entityService.getAreaByName("Room1"));
        proceed = true;
    }

    @Override
    public void stop(Player player) {
        //TODO Persist
        proceed = false;
    }

    @Override
    public UserInput getUI() {
        return userInput;
    }
}
