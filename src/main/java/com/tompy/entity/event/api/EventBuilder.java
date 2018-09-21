package com.tompy.entity.event.api;

import com.tompy.common.Builder;
import com.tompy.directive.Direction;
import com.tompy.directive.ActionType;
import com.tompy.directive.TriggerType;
import com.tompy.entity.api.Entity;

public interface EventBuilder extends Builder<Event> {
    EventBuilder name(String name);

    EventBuilder longName(String longName);

    EventBuilder memo(String memo);

    EventBuilder actionType(ActionType actionType);

    EventBuilder triggerType(TriggerType triggerType);

    EventBuilder entity(Entity entity);

    EventBuilder responses(String... responses);

    EventBuilder text(String text);

    EventBuilder direction(Direction direction);

    EventBuilder delay(int delay);
}
