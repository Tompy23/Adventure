package com.tompy.exit.internal;

import com.tompy.exit.api.ExitBuilder;
import com.tompy.exit.api.ExitBuilderFactory;

public class ExitBuilderFactoryImpl implements ExitBuilderFactory {
    @Override
    public ExitBuilder builder() {
        return new ExitImpl.ExitBuilderImpl();
    }
}
