package com.tompy.adventure.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.api.EntityFacadeBuilderFactory;
import com.tompy.entity.api.EntityService;
import com.tompy.exit.api.ExitBuilderFactory;
import com.tompy.io.UserInput;
import com.tompy.player.api.Player;
import com.tompy.state.api.AdventureState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.util.Objects;

public abstract class AdventureImpl implements Adventure {
    private static final Logger LOGGER = LogManager.getLogger(AdventureImpl.class);
    protected final Player player;
    protected final EntityService entityService;
    protected final EntityFacadeBuilderFactory entityFacadeBuilderFactory;
    protected final ExitBuilderFactory exitBuilderFactory;
    private final UserInput userInput;
    private final PrintStream outStream;
    private boolean proceed;
    private AdventureState currentState;

    public AdventureImpl(Player player, EntityService entityService,
        EntityFacadeBuilderFactory entityFacadeBuilderFactory, ExitBuilderFactory exitBuilderFactory,
        UserInput userInput, PrintStream outStream) {
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        this.entityFacadeBuilderFactory =
            Objects.requireNonNull(entityFacadeBuilderFactory, "Entity Facade Builder Factory cannot be null.");
        this.exitBuilderFactory = Objects.requireNonNull(exitBuilderFactory, "Exit Builder Factory cannot be null.");
        this.userInput = Objects.requireNonNull(userInput, "User Input cannot be null.");
        this.outStream = Objects.requireNonNull(outStream, "Output Stream cannot be null.");
    }

    @Override
    public UserInput getUI() {
        return userInput;
    }

    @Override
    public void start(AdventureState state) {
        LOGGER.info("Starting the adventure.");
        player.setArea(entityService.getAreaByName("Room1"));
        proceed = true;
        changeState(state);
        go();
    }

    @Override
    public void stop() {
        LOGGER.info("Stopping the adventure.");
        //TODO Persist
        proceed = false;
    }

    @Override
    public void process() {
        if (currentState != null) {
            currentState.process();
        }
    }

    @Override
    public void changeState(AdventureState newState) {
        if (currentState != null) {
            currentState.end();
        }
        currentState = newState;
        currentState.start();
    }

    private void go() {
        while (proceed) {
            LOGGER.info("Start round");
            currentState.process();
        }
        currentState.end();
    }
}
