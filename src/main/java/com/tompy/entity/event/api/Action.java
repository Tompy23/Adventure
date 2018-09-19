package com.tompy.entity.event.api;

import com.tompy.adventure.api.Adventure;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.List;

public interface Action {
    List<Response> apply(Player player, Adventure adventure);
}
