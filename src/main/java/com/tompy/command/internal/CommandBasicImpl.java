package com.tompy.command.internal;

import com.tompy.command.api.Command;
import com.tompy.directive.CommandType;
import com.tompy.entity.api.EntityService;
import com.tompy.response.api.Responsive;

import java.util.Objects;

public abstract class CommandBasicImpl extends Responsive implements Command {
    protected EntityService entityService;
    protected CommandType type = CommandType.COMMAND_QUIT;

    protected CommandBasicImpl(CommandType type) {
        this.type = Objects.requireNonNull(type, "Type cannot be null.");
        this.entityService = null;
    }

    protected CommandBasicImpl(CommandType type, EntityService entityService) {
        this.type = Objects.requireNonNull(type, "Type cannot be null.");
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
    }

    @Override
    public CommandType getType() {
        return type;
    }
}
