package com.tompy.entity.item.interna;

import com.tompy.entity.EntityUtil;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityFacade;
import com.tompy.entity.api.EntityService;
import com.tompy.response.api.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ItemKeyImpl extends ItemImpl {
    private static final Logger LOGGER = LogManager.getLogger(ItemKeyImpl.class);
    private final EntityFacade target;

    public ItemKeyImpl(Long key, String name, List<String> descriptors, String description, EntityService entityService,
                       EntityFacade target) {
        super(key, name, descriptors, description, entityService);
        this.target = target;
    }

    @Override
    public List<Response> use() {
        List<Response> returnValue = new ArrayList<>();

        LOGGER.info("Using [{}] on [{}]", new String[] {getName(), target.getEntity().getName()});

        returnValue.add(this.responseFactory.createBuilder().source(getSource())
                            .text(String.format("Using %s on %s", getName(), target.getEntity().getName())).build());

        if (EntityUtil.is(target)) {
            EntityUtil.remove(target);
        } else {
            EntityUtil.add(target);
        }

        return returnValue;
    }

    @Override
    public boolean hasTarget(Entity entity) {
        return entity.getKey().equals(target.getEntity().getKey());
    }

}
