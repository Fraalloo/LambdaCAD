package com.gallo.it.shape;

public interface Shape {
    public boolean inside(int x, int y, int h, int w);
    public void translate(int offx, int offy);
}