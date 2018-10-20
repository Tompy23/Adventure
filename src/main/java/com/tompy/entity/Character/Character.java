package com.tompy.entity.Character;

import com.tompy.entity.compartment.Compartment;
import com.tompy.response.Response;

import java.util.List;

public interface Character extends Compartment {

    List<Response> move();
}
