package com.tompy.area.api;

import com.tompy.directive.Direction;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.List;

/**
 * An Exit is an abstract construct handling the movement between areas.
 */
public interface Exit {
    /**
     * When a {@link Player} passes through the Exit
     * }
     * @return - List of {@link Response}
     */
    List<Response> passThru();

    /**
     * Retrieve the {@link Direction} of the exit relative to its {@link Area}
     * @return - The {@link Direction}
     */
    Direction getDirection();

    /**
     * Retrieve the {@link Area} to which this Exit is attached
     * @return - The {@link Area}
     */
    Area getArea();

    /**
     * Retrieve the matching Exit if any.  A matching Exit is attached to the {@link Area} to which
     * this Exit is pointed and points to this Exit's {@link Area}
     * @return - The matching Exit.
     */
    Exit getParallel();

    /**
     * Sets a parallel exit that normally represents the opposite direction of travel between two {@link Area}
     * @param exit - The matching Exit
     */
    void setParallel(Exit exit);

    /**
     * Sets the Exit to allow use
     */
    void open();

    /**
     * Sets the Exit to disallow use
     */
    void close();

    /**
     * Checks the state of the exit if it is usable or not
     * @return - True, usable; False, not usable
     */
    boolean isOpen();
}
