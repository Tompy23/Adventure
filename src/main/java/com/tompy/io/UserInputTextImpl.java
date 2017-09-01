package com.tompy.io;

import com.tompy.command.api.Command;
import com.tompy.command.CommandFactory;
import com.tompy.command.CommandFactoryImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputTextImpl implements UserInput {
    private CommandFactory factory = new CommandFactoryImpl();
    private BufferedReader br = null;

    public UserInputTextImpl() { br = new BufferedReader(new InputStreamReader(System.in)); }

    @Override
    public void quit() {
        try {
            br.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public Command getCommand() {
        Command returnValue = null;
        try {
            System.out.println(">>> ");
            String input = br.readLine();
            returnValue = factory.createCommand(input.split(" "));
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return returnValue;
    }
}
