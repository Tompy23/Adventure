package com.tompy.entity;

import com.tompy.attribute.api.Attribute;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityFacade;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.item.api.Item;
import com.tompy.io.UserInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Utility class of functions dealing with Entities.
 */
public class EntityUtil {
    private static final Logger LOGGER = LogManager.getLogger(EntityUtil.class);

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
        Long itemKey = null;
        if (!scores.isEmpty()) {
            List<Long> finalists = computeFinalists(scores);

            if (finalists.size() == 0) {
                return null;
            } else if (finalists.size() == 1) {
                itemKey = finalists.get(0);
            } else {
                itemKey = makeChoice(items, finalists, io);
            }
        }

        return itemKey;
    }

    /**
     * @param items       - The list of items from which to choose
     * @param description - The description supplied by the user.
     * @param io          - In case the user must supply a response
     * @return - The Item selected.
     */
    public static Optional<Item> findItemByDescription(List<Item> items, String description, UserInput io) {
        Long objectKey = EntityUtil.findEntityByDescription(items, description, io);
        return items.stream().filter((i) -> i.getKey().equals(objectKey)).findFirst();
    }

    /**
     * @param entityService - The entity service where the managers are located.
     * @param items         - The list of items from which to choose
     * @param description   - The description supplied by the user.
     * @param io            - In case the user must supply a response
     * @return - The Item selected.
     */
    public static Optional<Item> findVisibleItemByDescription(EntityService entityService, List<Item> items,
            String description, UserInput io) {
        Long objectKey = EntityUtil.findEntityByDescription(items, description, io);
        return items.stream().filter((i) -> i.getKey().equals(objectKey) && entityService.is(i, Attribute.VISIBLE))
                .findFirst();
    }

    /**
     * @param features    - The list of features from which to choose
     * @param description - The description supplied by the user.
     * @param io          - In case the user must supply a response
     * @return - The Item selected.
     */
    public static Optional<Feature> findFeatureByDescription(List<Feature> features, String description, UserInput io) {
        Long objectKey = EntityUtil.findEntityByDescription(features, description, io);
        return features.stream().filter((i) -> i.getKey().equals(objectKey)).findFirst();
    }

    /**
     * @param entityService - The entity service where the managers are located.
     * @param features      - The list of features from which to choose
     * @param description   - The description supplied by the user.
     * @param io            - In case the user must supply a response
     * @return - The Item selected.
     */
    public static Optional<Feature> findVisibleFeatureByDescription(EntityService entityService, List<Feature> features,
            String description, UserInput io) {
        Long objectKey = EntityUtil.findEntityByDescription(features, description, io);
        return features.stream().filter((i) -> i.getKey().equals(objectKey) && entityService.is(i, Attribute.VISIBLE))
                .findFirst();
    }

    private static Map<Long, Integer> computeScores(List<? extends Entity> items, String description) {
        Map<Long, Integer> scores = new HashMap<>();
        items.stream().forEach(e -> scores.put(e.getKey(), 0));
        for (Entity entity : items) {
            for (String word : entity.getDescriptionWords()) {
                for (String d : description.split(Pattern.quote(" "))) {
                    if (d.toUpperCase().contains(word.toUpperCase())) {
                        scores.put(entity.getKey(), scores.get(entity.getKey()) + 1);
                    }
                }
            }
        }

        return scores;
    }

    private static List<Long> computeFinalists(Map<Long, Integer> scores) {
        List<Long> finalists = new ArrayList<>();
        Integer highestScore = scores.values().stream().reduce(Integer::max).get();

        for (Long key : scores.keySet()) {
            if (scores.get(key) == highestScore) {
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
                    choices.put(entity.getKey(), entity.getDescription());
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

    public static void makeVisisble(EntityService entityService, Entity entity) {
        entityService.add(entity, Attribute.VISIBLE);
    }
}
