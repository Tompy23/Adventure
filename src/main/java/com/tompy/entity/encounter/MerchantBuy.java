package com.tompy.entity.encounter;

import com.tompy.entity.item.Item;
import com.tompy.response.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tompy.attribute.Attribute.VALUE;

public class MerchantBuy extends MerchantStateBaseImpl implements MerchantState {

    public MerchantBuy(Merchant merchant) {
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
        for (Item item : merchant.getAvailable()) {
            returnValue.put(item.getKey(), String.format("Buy %s for $%d.", item.getDescription(), (int) Math
                    .round(merchant.getEntityService().valueFor(item, VALUE).getAsInt() * merchant.getBuyRate())));
        }
        return returnValue;
    }

    @Override
    public List<Response> act(Long option) {
        Item tradeItem = null;
        for (Item item : merchant.getAvailable()) {
            if (item.getKey() == option) {
                tradeItem = item;
                break;
            }
        }
        if (tradeItem != null) {
            merchant.getAvailable().remove(tradeItem);
            if (merchant.getPlayer().pay((int) Math
                    .round(merchant.getEntityService().valueFor(tradeItem, VALUE).getAsInt() *
                            merchant.getBuyRate()))) {
                merchant.getPlayer().addItem(tradeItem);
            }
        }
        merchant.changeState(merchant.getChatState());
        return Collections.emptyList();
    }
}
