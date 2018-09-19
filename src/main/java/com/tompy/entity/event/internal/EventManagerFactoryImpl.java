package com.tompy.entity.event.internal;

import com.tompy.entity.event.api.EventManager;
import com.tompy.entity.event.api.EventManagerFactory;

public class EventManagerFactoryImpl implements EventManagerFactory {
    @Override
    public EventManager create() {
        return new EventManagerImpl();
    }
}
