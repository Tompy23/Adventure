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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This feature represents objects that can be open/closed, locked/unlocked, trapped...
 */
public class CommandOpenImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandOpenImpl.class);
    private final String target;

    private CommandOpenImpl(CommandType type, String target, EntityService entityService) {
        super(type, entityService);
        this.target = Objects.requireNonNull(target, "Target cannot be null.");
    }

    public static CommandBuilderFactory createBuilderFactory() {
        return CommandOpenImpl::createBuilder;
    }

    public static CommandBuilder createBuilder() {
        return new CommandOpenImpl.CommandOpenBuilderImpl();
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Command Open");
        List<Response> returnValue = new ArrayList<>();

        Optional<Feature> optObject = EntityUtil.findFeatureByDescription(player.getArea().getAllFeatures(), target,
            adventure.getUI());

        if (optObject.isPresent()) {
            Feature object = optObject.get();
            returnValue.addAll(object.open(player, adventure));
            returnValue.add(
                responseFactory.createBuilder().source("CommandOpen").text(object.getName() + " is open.").build());
        } else {
            returnValue
                .add(responseFactory.createBuilder().source("CommandOpen").text(target + " is not open.").build());
        }
        return returnValue;
    }

    public static final class CommandOpenBuilderImpl extends CommandBuilderImpl {
        private String target;

        @Override
        public CommandBuilder parts(String[] parts) {
            target = parts[1];
            return this;
        }

        @Override
        public Command build() {
            return new CommandOpenImpl(CommandType.COMMAND_OPEN, target, entityService);
        }
    }
}
