package com.tompy.adventure;

import com.tompy.directive.Direction;
import com.tompy.io.UserInput;
import com.tompy.state.AdventureState;
import com.tompy.state.AdventureStateFactory;
import com.tompy.state.StateMachine;

import java.io.PrintStream;

/**
 * Defines functions which interact with the state of the adventure.
 */
public interface Adventure extends StateMachine {

    /**
     * Create the adventure elements
     */
    void create();

    /**
     * Set the state factory for the adventure
     *
     * @param stateFactory - The state factory
     */
    void setStateFactory(AdventureStateFactory stateFactory);

    /**
     * Expose the user input
     *
     * @return - The user input implementation
     */
    UserInput getUI();

    /**
     * Expose the output mechanism
     *
     * @return - The print stream implementation
     */
    PrintStream getOutStream();

    /**
     * Begin an adventure for a player
     *
     * @param state - The starting state for the adventure
     */
    void start(AdventureState state, String startRoom, Direction direction);

    /**
     * Gracefully stop an adventure for a specific player
     */
    void stop();
}
