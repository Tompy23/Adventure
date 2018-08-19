package com.tompy.command.internal;

import com.tompy.command.api.CommandBuilder;
import com.tompy.directive.CommandType;
import com.tompy.entity.api.EntityService;

public abstract class CommandBuilderImpl implements CommandBuilder {
    protected EntityService entityService;
    protected CommandType type = null;


    @Override
    public CommandBuilder type(CommandType type) {
        this.type = type;
        return this;
    }

    @Override
    public CommandBuilder entityService(EntityService entityService) {
        this.entityService = entityService;
        return this;
    }
}
