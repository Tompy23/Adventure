package com.tompy.state.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.io.UserInput;
import com.tompy.player.api.Player;
import com.tompy.state.api.AdventureState;
import com.tompy.state.api.AdventureStateFactory;

import java.io.PrintStream;
import java.util.Objects;

public class AdventureStateFactoryImpl implements AdventureStateFactory {
    private final Player player;
    private final Adventure adventure;
    private final UserInput userInput;
    private final PrintStream outputStream;


    public AdventureStateFactoryImpl(Player player, Adventure adventure, UserInput userInput,
        PrintStream outputStream) {
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
        this.userInput = Objects.requireNonNull(userInput, "UserInput cannot be null.");
        this.outputStream = Objects.requireNonNull(outputStream, "Output Stream cannot be null.");
    }

    @Override
    public AdventureState createExploreState() {
        return new StateExploreImpl(player, adventure, userInput, outputStream);
    }

    @Override
    public AdventureState createEncounterState() {
        return null;
    }

    @Override
    public AdventureState createCombatState() {
        return null;
    }
}
