package com.tompy.command;

import com.tompy.command.api.Command;

public class CommandParserBasicImpl implements CommandParser {
    private CommandFactory factory = new CommandFactoryImpl();

    @Override
    public Command parseCommand(String input) {
        Command returnValue = null;
        String [] parts = input.split(" ");

        returnValue = factory.createCommand(parts);

        return returnValue;
    }
}
