package com.tompy.area.api;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.Direction;
import com.tompy.feature.api.Feature;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.List;

public interface Area {

    public void installExit(Exit exit);
    public void installFeature(Feature feature, Direction direction);
    public String getName();
    public Exit getExitForDirection(Direction direction);
    public List<Response> enter(Direction direction, Player player, Adventure adventure);
    public List<Response> exit(Direction direction, Player player, Adventure adventure);
    public List<Response> search(Player player, Adventure adventure);
    public List<Response> searchDirection(Direction direction, Player player, Adventure adventure);
}
