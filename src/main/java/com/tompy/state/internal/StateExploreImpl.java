package com.tompy.state.internal;

import com.tompy.adventure.api.Adventure;
import com.tompy.command.api.Command;
import com.tompy.io.UserInput;
import com.tompy.player.api.Player;
import com.tompy.state.api.AdventureState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.util.Objects;

public class StateExploreImpl extends AdventureStateBaseImpl implements AdventureState {
    private final static Logger LOGGER = LogManager.getLogger(StateExploreImpl.class);
    private final UserInput userInput;
    private final PrintStream outStream;

    public StateExploreImpl(Player player, Adventure adventure, UserInput userInput,
        PrintStream outStream) {
        super(player, adventure);
        this.userInput = Objects.requireNonNull(userInput, "User Input cannot be null.");
        this.outStream = Objects.requireNonNull(outStream, "Output Stream cannot be null.");
    }

    @Override
    public void start() {
        LOGGER.info("Start Exploring");
    }

    @Override
    public void process() {
        Command command = userInput.getCommand();
        if (null != command) {
            command.execute(player, adventure).stream().forEachOrdered((a) -> outStream.println(a.render()));
        }
    }

    @Override
    public void end() {
        LOGGER.info("End Exploring");
    }
}
