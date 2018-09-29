package com.tompy.entity.encounter.internal;

import com.tompy.attribute.api.Attribute;
import com.tompy.entity.encounter.api.Merchant;
import com.tompy.entity.encounter.api.MerchantState;
import com.tompy.entity.item.api.Item;
import com.tompy.response.api.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MerchantSell extends MerchantStateBaseImpl implements MerchantState {

    public MerchantSell(Merchant merchant) {
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
        for (Item item : merchant.getPlayer().getInventory()) {
            if (merchant.getEntityService().is(item, Attribute.VALUE)) {
                returnValue.put(item.getKey(), String.format("Sell %s for $%d.", item.getDescription(), (int) Math
                        .round(merchant.getEntityService().valueFor(item, Attribute.VALUE).getAsInt() *
                                merchant.getSellRate())));
            }
        }
        return returnValue;
    }

    @Override
    public List<Response> act(Long option) {
        Item tradeItem = null;
        for (Item item : merchant.getPlayer().getInventory()) {
            if (item.getKey() == option) {
                tradeItem = item;
            }
        }
        if (tradeItem != null) {
            merchant.getAvailable().add(tradeItem);
            merchant.getPlayer().removeItem(tradeItem);
            merchant.getPlayer().addMoney(merchant.getEntityService().valueFor(tradeItem, Attribute.VALUE).getAsInt());
        }
        merchant.changeState(merchant.getChatState());
        return Collections.emptyList();
    }
}
