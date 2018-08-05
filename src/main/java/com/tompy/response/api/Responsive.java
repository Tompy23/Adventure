package com.tompy.response.api;

import com.tompy.response.api.ResponseBuilderFactory;
import com.tompy.response.internal.ResponseImpl;

public abstract class Responsive {
    protected ResponseBuilderFactory responseFactory = ResponseImpl.createBuilderFactory();
}
