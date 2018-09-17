package com.tompy.entity.event.api;

import com.tompy.response.api.Response;

import java.util.List;

public interface Action {
    List<Response> apply();
}
