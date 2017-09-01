package com.tompy.area.api;

import com.tompy.directive.Direction;
import com.tompy.response.api.Response;

import java.util.List;

public interface Exit {
    public List<Response> passThru();
    public Direction getDirection();
    public Area getArea();
    public void setParallel(Exit exit);
    public Exit getParallel();
}
