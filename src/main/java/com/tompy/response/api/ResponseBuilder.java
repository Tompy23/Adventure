package com.tompy.response.api;

import com.tompy.common.Builder;

public interface ResponseBuilder extends Builder<Response> {
    public ResponseBuilder source(String source);
    public ResponseBuilder text(String text);
}
