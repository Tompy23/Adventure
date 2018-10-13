package com.tompy.state;

public interface StateMachine {
    void process();

    void changeState(AdventureState newState);
}
