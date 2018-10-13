package com.tompy.io;

import com.tompy.command.Command;

import java.util.Map;

public interface UserInput {
    Command getCommand();

    Long getSelection(Map<Long, String> selection);

    String getResponse(String question);

    void quit();
}
