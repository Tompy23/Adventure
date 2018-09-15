package com.tompy.command.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
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
import java.util.Optional;

public class CommandSearchFeatureImpl extends CommandSearchImpl {
    private static final Logger LOGGER = LogManager.getLogger(CommandSearchFeatureImpl.class);

    public CommandSearchFeatureImpl(CommandType type, EntityService entityService, String target,
        String secondaryTarget) {
        super(type, entityService, target, secondaryTarget);
    }

    @Override
    public List<Response> execute(Player player, Adventure adventure) {
        LOGGER.info("Executing Search Feature");
        List<Response> returnValue = new ArrayList<>();

        List<Feature> features = player.getArea().getAllFeatures();
        Long featureKey = EntityUtil.findEntityByDescription(features, target, adventure.getUI());
        Optional<Feature> optObject = features.stream().filter((i) -> i.getKey().equals(featureKey)).findFirst();

        if (optObject.isPresent()) {
            Feature object = optObject.get();
            returnValue.addAll(object.search());
            if (entityService.is(object, Attribute.OPEN)) {
                object.getAllItems().stream().forEach((i) -> {
                    returnValue.add(
                        responseFactory.createBuilder().source("CommandSearchFeature").text(i.getDetailDescription())
                            .build());
                    entityService.add(i, Attribute.VISIBLE);

                });
            }
        }

        return returnValue;
    }
}