package com.tompy.adventure.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.directive.*;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityFacadeBuilderFactory;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.area.api.Area;
import com.tompy.entity.encounter.api.Encounter;
import com.tompy.entity.encounter.api.EncounterBuilder;
import com.tompy.entity.event.api.Event;
import com.tompy.entity.event.api.EventBuilder;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.feature.api.FeatureBuilder;
import com.tompy.entity.item.api.Item;
import com.tompy.entity.item.api.ItemBuilder;
import com.tompy.exit.api.Exit;
import com.tompy.exit.api.ExitBuilderFactory;
import com.tompy.io.UserInput;
import com.tompy.messages.MessageHandler;
import com.tompy.player.api.Player;
import com.tompy.state.api.AdventureState;
import com.tompy.state.api.AdventureStateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.util.Objects;

import static com.tompy.directive.ActionType.ACTION_DESCRIBE;
import static com.tompy.directive.TriggerType.TRIGGER_ALWAYS;

public abstract class AdventureImpl implements Adventure {
    private static final Logger LOGGER = LogManager.getLogger(AdventureImpl.class);
    protected final Player player;
    protected final EntityService entityService;
    protected final EntityFacadeBuilderFactory entityFacadeBuilderFactory;
    protected final ExitBuilderFactory exitBuilderFactory;
    protected final MessageHandler messages;
    private final UserInput userInput;
    private final PrintStream outStream;
    protected AdventureStateFactory stateFactory;
    private boolean proceed;
    private AdventureState currentState;

    public AdventureImpl(Player player, EntityService entityService,
            EntityFacadeBuilderFactory entityFacadeBuilderFactory, ExitBuilderFactory exitBuilderFactory,
            UserInput userInput, PrintStream outStream) {
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        this.entityFacadeBuilderFactory =
                Objects.requireNonNull(entityFacadeBuilderFactory, "Entity Facade Builder Factory cannot be null.");
        this.exitBuilderFactory = Objects.requireNonNull(exitBuilderFactory, "Exit Builder Factory cannot be null.");
        this.userInput = Objects.requireNonNull(userInput, "User Input cannot be null.");
        this.outStream = Objects.requireNonNull(outStream, "Output Stream cannot be null.");
        this.messages = new MessageHandler();
    }

    @Override
    public UserInput getUI() {
        return userInput;
    }

    @Override
    public PrintStream getOutStream() {
        return outStream;
    }

    @Override
    public void start(AdventureState state, String startRoom) {
        LOGGER.info("Starting the adventure.");
        player.setArea(entityService.getAreaByName(startRoom));
        proceed = true;
        changeState(state);
        processState();
    }

    @Override
    public void stop() {
        LOGGER.info("Stopping the adventure.");
        //TODO Persist
        proceed = false;
    }

    @Override
    public void process() {
        if (currentState != null) {
            currentState.process();
        }
    }

    @Override
    public void changeState(AdventureState newState) {
        if (currentState != null) {
            currentState.end();
        }
        currentState = newState;
        currentState.start();
    }

    private void processState() {
        while (proceed) {
            LOGGER.info("Start round");
            currentState.process();
        }
        currentState.end();
    }

    /**
     * Below are functions used to create objects
     */


    /**
     * Translate responses from message id
     *
     * @param responses
     * @return
     */
    private String[] fixResponses(String... responses) {
        String[] fixedResponses = new String[responses.length];
        for (int i = 0; i < responses.length; i++) {
            fixedResponses[i] = messages.get(responses[i]);
        }
        return fixedResponses;
    }

    /**
     * Area
     *
     * @param name
     * @return
     */
    protected Area buildArea(String name) {
        Area area = entityService.createAreaBuilder().name(name).build();
        entityService.addArea(area);
        return area;
    }

    /**
     * Exit
     *
     * @param a1
     * @param d1
     * @param a2
     * @param d2
     * @param open
     * @return
     */
    protected Exit buildExit(Area a1, Direction d1, Area a2, Direction d2, boolean open) {
        Exit e = exitBuilderFactory.builder().area(a1).area(a2).state(open).build();
        a1.installExit(d1, e);
        a2.installExit(d2, e);
        return e;
    }

    /**
     * Basic Feature
     *
     * @param type
     * @param description
     * @return
     */
    protected Feature buildFeature(FeatureType type, String description) {
        return featureBuilder(type, description).build();
    }

    /**
     * Named Feature
     *
     * @param type
     * @param description
     * @param name
     * @return
     */
    protected Feature buildFeature(FeatureType type, String description, String name) {
        return featureBuilder(type, description).name(name).build();
    }

    /**
     * Feature Builder
     *
     * @param type
     * @param description
     * @return
     */
    protected FeatureBuilder featureBuilder(FeatureType type, String description) {
        return entityService.createFeatureBuilder().type(type).description(messages.get(description));
    }

    /**
     * Event Builder
     *
     * @param action
     * @param trigger
     * @param entity
     * @param responses
     * @return
     */
    protected EventBuilder eventBuilder(ActionType action, TriggerType trigger, Entity entity, String... responses) {
        return entityService.createEventBuilder().actionType(action).triggerType(trigger).entity(entity)
                .responses(fixResponses(responses)).stateFactory(stateFactory);
    }

    protected void describeAlways(Entity entity, EventType type, String... responses) {
        addEvent(entity, type,
                eventBuilder(ACTION_DESCRIBE, TRIGGER_ALWAYS, entity, responses).build());
    }

    /**
     * Add Event to Entity
     *
     * @param entity
     * @param type
     * @param event
     */
    protected void addEvent(Entity entity, EventType type, Event event) {
        entityService.add(entity, type, event);
    }

    /**
     * Add an attribute to an entity
     *
     * @param e
     * @param a
     */
    protected void add(Entity e, Attribute a) {
        entityService.add(e, a);
    }

    /**
     * Add an attribute and value to an entity
     *
     * @param e
     * @param a
     * @param v
     */
    protected void add(Entity e, Attribute a, int v) {
        entityService.add(e, a, v);
    }

    /**
     * Remove an attribute from an entity
     *
     * @param e
     * @param a
     */
    protected void remove(Entity e, Attribute a) {
        entityService.remove(e, a);
    }

    /**
     * Encounter
     *
     * @param t
     * @return
     */
    protected Encounter buildEncounter(EncounterType t) {
        return encounterBuilder(t).build();
    }

    protected EncounterBuilder encounterBuilder(EncounterType t) {
        return entityService.createEncounterBuilder(player, this).type(t);
    }

    /**
     * Item
     *
     * @param t
     * @param d
     * @return
     */
    protected Item buildItem(ItemType t, String d) {
        return itemBuilder(t, d).build();
    }

    /**
     * Item Builder
     *
     * @param t
     * @param d
     * @return
     */
    protected ItemBuilder itemBuilder(ItemType t, String d) {
        return entityService.createItemBuilder().type(t).description(messages.get(d));
    }
}
