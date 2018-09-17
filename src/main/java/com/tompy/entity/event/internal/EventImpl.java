package com.tompy.entity.event.internal;

import com.tompy.directive.ActionType;
import com.tompy.directive.Direction;
import com.tompy.directive.TriggerType;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.event.api.Action;
import com.tompy.entity.event.api.Event;
import com.tompy.entity.event.api.EventBuilder;
import com.tompy.entity.event.api.Trigger;
import com.tompy.entity.internal.EntityBuilderHelperImpl;
import com.tompy.entity.internal.EntityImpl;
import com.tompy.response.api.Response;

import java.util.List;
import java.util.Objects;

public class EventImpl extends EntityImpl implements Event {
    private final Action action;
    private final Trigger trigger;

    public EventImpl(Long key, String name, List<String> descriptors, String description, EntityService entityService,
        Action action, Trigger trigger) {
        super(key, name, descriptors, description, entityService);
        this.action = Objects.requireNonNull(action, "Action cannot be null.");
        this.trigger = Objects.requireNonNull(trigger, "Trigger cannot be null.");

    }

    public static EventBuilder createBuilder(Long key, EntityService entityService) {
        return new EventBuilderImpl(key, entityService);
    }

    @Override
    public boolean pull() {
        return trigger.pull();
    }

    @Override
    public List<Response> apply() {
        return action.apply();
    }

    public static final class EventBuilderImpl extends EntityBuilderHelperImpl implements EventBuilder {
        private Action action;
        private Trigger trigger;
        private ActionType actionType;
        private TriggerType triggerType;
        private Entity entity;
        private String[] responses;
        private String text;
        private Direction direction;

        public EventBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        @Override
        public EventBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public EventBuilder longName(String longName) {
            this.longName = longName;
            return this;
        }

        @Override
        public EventBuilder memo(String memo) {
            this.description = memo;
            return this;
        }

        @Override
        public EventBuilder actionType(ActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        @Override
        public EventBuilder triggerType(TriggerType triggerType) {
            this.triggerType = triggerType;
            return this;
        }

        @Override
        public EventBuilder entity(Entity entity) {
            this.entity = entity;
            return this;
        }

        @Override
        public EventBuilder responses(String... responses) {
            this.responses = responses;
            return this;
        }

        @Override
        public EventBuilder text(String text) {
            this.text = text;
            return this;
        }

        @Override
        public EventBuilder direction(Direction direction) {
            this.direction = direction;
            return this;
        }

        @Override
        public EventBuilder action(Action action) {
            this.action = action;
            return this;
        }

        @Override
        public EventBuilder trigger(Trigger trigger) {
            this.trigger = trigger;
            return this;
        }

        @Override
        public Event build() {
            return new EventImpl(key, name, this.buildDescriptors(), description, entityService, buildAction(),
                buildTrigger());
        }

        private Action buildAction() {
            switch (actionType) {
                case DESCRIBE:
                    return new ActionDescribeImpl(entity, responses);
            }
            return null;
        }

        private Trigger buildTrigger() {
            switch (triggerType) {
                case ALWAYS:
                    return new TriggerAlwaysImpl(entity);
                case ONCE:
                    return new TriggerOnceImpl(entity);
            }
            return null;
        }
    }
}
