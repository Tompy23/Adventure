package com.tompy.adventure.api;

import com.tompy.area.api.Area;
import com.tompy.player.api.Player;

/**
 * Defines functions which interact with the state of the adventure.
 */
public interface Adventure {
    /**
     * Begin an adventure for a player
     * @param player - The player who is joining the adventure
     */
    void start(Player player);

    /**
     * Gracefully stop an adventure for a specific player
     * @param player - The player who is exiting the adventure
     */
    void stop(Player player);

    /**
     * Retrieve an Area of the adventure based on the key name of the area
     * @param key - The name of the area
     * @return - A reference to the area
     */
    Area getArea(String key);

    /**
     * Determines if the Adventure is in a state which is ready for user input.
     * @return - True for when the adventure is ready for user input.
     */
    boolean proceed();
}
