package com.tompy.command.api;

import com.tompy.common.Builder;
import com.tompy.directive.CommandType;
import com.tompy.entity.api.EntityService;

public interface CommandBuilder extends Builder<Command> {
    CommandBuilder parts(String[] parts);

    CommandBuilder type(CommandType type);

    CommandBuilder entityService(EntityService entityService);
}
