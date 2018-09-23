package com.tompy.state.api;

public interface StateMachine {
    void process();

    void changeState(AdventureState newState);
}
