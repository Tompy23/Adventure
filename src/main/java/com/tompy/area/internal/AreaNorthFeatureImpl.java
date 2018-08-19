package com.tompy.area.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.area.api.Area;
import com.tompy.directive.Direction;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.compartment.api.Compartment;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.List;

public class AreaNorthFeatureImpl extends AreaImpl implements Area {
    protected AreaNorthFeatureImpl(String name, String description, String searchDescription, String[]
            searchDirectionDesription, Compartment compartment, EntityService entityService) {
        super(name, description, searchDescription, searchDirectionDesription, compartment, entityService);
    }

    @Override
    public List<Response> searchDirection(Direction direction, Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();

        if (searchDirectionDescription[direction.ordinal()] != null) {
            returnValue.add(
                    responseFactory.createBuilder().text(searchDirectionDescription[direction.ordinal()]).source(
                            name).build());
        } else {
            returnValue.add(
                    responseFactory.createBuilder().text("Nothing special to the " + direction.getDescription()).source(
                            name).build());
        }

        return returnValue;
    }
}
