package com.tompy.entity.feature;

import com.tompy.adventure.Adventure;
import com.tompy.entity.compartment.Compartment;
import com.tompy.entity.item.Item;
import com.tompy.player.Player;
import com.tompy.response.Response;

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
