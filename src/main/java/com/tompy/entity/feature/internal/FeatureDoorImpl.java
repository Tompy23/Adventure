package com.tompy.entity.feature.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityFacade;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.internal.EntityFacadeImpl;
import com.tompy.exit.api.Exit;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A Door is something that can be hidden, open/closed, locked, etc.
 * A door interacts with an Exit, the door is just a feature which controls the exit.  The Exit itself
 * controls whether or not the player can pass through.  The door and exit should be in sync.
 * A Door can be trapped.
 */
public class FeatureDoorImpl extends FeatureBasicImpl {
    private static final Logger LOGGER = LogManager.getLogger(FeatureDoorImpl.class);
    private Exit exit;

    protected FeatureDoorImpl(Long key, String name, List<String> descriptors, String description,
                              EntityService entityService, Exit exit) {
        super(key, name, descriptors, description, entityService);
        this.exit = exit;
        EntityUtil.add(visible);
   }

    @Override
    public List<Response> open(Player player, Adventure adventure) {
        LOGGER.info("Opening [{}, {}]", this.getName(), exit.toString());
        List<Response> returnValue = new ArrayList<>();
        if (!EntityUtil.is(open) && !EntityUtil.is(locked)) {
            EntityUtil.add(open);
            exit.open();
            returnValue.add(
                responseFactory.createBuilder().source(this.getName()).text(String.format("%s opens", this.getName()))
                    .build());
        } else {
            returnValue.add(responseFactory.createBuilder().source(this.getName())
                                .text(String.format("%s does not open", this.getName())).build());
        }

        return returnValue;
    }


    @Override
    public List<Response> close(Player player, Adventure adventure) {
        LOGGER.info("Closing [{}, {}]", this.getName(), exit.toString());
        List<Response> returnValue = new ArrayList<>();

        if (EntityUtil.is(open)) {
            EntityUtil.remove(open);
            exit.close();
            returnValue.add(
                responseFactory.createBuilder().source(this.getName()).text(String.format("%s closes", this.getName()))
                    .build());
        } else {
            returnValue.add(responseFactory.createBuilder().source(this.getName())
                                .text(String.format("%s does not close", this.getName())).build());
        }

        return returnValue;
    }

    @Override
    public List<Response> search(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Searching Door [{}]", getName());

        returnValue.add(responseFactory.createBuilder().source(name).text(String
            .format("%s [%s] [%s]", description, (EntityUtil.is(open) ? "open" : "closed"),
                EntityUtil.is(locked) ? "locked" : "unlocked")).build());

        return returnValue;
    }
}
