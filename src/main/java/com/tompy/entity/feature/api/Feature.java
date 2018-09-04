package com.tompy.entity.feature.api;

import com.tompy.entity.compartment.api.Compartment;
import com.tompy.response.api.Response;

import java.util.List;

public interface Feature extends Compartment {
    String getName();

    List<Response> search();

    List<Response> open();

    List<Response> close();

    List<Response> drink();
}
