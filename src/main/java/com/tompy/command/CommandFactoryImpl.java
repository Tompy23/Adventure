package com.tompy.command;

import com.tompy.command.api.Command;
import com.tompy.command.api.CommandBuilder;
import com.tompy.command.api.CommandBuilderFactory;
import com.tompy.command.CommandFactory;
import com.tompy.command.internal.CommandMoveImpl;
import com.tompy.command.internal.CommandNullImpl;
import com.tompy.command.internal.CommandQuitImpl;
import com.tompy.command.internal.CommandSearchImpl;
import com.tompy.adventure.internal.AdventureUtils;

import java.util.HashMap;
import java.util.Map;

public class CommandFactoryImpl implements CommandFactory {
    private static final String COMMAND_NULL = "NULL";
    private static final String COMMAND_MOVE = "MOVE";
    private static final String COMMAND_QUIT = "QUIT";
    private static final String COMMAND_RUN = "RUN";
    private static final String COMMAND_SEARCH = "SEARCH";
    private static final String COMMAND_USE = "USE";

    private Map<String, CommandBuilderFactory> factoryMap = new HashMap<>();

    public CommandFactoryImpl() {
        factoryMap.put(COMMAND_NULL, CommandNullImpl.createBuilderFactory());
        factoryMap.put(COMMAND_MOVE, CommandMoveImpl.createBuilderFactory());
        factoryMap.put(COMMAND_QUIT, CommandQuitImpl.createBuilderFactory());
        factoryMap.put(COMMAND_RUN, CommandMoveImpl.createBuilderFactory());
        factoryMap.put(COMMAND_SEARCH, CommandSearchImpl.createBuilderFactory());
    }

    @Override
    public Command createCommand(String[] inputs) {
        String[] commandInputs = new String[inputs.length];
        for(int i = 0; i < inputs.length; i++) {
            commandInputs[i] = inputs[i].toUpperCase();
        }

        CommandBuilder command;
        CommandBuilderFactory cbf = factoryMap.get(commandInputs[0]);
        if (null != cbf) {
            command = cbf.createBuilder();
            if (command != null) {
                return command.parts(commandInputs).type(AdventureUtils.getCommandType(commandInputs[0])).build();
            }
        }

        return factoryMap.get(COMMAND_NULL).createBuilder().build();
    }
}
