package com.tompy.entity.feature.api;

import com.tompy.entity.api.Entity;
import com.tompy.response.api.Response;

import java.util.List;

public interface Feature extends Entity {
    String getName();

    List<Response> search();

    List<Response> open();

    List<Response> close();

    List<Response> drink();
}
