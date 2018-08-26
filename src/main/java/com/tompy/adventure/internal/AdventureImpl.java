package com.tompy.adventure.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.Direction;
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

import static com.tompy.attribute.api.Attribute.LOCKED;
import static com.tompy.directive.FeatureType.FEATURE_CHEST;
import static com.tompy.directive.FeatureType.FEATURE_DOOR;
import static com.tompy.directive.ItemType.ITEM_KEY;

public class AdventureImpl implements Adventure {
    private final EntityService entityService;
    private final EntityFacadeBuilderFactory entityFacadeBuilderFactory;
    private final ExitBuilderFactory exitBuilderFactory;
    private boolean proceed = false;
    private final UserInput userInput;

    public AdventureImpl(EntityService entityService, EntityFacadeBuilderFactory entityFacadeBuilderFactory,
                         ExitBuilderFactory exitBuilderFactory, UserInput userInput) {
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        this.entityFacadeBuilderFactory = Objects.requireNonNull(entityFacadeBuilderFactory,
                "Entity Facade Builder Factory cannot be null.");
        this.exitBuilderFactory = Objects.requireNonNull(exitBuilderFactory, "Exit Builder Factory cannot be null.");
        this.userInput = Objects.requireNonNull(userInput, "User Input cannot be null.");
    }

    @Override
    public void create() {
        // Areas
        Area room3 = entityService.createAreabuilder().searchDescription("you find a portal to where yo are now").name(
                "Room3").description("A big empty room, how disappointing").compartmentName(
                "nothing").compartmentDescription("nothing").build();
        Area room2 = entityService.createAreabuilder().searchDescription("Nothing special").name("Room2").description(
                "Smoke is making it difficult to breath").searchDirectionDescription(Direction.DIRECTION_NORTH,
                "A crack in the wall, this is where the smoke must be coming from").build();
        Area room1 = entityService.createAreabuilder().searchDescription("You discover that you are in this room").name(
                "Room1").description("Look around, this room is exactly like that").build();

        // Exits
        Exit exit12 = exitBuilderFactory.builder().direction(Direction.DIRECTION_NORTH).area(room2).build();
        Exit exit23 = exitBuilderFactory.builder().direction(Direction.DIRECTION_EAST).area(room3).build();
        Exit exit21 = exitBuilderFactory.builder().direction(Direction.DIRECTION_SOUTH).area(room1).build();
        Exit exit32 = exitBuilderFactory.builder().direction(Direction.DIRECTION_WEST).area(room2).build();

        room1.installExit(exit12);
        room2.installExit(exit23);
        room2.installExit(exit21);
        room3.installExit(exit32);

        // Features
        Feature room2Chest = entityService.createFeatureBuilder().type(FEATURE_CHEST).name("Chest").longName(
                "dusty chest").description("an old and dusty chest").build();
        Feature room1Desk = entityService.createFeatureBuilder().type(FEATURE_CHEST).name("Desk").longName(
                "roll top desk").description("an old roll top desk").build();
        Feature room2EastDoor = entityService.createFeatureBuilder().type(FEATURE_DOOR).name("Door").longName(
                "north door").description("oak door").build();

        EntityFacade room2ChestLock = entityFacadeBuilderFactory.builder().entity(room2Chest).attribute(LOCKED).build();
        EntityFacade room2EastDoorLock = entityFacadeBuilderFactory.builder().entity(room2EastDoor).attribute(
                LOCKED).build();

        EntityUtil.add(room2ChestLock);
        EntityUtil.add(room2EastDoorLock);

        room2.installFeature(room2Chest, Direction.DIRECTION_NORTH);
        room1.installFeature(room1Desk, null);


        // Items
        Item key1 = entityService.createItemBuilder().type(ITEM_KEY).name("key").longName("blue key").description(
                "a shiny blue key").target(room2ChestLock).build();

        room1.addItem(key1);


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
