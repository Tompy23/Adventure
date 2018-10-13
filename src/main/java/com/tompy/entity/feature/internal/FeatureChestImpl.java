package com.tompy.entity.feature.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.attribute.api.Attribute;
import com.tompy.directive.EventType;
import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.EntityService;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A Chest is a feature that can be open/close, locked, etc.
 */
public class FeatureChestImpl extends FeatureBasicImpl {
    private static final Logger LOGGER = LogManager.getLogger(FeatureChestImpl.class);

    protected FeatureChestImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        EntityUtil.add(visible);
    }
}
