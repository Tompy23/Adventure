package com.tompy.entity.event.api;

import com.tompy.directive.EventType;

import java.util.List;
import java.util.Set;

public interface EventManager {
    /**
     * Clear the managed Events
     */
    void clear();

    void add(EventType type, Event event);

    void remove(EventType type, Event event);

    void remove(Event event);

    Set<Event> getAll();

    List<Event> getAllOfType(EventType type);
}
