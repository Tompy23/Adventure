package com.tompy;


import com.tompy.adventure.api.Adventure;
import com.tompy.adventure.internal.AdventureImpl;
import com.tompy.attribute.internal.AttributeManagerFactoryImpl;
import com.tompy.command.api.Command;
import com.tompy.entity.api.EntityService;
import com.tompy.entity.internal.EntityFacadeBuilderFactoryImpl;
import com.tompy.entity.internal.EntityServiceImpl;
import com.tompy.exit.internal.ExitBuilderFactoryImpl;
import com.tompy.io.UserInput;
import com.tompy.io.UserInputTextImpl;
import com.tompy.player.api.Player;
import com.tompy.player.internal.PlayerImpl;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        App a = new App();
        System.exit(a.go(args));
    }

    public int go(String[] args) {
        InputStream inStream = System.in;
        PrintStream outStream = System.out;
        EntityService entityService = new EntityServiceImpl(new AttributeManagerFactoryImpl());
        UserInput ui = new UserInputTextImpl(inStream, outStream, entityService);
        Adventure adventure = new AdventureImpl(entityService, new EntityFacadeBuilderFactoryImpl(entityService),
                new ExitBuilderFactoryImpl(), ui);
        Player player = new PlayerImpl(ui.getResponse("Player name?"), null);

        adventure.create();
        adventure.start(player);

        while (adventure.proceed()) {
            Command command = ui.getCommand();
            if (null != command) {
                command.execute(player, adventure).stream().forEachOrdered((a) -> outStream.println(a.render()));
            }
        }

        return 0;
    }
}
