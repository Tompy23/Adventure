package com.tompy.state.api;

public interface AdventureStateFactory {

    AdventureState createExploreState();

    AdventureState createEncounterState();

    AdventureState createCombatState();
}
