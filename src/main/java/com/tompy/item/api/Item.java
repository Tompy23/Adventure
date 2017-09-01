package com.tompy.item.api;

public interface Item {
    public String getName();
    public String getGenericDescription();
    public boolean useable();
    public boolean targetable();
}
