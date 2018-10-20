package com.tompy.entity.Character;

import com.tompy.adventure.Adventure;
import com.tompy.entity.EntityService;
import com.tompy.entity.compartment.CompartmentImpl;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CharacterImpl extends CompartmentImpl implements Character {
    private static final Logger LOGGER = LogManager.getLogger(CharacterImpl.class);

    protected CharacterImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService) {
        super(key, name, descriptors, description, entityService);
    }

    @Override
    public List<Response> move() {
        return null;
    }
}
