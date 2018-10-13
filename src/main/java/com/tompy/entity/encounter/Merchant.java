package com.tompy.entity.encounter;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.entity.item.Item;
import com.tompy.player.Player;

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
