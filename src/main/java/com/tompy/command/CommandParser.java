package com.tompy.command;

import com.tompy.command.api.Command;

/**
 * Parses the command given by the player
 */
public interface CommandParser {

    /**
     * Translates the input String into a {@link Command}
     *
     * @param input - The input STring
     * @return - The {@link Command} specific implementation.
     */
    Command parseCommand(String input);
}
