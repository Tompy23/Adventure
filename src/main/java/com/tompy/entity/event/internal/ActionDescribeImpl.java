package com.tompy.entity.event.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.adventure.internal.AdventureUtils;
import com.tompy.attribute.api.Attribute;
import com.tompy.entity.api.Entity;
import com.tompy.entity.api.EntityService;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ActionDescribeImpl extends ActionImpl {
    private final List<String> responses;
    private final EntityService entityService;

    public ActionDescribeImpl(Entity entity, String[] responses, EntityService entityService) {
        super(entity);
        this.responses = Arrays.asList(Objects.requireNonNull(responses, "Response List cannot be null."));
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
    }

    @Override
    public List<Response> apply(Player player, Adventure adventure) {
        List<Response> returnValue = new ArrayList<>();
        returnValue.addAll(responses.stream().
            map((r) -> responseFactory.createBuilder().source(source).text(substitution(r)).build()).collect(Collectors.toList()));
        return returnValue;
    }

    // ${entity|attribute|applies|does not apply} ${required|required|optional|optional}
    private String substitution(String text) {
        int start = text.indexOf("${");
        int end = text.indexOf("}");
        if (start == -1 && end == -1) {
            return text;
        }
        String[] parts = text.substring(start + 2, end).split(Pattern.quote("|"));
        if (parts.length == 0) {
            return text;
        }
        Entity thisEntity = entityService.getEntityByName(parts[0]);
        Attribute attribute = AdventureUtils.getAttribute(parts[1]);
        boolean attributable = entityService.is(thisEntity, attribute);

        StringBuilder sb = new StringBuilder();
        sb.append(text.substring(0, start));
        if (parts.length == 4) {
            sb.append(attributable ? parts[2] : parts[3]);
        } else {
            sb.append(attributable ? attribute.getDoesApply() : attribute.getDoesNotApply());
        }
        sb.append(text.substring(end + 1));

        return substitution(sb.toString());
    }
}
