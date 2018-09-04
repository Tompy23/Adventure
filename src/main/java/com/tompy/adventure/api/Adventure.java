package com.tompy.adventure.api;

import com.tompy.io.UserInput;
import com.tompy.player.api.Player;

/**
 * Defines functions which interact with the state of the adventure.
 */
public interface Adventure {

    /**
     * Create the adventure elements
     */
    void create();

    /**
     * Begin an adventure for a player
     *
     * @param player - The player who is joining the adventure
     */
    void start(Player player);

    /**
     * Gracefully stop an adventure for a specific player
     *
     * @param player - The player who is exiting the adventure
     */
    void stop(Player player);

    /**
     * Determines if the Adventure is in a state which is ready for user input.
     *
     * @return - True for when the adventure is ready for user input.
     */
    boolean proceed();

    /**
     * Expose the user input
     *
     * @return - The user input implementation
     */
    UserInput getUI();
}
