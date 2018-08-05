package com.tompy.command.internal;

import com.tompy.response.api.Responsive;
import com.tompy.command.api.Command;
import com.tompy.directive.CommandType;

public abstract class CommandBasicImpl extends Responsive implements Command {
    protected CommandType type = CommandType.COMMAND_QUIT;

    @Override
    public CommandType getType() {
        return type;
    }
}
