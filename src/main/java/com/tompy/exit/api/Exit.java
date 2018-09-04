package com.tompy.exit.api;

import com.tompy.directive.Direction;
import com.tompy.entity.area.api.Area;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.List;

/**
 * An Exit is an abstract construct handling the movement between areas.
 */
public interface Exit {
    /**
     * When a {@link Player} passes through the Exit
     *
     * @param direction - The {@link Direction} traveling through the exit
     * @return - List of {@link Response}
     */
    List<Response> passThru(Direction direction);

    /**
     * Return the other area for this exit based on the first area
     *
     * @param area - The {@link Area} from which to start
     * @return - the {@link Area} to which this exit ends
     */
    Area getConnectedArea(Area area);

//    /**
//     * Retrieve the {@link Area} to which this Exit is attached
//     * @param - direction - the {@link Direction} of the {@link Area} requested
//     * @return - The {@link Area}
//     */
//    Area getArea(Direction direction);
//
//    /**
//     * Retrieve the {@link Direction} for an area
//     * @param area - the {@link Area} requested
//     * @return - The {@link Direction}
//     */
//    Direction getDirection(Area area);

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
     *
     * @return - True, usable; False, not usable
     */
    boolean isOpen();
}
