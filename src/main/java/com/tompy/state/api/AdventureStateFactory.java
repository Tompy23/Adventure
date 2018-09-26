package com.tompy.state.api;

public interface AdventureStateFactory {

    AdventureState getExploreState();

    AdventureStateEncounterBuilder createEncounterState();

    AdventureStateCombatBuilder createCombatState();
}
