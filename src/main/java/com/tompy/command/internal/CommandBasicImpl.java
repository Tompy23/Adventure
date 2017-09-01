package com.tompy.command.internal;

import com.tompy.response.api.Responsive;
import com.tompy.response.internal.ResponseImpl;
import com.tompy.command.api.Command;
import com.tompy.directive.CommandType;
import com.tompy.response.api.ResponseBuilderFactory;

public abstract class CommandBasicImpl extends Responsive implements Command {
    protected CommandType type = CommandType.COMMAND_QUIT;

    @Override
    public CommandType getType() {
        return type;
    }
}
