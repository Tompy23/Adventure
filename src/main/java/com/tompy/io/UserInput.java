package com.tompy.io;

import com.tompy.command.api.Command;

public interface UserInput {
    public Command getCommand();
    public void quit();
}
