package com.tompy.feature.api;

import com.tompy.response.api.Response;

import java.util.List;

public interface Feature {
    String getName();
    List<Response> search();
    List<Response> openClose();
    List<Response> drink();
}
