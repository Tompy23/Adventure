package com.tompy.command;

import com.tompy.command.api.Command;

public interface CommandFactory {
    public Command createCommand(String[] inputs);
}
