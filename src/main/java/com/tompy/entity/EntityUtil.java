package com.tompy.entity;

import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityFacade;
import com.tompy.io.UserInput;

import java.util.*;

/**
 * Utility class of functions dealing with Entities.
 */
public class EntityUtil {

    /**
     * Find an entity from a list based on the description.  If necessary the user may have to select from
     * the best choices.
     *
     * @param items       - The list of items from which to choose
     * @param description - The description supplied by the user.
     * @param io          - In case the user must supply a response
     * @return - The Item selected.
     */
    public static Long findEntityByDescription(List<? extends Entity> items, String description, UserInput io) {
        Map<Long, Integer> scores = computeScores(items, description);
        List<Long> finalists = computeFinalists(scores);
        Long itemKey;

        if (finalists.size() == 0) {
            return null;
        } else if (finalists.size() == 1) {
            itemKey = finalists.get(0);
        } else {
            itemKey = makeChoice(items, finalists, io);
        }

        return itemKey;
    }

    private static Map<Long, Integer> computeScores(List<? extends Entity> items, String description) {
        Map<Long, Integer> scores = new HashMap<>();
        items.stream().forEach(e -> scores.put(e.getKey(), 0));
        for (Entity entity : items) {
            if (description.toUpperCase().contains(entity.getShortName().toUpperCase())) {
                scores.put(entity.getKey(), scores.get(entity.getKey()) + 1);
            }
        }

        return scores;
    }

    private static List<Long> computeFinalists(Map<Long, Integer> scores) {
        List<Long> finalists = new ArrayList<>();
        for (Long key : scores.keySet()) {
            if (scores.get(key) > 0) {
                finalists.add(key);
            }
        }

        return finalists;
    }

    private static Long makeChoice(List<? extends Entity> entities, List<Long> finalists, UserInput io) {
        Map<Long, String> choices = new HashMap<>();
        for (Long finalist : finalists) {
            for (Entity entity : entities) {
                if (entity.getKey() == finalist) {
                    choices.put(entity.getKey(), entity.getName());
                }
            }
        }


        return io.getSelection(choices);
    }

    public static Entity findAreaByName(List<Entity> entities, String name) {
        return entities.stream().filter(name::equals).findFirst().get();
    }

    public static void add(EntityFacade facade) {
        facade.getService().add(facade.getEntity(), facade.getAttribute());
    }

    public static void add(EntityFacade facade, Integer value) {
        facade.getService().add(facade.getEntity(), facade.getAttribute(), value);
    }

    public static void remove(EntityFacade facade) {
        facade.getService().remove(facade.getEntity(), facade.getAttribute());
    }

    public static boolean is(EntityFacade facade) {
        return facade.getService().is(facade.getEntity(), facade.getAttribute());
    }

    public static OptionalInt valueFor(EntityFacade facade) {
        return facade.getService().valueFor(facade.getEntity(), facade.getAttribute());
    }
}
