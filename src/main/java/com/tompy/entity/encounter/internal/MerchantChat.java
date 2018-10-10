package com.tompy.entity.encounter.internal;

import com.tompy.entity.encounter.api.Merchant;
import com.tompy.entity.encounter.api.MerchantState;
import com.tompy.entity.event.api.Event;
import com.tompy.response.api.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tompy.directive.EventType.EVENT_INTERACTION;
import static com.tompy.entity.encounter.internal.EncounterConstants.BUY;
import static com.tompy.entity.encounter.internal.EncounterConstants.SELL;

public class MerchantChat extends MerchantStateBaseImpl implements MerchantState {


    public MerchantChat(Merchant merchant) {
        super(merchant);
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public Map<Long, String> getOptions() {
        Map<Long, String> returnValue = new HashMap<>();
        for (Event event : merchant.getEntityService().get(merchant, EVENT_INTERACTION)) {
            if (event.pull(merchant.getPlayer(), merchant.getAdventure())) {
                returnValue.put(event.getKey(), event.getDescription());
            }
        }
        returnValue.put((long) BUY, "Buy something");
        returnValue.put((long) SELL, "Sell something");
        return returnValue;
    }

    @Override
    public List<Response> act(Long option) {
        int choice = option.intValue();
        if (choice == BUY || choice == SELL) {
            merchant.changeState(choice == BUY ? merchant.getBuyState() : merchant.getSellState());
        } else {

            for (Event event : merchant.getEntityService().get(merchant, EVENT_INTERACTION)) {
                if (event.getKey() == option) {
                    return event.apply(merchant.getPlayer(), merchant.getAdventure());
                }
            }
        }
        return Collections.emptyList();
    }
}
