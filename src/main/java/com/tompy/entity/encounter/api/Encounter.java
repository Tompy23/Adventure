package com.tompy.entity.encounter.api;

import com.tompy.entity.api.Entity;
import com.tompy.response.api.Response;

import java.util.List;
import java.util.Map;

public interface Encounter extends Entity {
    Map<Long, String> getOptions();

    List<Response> act(Long option);
}
