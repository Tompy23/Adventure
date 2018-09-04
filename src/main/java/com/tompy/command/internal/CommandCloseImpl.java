package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.feature.api.Feature;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandCloseImpl extends CommandBasicImpl implements Command {
    private final String target;

    private CommandCloseImpl(CommandType type, String target, EntityService entityService) {
        super(type, entityService);
        this.target = Objects.requireNonNull(target, "Target cannot be null.");
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandCloseImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandCloseImpl.CommandCloseBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        List<Feature> entities = player.getArea().getAllFeatures();
        Long featureKey = EntityUtil.findEntityByDescription(entities, target, adventure.getUI());
        Feature object = entities.stream().filter((i) -> i.getKey().equals(featureKey)).findFirst().get();

        returnValue.addAll(object.close());
        returnValue
            .add(responseFactory.createBuilder().source("CommandClose").text(object.getName() + " is closed.").build());
        return returnValue;
    }

    public static final class CommandCloseBuilderImpl extends CommandBuilderImpl {
        private String target;

        @Override
        public CommandBuilder parts(String[] parts) {
            target = parts[1];
            return this;
        }

        @Override
        public Command build() {
            return new CommandCloseImpl(CommandType.COMMAND_CLOSE, target, entityService);
        }
    }
}
