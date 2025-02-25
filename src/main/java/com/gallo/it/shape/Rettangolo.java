package com.gallo.it.shape;

import java.io.Serializable;

public class Rettangolo implements Shape, Serializable {
    private Punto p;
    private double height;
    private double width;
    
    public Rettangolo(Punto p, double width, double height){
        setP(p);
        setHeight(height);
        setWidth(width);
    }
    
    public Punto getP() {
        return p;
    }

    public void setP(Punto p) {
        this.p = p;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public boolean inside(int x, int y, int w, int h){
        return p.inside(x, y, w, h) && new Punto(p.getX() + width, p.getY() + height).inside(x, y, w, h);
    }

    @Override
    public void translate(int offx, int offy){
        p.translate(offx, offy);
    }
}