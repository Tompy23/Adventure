package com.tompy.entity.encounter.api;

public interface MerchantStateMachine {

    void process();

    void changeState(MerchantState newState);
}
