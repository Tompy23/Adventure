package com.tompy.adventure.api;

import com.tompy.io.UserInput;
import com.tompy.player.api.Player;
import com.tompy.state.api.AdventureState;
import com.tompy.state.api.AdventureStateFactory;
import com.tompy.state.api.StateMachine;

/**
 * Defines functions which interact with the state of the adventure.
 */
public interface Adventure extends StateMachine {

    /**
     * Create the adventure elements
     */
    void create(AdventureStateFactory stateFactory);

    /**
     * Expose the user input
     *
     * @return - The user input implementation
     */
    UserInput getUI();

    /**
     * Begin an adventure for a player
     *
     * @param state - The starting state for the adventure
     */
    void start(AdventureState state);

    /**
     * Gracefully stop an adventure for a specific player
     *
     */
    void stop();
}
