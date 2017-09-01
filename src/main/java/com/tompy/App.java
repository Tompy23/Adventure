package com.tompy;


import com.tompy.adventure.api.Adventure;
import com.tompy.command.api.Command;
import com.tompy.adventure.internal.AdventureImpl;
import com.tompy.io.UserInput;
import com.tompy.io.UserInputTextImpl;
import com.tompy.player.api.Player;
import com.tompy.player.internal.PlayerImpl;
import com.tompy.response.api.Response;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        App a = new App();
        System.exit(a.go(args));
    }

    public int go(String[] args) {
        UserInput ui = new UserInputTextImpl();
        Player player = new PlayerImpl(null);
        Adventure adventure = new AdventureImpl();
        adventure.start(player);
        while (adventure.proceed()) {
            Command command = ui.getCommand();
            if (null != command) {
                command.execute(player, adventure).stream().forEachOrdered(Response::render);
            }
        }

        return 0;
    }
}
