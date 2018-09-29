package com.tompy.entity.encounter.api;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.item.api.Item;
import com.tompy.player.api.Player;

import java.util.List;

public interface Merchant extends Entity, MerchantStateMachine {
    List<Item> getAvailable();

    MerchantState getBuyState();

    MerchantState getSellState();

    MerchantState getChatState();

    Player getPlayer();

    Adventure getAdventure();

    double getSellRate();

    double getBuyRate();

    EntityService getEntityService();

    void addItem(Item item);

    void removeItem(Item item);
}
