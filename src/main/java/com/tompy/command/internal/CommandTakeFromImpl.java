package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.item.api.Item;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CommandTakeFromImpl extends CommandTakeImpl {
    private static final Logger LOGGER = LogManager.getLogger(CommandTakeFromImpl.class);
    private final String item;

    protected CommandTakeFromImpl(CommandType type, EntityService entityService, String item, String target) {
        super(type != null ? type : CommandType.COMMAND_TAKE_FROM, entityService, target);
        this.item = Objects.requireNonNull(item, "Item cannot be null.");
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Command Take From");
        List<Response> returnValue = new ArrayList<>();

        Optional<Feature> optObject =
            EntityUtil.findFeatureByDescription(player.getArea().getAllFeatures(), target, adventure.getUI());

        if (optObject.isPresent()) {
            Feature object = optObject.get();

            if (entityService.is(object, Attribute.VISIBLE)) {
                Optional<Item> optSource =
                    EntityUtil.findItemByDescription(object.getAllItems(), item, adventure.getUI());

                if (optSource.isPresent()) {
                    Item source = optSource.get();

                    if (player.addItem(source)) {
                        object.removeItem(source);
                        returnValue.add(responseFactory.createBuilder().source("CommandTakeFrom")
                            .text(String.format("%s is now in %s's inventory", source.getName(), player.getName()))
                            .build());
                    } else {
                        // TODO Inventory full?  Or some other issue?
                        returnValue.add(
                            responseFactory.createBuilder().source("CommandTakeFrom").text("Inventory full.").build());
                    }
                } else { // Item not in target
                    returnValue.add(responseFactory.createBuilder().source("CommandTakeFrom")
                        .text(String.format("%s not in %s", item, target)).build());
                }
            } else { // target not visible
                returnValue.add(
                    responseFactory.createBuilder().source("CommandTakeFrom").text(String.format("%s not here", target))
                        .build());
            }
        } else { // Target not present
            returnValue.add(responseFactory.createBuilder().source("CommandTakeFrom")
                .text(String.format("%s does not contain %s", target, item)).build());
        }

        return returnValue;
    }
}
