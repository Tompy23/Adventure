package com.tompy.entity.event.internal;

import com.tompy.entity.api.Entity;
import com.tompy.response.api.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ActionDescribeImpl extends ActionImpl {
    private final List<String> responses;

    public ActionDescribeImpl(Entity entity, String[] responses) {
        super(entity);
        Objects.requireNonNull(responses, "Response List cannot be null.");
        this.responses = Arrays.asList(responses);
    }

    @Override
    public List<Response> apply() {
        List<Response> returnValue = new ArrayList<>();
        returnValue.addAll(responses.stream().
            map((r) -> responseFactory.createBuilder().source(source).text(r).build()).collect(Collectors.toList()));
        return returnValue;
    }
}
