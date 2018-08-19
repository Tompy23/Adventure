package com.tompy.command.internal;

import com.tompy.entity.api.EntityService;
import com.tompy.response.api.Responsive;
import com.tompy.command.api.Command;
import com.tompy.directive.CommandType;

public abstract class CommandBasicImpl extends Responsive implements Command {
    protected EntityService entityService;
    protected CommandType type = CommandType.COMMAND_QUIT;

    protected CommandBasicImpl(CommandType type) {
        this.type = type;
        this.entityService = null;
    }

    protected CommandBasicImpl(CommandType type, EntityService entityService) {
        this.type = type;
        this.entityService = entityService;
    }

    @Override
    public CommandType getType() {
        return type;
    }
}
