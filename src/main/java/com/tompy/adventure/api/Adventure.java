package com.tompy.adventure.api;

import com.tompy.area.api.Area;
import com.tompy.player.api.Player;

public interface Adventure {
    public void start(Player player);
    public void stop(Player player);
    public Area getArea(String name);
    public boolean proceed();
}
