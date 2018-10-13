package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;

public interface Action {
    List<Response> apply(Player player, Adventure adventure);
}
