package com.tompy.entity.event.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.ActionType;
import com.tompy.directive.Direction;
import com.tompy.directive.EventType;
import com.tompy.directive.TriggerType;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.encounter.api.Encounter;
import com.tompy.entity.event.api.Action;
import com.tompy.entity.event.api.Event;
import com.tompy.entity.event.api.EventBuilder;
import com.tompy.entity.event.api.Trigger;
import com.tompy.entity.internal.EntityBuilderHelperImpl;
import com.tompy.entity.internal.EntityImpl;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import com.tompy.state.api.AdventureStateFactory;

import java.util.Collections;
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

    @Override public boolean pull(Player player, Adventure adventure) {
        return trigger.pull(player, adventure);
    }

    @Override public List<Response> apply(Player player, Adventure adventure) {
        return action.apply(player, adventure);
    }

    public static final class EventBuilderImpl extends EntityBuilderHelperImpl implements EventBuilder {
        private ActionType actionType;
        private TriggerType triggerType;
        private Entity entity;
        private String[] responses;
        private String text;
        private Direction direction;
        private Encounter encounter;
        private AdventureStateFactory stateFactory;
        private int delay;
        private List<Event> events;
        private EventType subEventType;

        public EventBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        @Override public EventBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override public EventBuilder memo(String memo) {
            this.description = memo;
            return this;
        }

        @Override public EventBuilder actionType(ActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        @Override public EventBuilder triggerType(TriggerType triggerType) {
            this.triggerType = triggerType;
            return this;
        }

        @Override public EventBuilder stateFactory(AdventureStateFactory stateFactory) {
            this.stateFactory = stateFactory;
            return this;
        }

        @Override public EventBuilder entity(Entity entity) {
            this.entity = entity;
            return this;
        }

        @Override public EventBuilder responses(String... responses) {
            this.responses = responses;
            return this;
        }

        @Override public EventBuilder text(String text) {
            this.text = text;
            return this;
        }

        @Override public EventBuilder direction(Direction direction) {
            this.direction = direction;
            return this;
        }

        @Override public EventBuilder encounter(Encounter encounter) {
            this.encounter = encounter;
            return this;
        }

        @Override public EventBuilder delay(int delay) {
            this.delay = delay;
            return this;
        }

        @Override
        public EventBuilder events(List<Event> events) {
            this.events = Collections.unmodifiableList(events);
            return this;
        }

        @Override
        public EventBuilder eventType(EventType subType) {
            this.subEventType = subType;
            return this;
        }

        @Override public Event build() {
            return new EventImpl(key, name, this.buildDescriptors(), description, entityService, buildAction(),
                    buildTrigger());
        }

        private Action buildAction() {
            switch (actionType) {
                case ADD_EVENT:
                    return new ActionAddEventImpl(entity, entityService, responses, subEventType, events);
                case DESCRIBE:
                    return new ActionDescribeImpl(entity, entityService, responses);
                case ENCOUNTER:
                    return new ActionEncounterImpl(entity, entityService, responses, encounter, stateFactory);
                case EXPLORE:
                    return new ActionExploreImpl(entity, entityService, responses, stateFactory);
                case HORRIBLE_DEATH:
                    return new ActionDeathImpl(entity, entityService, responses);
                case MAKE_VISIBLE:
                    return new ActionVisibleImp(entity, entityService, responses);
            }
            return null;
        }

        private Trigger buildTrigger() {
            switch (triggerType) {
                case ALWAYS:
                    return new TriggerAlwaysImpl(entity);
                case ONCE:
                    return new TriggerOnceImpl(entity);
                case ONCE_DELAY:
                    return new TriggerOnceAfterDelay(entity, delay);
                case ALWAYS_DELAY:
                    return new TriggerAlwaysAfterDelay(entity, delay);
            }
            return null;
        }
    }
}
