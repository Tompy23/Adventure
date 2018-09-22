package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.feature.api.Feature;
import com.tompy.entity.item.api.Item;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CommandUseImpl extends CommandBasicImpl implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommandUseImpl.class);
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
        LOGGER.info("Executing Command Use. subject: {}; target {}", subject, target);
        Optional<Item> optSource =
            EntityUtil.findItemByDescription(player.getInventory(), subject, adventure.getUI());

        Optional<Feature> optObject =
            EntityUtil.findFeatureByDescription(player.getArea().getAllFeatures(), target, adventure.getUI());

        if (optSource.isPresent() && optObject.isPresent()) {
            Item source = optSource.get();
            Feature object = optObject.get();
            // Use subject on target
            if (source.hasTarget(object)) {
                LOGGER.info("Using [{}] on [{}]", source.getName(), object.getName());
                return source.use(player, adventure);
            }

            return Collections.singletonList(responseFactory.createBuilder().source("CommandUse").text(
                String.format("Cannot use %s on %s", source.getName(), object.getName())).build());
        }
        return Collections.singletonList(responseFactory.createBuilder().source("CommandUse").text(
            String.format("Cannot use %s on %s", subject, target)).build());
    }

    public static final class CommandUseBuilderImpl extends CommandBuilderImpl {
        private String target;
        private String subject;


        @Override
        public Command build() {
            return new CommandUseImpl(type, entityService, subject, target);
        }

        @Override
        public CommandBuilder parts(String[] parts) {
            int on = -1;
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equalsIgnoreCase("on") || parts[i].equalsIgnoreCase("in")) {
                    on = i;
                    break;
                }
            }
            StringBuilder subjectSb = new StringBuilder();
            StringBuilder targetSb = new StringBuilder();
            if (on > 0 && parts.length >= 4) {
                for (int i = 1; i < parts.length; i++) {
                    if (i < on) {
                        subjectSb.append(parts[i] + " ");
                    } else if (i > on) {
                        targetSb.append(parts[i] + " ");
                    }
                }
                subject = subjectSb.toString().trim();
                target = targetSb.toString().trim();
            }

            return this;
        }
    }
}
