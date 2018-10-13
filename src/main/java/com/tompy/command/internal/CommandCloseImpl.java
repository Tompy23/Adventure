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

public class CommandCloseImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandCloseImpl.class);
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
        LOGGER.info("Executing Command Close");
        List<Response> returnValue = new ArrayList<>();

        Optional<Feature> objectOpt = EntityUtil
                .findVisibleFeatureByDescription(entityService, player.getArea().getAllFeatures(), target,
                        adventure.getUI());

        if (objectOpt.isPresent()) {
            Feature object = objectOpt.get();
            LOGGER.debug("Converted {} to {}", new String[]{target, object.getName()});
            returnValue.addAll(object.close(player, adventure));
        } else {
            LOGGER.debug("Unable to convert {}", target);
        }
        return returnValue;
    }

    public static final class CommandCloseBuilderImpl extends CommandBuilderImpl {
        private String target;

        @Override
        public CommandBuilder parts(String[] parts) {
            StringBuilder targetSb = new StringBuilder();
            for (int i = 1; i < parts.length; i++) {
                targetSb.append(parts[i] + " ");
            }
            target = targetSb.toString().trim();
            return this;
        }

        @Override
        public Command build() {
            return new CommandCloseImpl(CommandType.COMMAND_CLOSE, target, entityService);
        }
    }
}
