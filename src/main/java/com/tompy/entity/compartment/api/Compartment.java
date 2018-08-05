package com.tompy.entity.compartment.api;

import com.tompy.entity.api.Entity;

public interface Compartment extends Entity {
    Entity fromIn();

    Entity fromOn();

    Entity fromUnder();

    Entity fromBehind();

}
