package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.item.api.Item;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CommandUseImpl extends CommandBasicImpl implements Command {
    private final String subject;
    private final String target;

    public CommandUseImpl(CommandType type, EntityService entityService, String subject, String target) {
        super(type, entityService);
        this.subject = Objects.requireNonNull(subject, "Subject cannot be null.");
        this.target = Objects.requireNonNull(target, "Target cannot be null.");
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandUseImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandUseImpl.CommandUseBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        // Determine the Entities subject/target represent

        List<Item> items = player.getArea().getAllItems();
        Long itemKey = EntityUtil.findEntityByDescription(items, target, adventure.getUI());
        Item source = items.stream().filter((i) -> i.getKey().equals(itemKey)).findFirst().get();

        List<Feature> entities = player.getArea().getAllFeatures();
        Long featureKey = EntityUtil.findEntityByDescription(items, target, adventure.getUI());
        Entity object = items.stream().filter((i) -> i.getKey().equals(featureKey)).findFirst().get();

        // TODO Determine the source/object from the subject/target
        // Source must be active for player
        // Object must be in area and visible
        // This is the bulk of the function and clearly requires Entity tricks (like EntityUtil
        // .findEntityByDescription())

        // Use subject on object
        if (source.hasTarget(object)) {
            return source.use();
        }
        return Collections.singletonList(responseFactory.createBuilder().source("CommandUse").text(
                String.format("Cannot use %s on %s", source.getName(), object.getName())).build());
    }

    public static final class CommandUseBuilderImpl extends CommandBuilderImpl {
        private String target = null;
        private String subject = null;


        @Override
        public Command build() {
            return new CommandUseImpl(type, entityService, subject, target);
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            if (parts.length == 3 && (parts[1].equalsIgnoreCase("on") || parts[1].equalsIgnoreCase("in"))) {
                subject = parts[0];
                target = parts[2];
            }
            return this;
        }
    }
}
