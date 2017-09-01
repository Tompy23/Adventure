package com.tompy.command.api;

import com.tompy.adventure.api.Adventure;
import com.tompy.directive.CommandType;
import com.tompy.player.api.Player;
import com.tompy.response.api.Response;

import java.util.List;

public interface Command {
    public CommandType getType();
    public List<Response> execute(Player player, Adventure adventure);
}
