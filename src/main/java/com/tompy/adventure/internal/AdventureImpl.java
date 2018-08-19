package com.tompy.adventure.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.area.api.Area;
import com.tompy.area.api.Exit;
import com.tompy.area.internal.AreaImpl;
import com.tompy.area.internal.ExitImpl;
import com.tompy.attribute.internal.AttributeManagerFactoryImpl;
import com.tompy.directive.Direction;
import com.tompy.directive.FeatureType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityFacade;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.internal.EntityFacadeBuilderFactoryImpl;
import com.tompy.entity.internal.EntityServiceImpl;
import com.tompy.entity.item.api.Item;
import com.tompy.player.api.Player;

import java.util.HashMap;
import java.util.Map;

import static com.tompy.attribute.api.Attribute.LOCKED;
import static com.tompy.directive.FeatureType.FEATURE_CHEST;
import static com.tompy.directive.FeatureType.FEATURE_DOOR;
import static com.tompy.directive.ItemType.ITEM_KEY;

public class AdventureImpl implements Adventure {
    private EntityService entityService = new EntityServiceImpl(new AttributeManagerFactoryImpl());
    private final EntityFacadeBuilderFactoryImpl entityFacadeFactory = new EntityFacadeBuilderFactoryImpl(
            entityService);
    private Map<String, Area> areas = new HashMap<>();
    private boolean proceed = false;

    public AdventureImpl() {
        // Areas
        Area room3 = AreaImpl.createBuilder(entityService).searchDescription(
                "you find a portal to where yo are now").name("Room3").description(
                "A big empty room, how disappointing").compartment(
                entityService.createCompartmentBuilder().name("nothing").description("nothing").build()).build();
        Area room2 = AreaImpl.createBuilder(entityService).searchDescription("Nothing special").name(
                "Room2").description("Smoke is making it difficult to breath").searchDirectionDescription(
                Direction.DIRECTION_NORTH, "A crack in the wall, this is where the smoke must be coming from").build();
        Area room1 = AreaImpl.createBuilder(entityService).searchDescription(
                "You discover that you are in this room").name("Room1").description(
                "Look around, this room is exactly like that").build();

        // Exits
        Exit exit12 = ExitImpl.createBuilder().direction(Direction.DIRECTION_NORTH).area(room2).build();
        Exit exit23 = ExitImpl.createBuilder().direction(Direction.DIRECTION_EAST).area(room3).build();
        Exit exit21 = ExitImpl.createBuilder().direction(Direction.DIRECTION_SOUTH).area(room1).build();
        Exit exit32 = ExitImpl.createBuilder().direction(Direction.DIRECTION_WEST).area(room2).build();

        room1.installExit(exit12);
        room2.installExit(exit23);
        room2.installExit(exit21);
        room3.installExit(exit32);

        // Features
        Feature room2Chest = entityService.createFeatureBuilder().type(FEATURE_CHEST).name(
                "Chest").longName("dusty chest").description("an old and dusty chest").build();
        Feature room1Desk = entityService.createFeatureBuilder().type(FEATURE_CHEST).name("Desk").longName(
                "roll top desk").description("an old roll top desk").build();
        Feature room2EastDoor = entityService.createFeatureBuilder().type(FEATURE_DOOR).name("Door").longName(
                "north door").description("oak door").build();

        EntityFacade room2ChestLock = entityFacadeFactory.builder().entity(room2Chest).attribute(LOCKED).build();
        EntityFacade room2EastDoorLock = entityFacadeFactory.builder().entity(room2EastDoor).attribute(LOCKED).build();

        EntityUtil.add(room2ChestLock);
        EntityUtil.add(room2EastDoorLock);

        room2.installFeature(room2Chest, Direction.DIRECTION_NORTH);
        room1.installFeature(room1Desk, null);


        // Items
        Item key1 = entityService.createItemBuilder().type(ITEM_KEY).name("key").longName("blue key").description(
                "a shiny blue key").target(room2ChestLock).build();


        // Summary
        areas.put(room1.getName(), room1);
        areas.put(room2.getName(), room2);
        areas.put(room3.getName(), room3);
    }

    @Override
    public boolean proceed() {
        return proceed;
    }

    @Override
    public void start(Player player) {
        player.setArea(areas.get("Room1"));
        proceed = true;
    }

    @Override
    public void stop(Player player) {
        //TODO
        //Persist
        proceed = false;
    }

    @Override
    public Area getArea(String name) {
        return areas.get(name);
    }
}
