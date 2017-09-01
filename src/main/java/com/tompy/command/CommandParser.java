package com.tompy.command;

import com.tompy.command.api.Command;

public interface CommandParser {
    public Command parseCommand(String input);
}
