package com.tompy.entity.encounter;

import com.tompy.response.Response;

import java.util.List;
import java.util.Map;

public interface MerchantState {
    void start();

    void end();

    Map<Long, String> getOptions();

    List<Response> act(Long option);
}
