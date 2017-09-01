package com.tompy.command.api;

import com.tompy.common.Builder;
import com.tompy.directive.CommandType;

public interface CommandBuilder extends Builder<Command> {
    public CommandBuilder parts(String[] parts);
    public CommandBuilder type(CommandType type);
}
