package com.tompy.entity.feature.api;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.entity.event.api.Event;
import com.tompy.entity.item.api.Item;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.List;

public interface Feature extends Compartment {

    List<Response> search(Player player, Adventure adventure);

    List<Response> open(Player player, Adventure adventure);

    List<Response> close(Player player, Adventure adventure);

    List<Response> lock(Player player, Adventure adventure);

    List<Response> unlock(Player player, Adventure adventure);

    List<Response> misUse(Item item, Player player, Adventure adventure);

    List<Response> drink(Player player, Adventure adventure);
}
