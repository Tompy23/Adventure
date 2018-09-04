package com.tompy.response.api;

import com.tompy.response.internal.ResponseImpl;

public abstract class Responsive {
    protected final ResponseBuilderFactory responseFactory = ResponseImpl.createBuilderFactory();
}
