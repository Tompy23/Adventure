package com.tompy.command;

import com.tompy.common.Builder;
import com.tompy.directive.CommandType;
import com.tompy.entity.EntityService;

public interface CommandBuilder extends Builder<Command> {
    CommandBuilder parts(String[] parts);

    CommandBuilder type(CommandType type);

    CommandBuilder entityService(EntityService entityService);
}
