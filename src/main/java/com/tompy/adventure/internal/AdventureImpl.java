package com.tompy.adventure.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.area.api.Area;
import com.tompy.area.api.AreaBuilderFactory;
import com.tompy.area.api.Exit;
import com.tompy.area.api.ExitBuilderFactory;
import com.tompy.area.internal.AreaImpl;
import com.tompy.area.internal.ExitImpl;
import com.tompy.directive.Direction;
import com.tompy.directive.FeatureType;
import com.tompy.feature.api.Feature;
import com.tompy.feature.api.FeatureBuilderFactory;
import com.tompy.feature.internal.FeatureBasicImpl;
import com.tompy.player.api.Player;

import java.util.HashMap;
import java.util.Map;

public class AdventureImpl implements Adventure {
    private Map<String, Area> areas = new HashMap<>();
    private FeatureBuilderFactory featureFactory = FeatureBasicImpl.createBuilderFactory();
    private ExitBuilderFactory exitFactory = ExitImpl.createBuilderFactory();
    private AreaBuilderFactory areaFactory = AreaImpl.createBuilderFactory();
    private boolean proceed = false;

    public AdventureImpl() {
        // Items

        // Areas
        Area room3 = areaFactory.createBuilder()
                .searchDescription("you find a portal to where yo are now")
                .name("Room3")
                .description("A big empty room, how disappointing")
                .build();
        Area room2 = areaFactory.createBuilder()
                .searchDescription("Nothing special")
                .name("Room2")
                .description("Smoke is making it difficult to breath")
                .searchDirectionDescription(Direction.DIRECTION_NORTH, "A crack in the wall, this is where the smoke must be coming from")
                .build();
        Area room1 = areaFactory.createBuilder()
                .searchDescription("You discover that you are in this room")
                .name("Room1")
                .description("Look around, this room is exactly like that")
                .build();

        // Exits
        Exit exit12 = exitFactory.createBuilder().direction(Direction.DIRECTION_NORTH).area(room2).build();
        Exit exit23 = exitFactory.createBuilder().direction(Direction.DIRECTION_EAST).area(room3).build();
        Exit exit21 = exitFactory.createBuilder().direction(Direction.DIRECTION_SOUTH).area(room1).build();
        Exit exit32 = exitFactory.createBuilder().direction(Direction.DIRECTION_WEST).area(room2).build();

        room1.installExit(exit12);
        room2.installExit(exit23);
        room2.installExit(exit21);
        room3.installExit(exit32);

        // Features
        Feature room2Chest = featureFactory.createBuilder()
                .type(FeatureType.FEATURE_CHEST)
                .name("Chest")
                .description("an old and dusty chest")
                .build();
        Feature room1Desk = featureFactory.createBuilder()
                .type(FeatureType.FEATURE_BASIC)
                .name("Desk")
                .description("an old roll top desk")
                .build();

        room2.installFeature(room2Chest, Direction.DIRECTION_NORTH);
        room1.installFeature(room1Desk, null);

        // Summary
        areas.put(room1.getName(), room1);
        areas.put(room2.getName(), room2);
        areas.put(room3.getName(), room3);
    }

    @Override
    public boolean proceed() { return proceed; }

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
