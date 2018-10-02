package com.tompy.state.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.encounter.api.Encounter;
import com.tompy.io.UserInput;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import com.tompy.state.api.AdventureState;
import com.tompy.state.api.AdventureStateEncounterBuilder;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StateEncounterImpl extends AdventureStateBaseImpl implements AdventureState {
    private final Encounter encounter;

    public StateEncounterImpl(Player player, Adventure adventure, UserInput userInput, PrintStream outStream,
            Encounter encounter, EntityService entityService) {
        super(player, adventure, userInput, outStream, entityService);
        this.encounter = Objects.requireNonNull(encounter, "Encounter cannot be null.");
    }

    public static AdventureStateEncounterBuilder createBuilder(Player player, Adventure adventure, UserInput userInput,
            PrintStream outputStream, EntityService entityService) {
        return new AdventureStateEncounterBuilderImpl(player, adventure, userInput, outputStream, entityService);
    }

    @Override
    public void start() {

    }

    @Override
    public void process() {
        // Call the encounter's "list options", returns Map<Long, String>
        Map<Long, String> options = encounter.getOptions();

        // Call UserInput Make choice, returns Long
        Long option = userInput.getSelection(options);

        // Call the encounter and pass the Long selected, returns List<Response>
        List<Response> responses = encounter.act(option);

        // Render the list of responses to outStream
        responses.stream().forEachOrdered((r) -> outStream.println(r.render()));
    }

    @Override
    public void end() {

    }

    public static final class AdventureStateEncounterBuilderImpl implements AdventureStateEncounterBuilder {
        private final Player player;
        private final Adventure adventure;
        private final UserInput userInput;
        private final PrintStream outputStream;
        private final EntityService entityService;
        private Encounter encounter;


        public AdventureStateEncounterBuilderImpl(Player player, Adventure adventure, UserInput userInput,
                PrintStream outputStream, EntityService entityService) {
            this.player = Objects.requireNonNull(player, "Player cannot be null.");
            this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
            this.userInput = Objects.requireNonNull(userInput, "UserInput cannot be null.");
            this.outputStream = Objects.requireNonNull(outputStream, "Output Stream cannot be null.");
            this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        }

        @Override
        public AdventureState build() {
            return new StateEncounterImpl(player, adventure, userInput, outputStream, encounter, entityService);
        }

        @Override
        public AdventureStateEncounterBuilder encounter(Encounter encounter) {
            this.encounter = encounter;
            return this;
        }
    }
}
