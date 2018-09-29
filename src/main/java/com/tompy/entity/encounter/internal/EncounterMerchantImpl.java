package com.tompy.entity.encounter.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.encounter.api.Merchant;
import com.tompy.entity.encounter.api.MerchantState;
import com.tompy.entity.encounter.api.MerchantStateMachine;
import com.tompy.entity.item.api.Item;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EncounterMerchantImpl extends EncounterImpl implements MerchantStateMachine, Merchant {

    private final MerchantChat chat;
    private final MerchantBuy buy;
    private final MerchantSell sell;
    private final double sellRate;
    private final double buyRate;
    private List<Item> available;
    private MerchantState currentState = null;

    public EncounterMerchantImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService, Player player, Adventure adventure, List<Item> items, double sellRate,
            double buyRate) {
        super(key, name, descriptors, description, entityService, player, adventure);
        this.available = new ArrayList<>();
        this.available.addAll(items);
        this.sellRate = sellRate;
        this.buyRate = buyRate;
        this.chat = new MerchantChat(this);
        this.buy = new MerchantBuy(this);
        this.sell = new MerchantSell(this);
        this.currentState = chat;
    }

    @Override
    public Map<Long, String> getOptions() {
        return currentState.getOptions();
    }

    @Override
    public List<Response> act(Long option) {
        return currentState.act(option);
    }

    @Override
    public void process() {
        // Call the encounter's "list options", returns Map<Long, String>
        Map<Long, String> options = currentState.getOptions();

        // Call UserInput Make choice, returns Long
        Long option = adventure.getUI().getSelection(options);

        // Call the encounter and pass the Long selected, returns List<Response>
        List<Response> responses = currentState.act(option);

        // Render the list of responses to outStream
        responses.stream().forEachOrdered((r) -> adventure.getOutStream().println(r.render()));
    }

    @Override
    public void changeState(MerchantState newState) {
        if (currentState != null) {
            currentState.end();
        }
        currentState = newState;
        currentState.start();
    }

    @Override
    public List<Item> getAvailable() {
        return available;
    }

    @Override
    public MerchantState getBuyState() {
        return buy;
    }

    @Override
    public MerchantState getSellState() {
        return sell;
    }

    @Override
    public MerchantState getChatState() {
        return chat;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Adventure getAdventure() {
        return adventure;
    }

    @Override
    public double getSellRate() {
        return sellRate;
    }

    @Override
    public double getBuyRate() {
        return buyRate;
    }

    @Override
    public EntityService getEntityService() {
        return entityService;
    }

    @Override
    public void addItem(Item item) {
        available.add(item);
    }

    @Override
    public void removeItem(Item item) {
        available.remove(item);
    }
}
