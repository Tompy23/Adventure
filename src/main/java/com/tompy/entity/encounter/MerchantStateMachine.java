package com.tompy.entity.encounter;

public interface MerchantStateMachine {

    void process();

    void changeState(MerchantState newState);
}
