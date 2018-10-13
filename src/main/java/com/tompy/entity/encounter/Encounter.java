package com.tompy.entity.encounter;

import com.tompy.entity.Entity;
import com.tompy.response.Response;

import java.util.List;
import java.util.Map;

public interface Encounter extends Entity {
    Map<Long, String> getOptions();

    List<Response> act(Long option);
}
