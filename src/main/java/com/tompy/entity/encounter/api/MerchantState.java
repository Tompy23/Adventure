package com.tompy.entity.encounter.api;

import com.tompy.response.api.Response;
import com.tompy.state.api.AdventureState;

import java.util.List;
import java.util.Map;

public interface MerchantState {
    void start();

    void end();

    Map<Long, String> getOptions();

    List<Response> act(Long option);
}
