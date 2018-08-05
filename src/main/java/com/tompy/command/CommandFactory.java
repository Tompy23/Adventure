package com.tompy.command;

import com.tompy.command.api.Command;

public interface CommandFactory {
    Command createCommand(String[] inputs);
}
