package com.tompy.state.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.io.UserInput;
import com.tompy.player.api.Player;
import com.tompy.state.api.AdventureState;
import com.tompy.state.api.AdventureStateCombatBuilder;

import java.io.PrintStream;
import java.util.Objects;

public class StateCombatImpl extends AdventureStateBaseImpl implements AdventureState {

    public StateCombatImpl(Player player, Adventure adventure, UserInput userInput, PrintStream outStream) {
        super(player, adventure, userInput, outStream);
    }

    public static AdventureStateCombatBuilder createBuilder(Player player, Adventure adventure, UserInput userInput,
            PrintStream outputStream) {
        return new AdventureStateCombatBuilderImpl(player, adventure, userInput, outputStream);
    }

    @Override public void start() {

    }

    @Override public void process() {

    }

    @Override public void end() {

    }

    public static final class AdventureStateCombatBuilderImpl implements AdventureStateCombatBuilder {
        private final Player player;
        private final Adventure adventure;
        private final UserInput userInput;
        private final PrintStream outputStream;

        public AdventureStateCombatBuilderImpl(Player player, Adventure adventure, UserInput userInput,
                PrintStream outputStream) {
            this.player = Objects.requireNonNull(player, "Player cannot be null.");
            this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
            this.userInput = Objects.requireNonNull(userInput, "UserInput cannot be null.");
            this.outputStream = Objects.requireNonNull(outputStream, "Output Stream cannot be null.");
        }

        @Override public AdventureState build() {
            return new StateCombatImpl(player, adventure, userInput, outputStream);
        }
    }
}
