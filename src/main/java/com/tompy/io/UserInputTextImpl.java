package com.tompy.io;

import com.tompy.command.CommandFactory;
import com.tompy.command.CommandFactoryImpl;
import com.tompy.command.api.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class UserInputTextImpl implements UserInput {
    private CommandFactory factory = new CommandFactoryImpl();
    private BufferedReader br = null;

    public UserInputTextImpl() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void quit() {
        try {
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public Command getCommand() {
        Command returnValue = null;
        try {
            System.out.print(">>> ");
            String input = br.readLine();
            returnValue = factory.createCommand(input.split(" "));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return returnValue;
    }

    @Override
    public Long getSelection(Map<Long, String> options) {
        Long[] selectionList = setUpSelection(options);
        displaySelection(options, selectionList);
        try {
            System.out.print("=== ");
            String input = br.readLine();
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > options.size()) {
                // TODO this is a bad choice.  Is this ok?
                System.out.println("Not a valid choice.  Try again.");
                return getSelection(options);
            } else {
                return selectionList[choice - 1];
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Not a valid choice.  Try again.");
            return getSelection(options);
        }
    }

    @Override
    public String getResponse(String question) {
        String returnValue = "";
        try {
            System.out.print(question);
            System.out.print("??? ");
            returnValue = br.readLine();
        } catch(IOException ioe) {
            System.out.println("ERROR");
            ioe.printStackTrace();
        }
        return returnValue;
    }

    private Long[] setUpSelection(Map<Long, String> options) {
        Long[] returnValue = new Long[options.keySet().size()];
        Integer index = 0;
        for (Long key : options.keySet()) {
            returnValue[index++] = key;
        }

        return returnValue;
    }

    private void displaySelection(Map<Long, String> options, Long[] selections) {
        Integer index = 1;
        for (Long selection : selections) {
            System.out.println(index++ + ". " + options.get(selection));
        }
    }
}
