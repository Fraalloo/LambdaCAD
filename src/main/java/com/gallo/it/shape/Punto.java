package com.gallo.it.shape;

import java.io.Serializable;

public class Punto implements Shape, Serializable {
    private double x;
    private double y;

    public Punto(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Punto(){
        this(0, 0);
    }

    public double getX(){
        return x;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getY(){
        return y;
    }

    public void setY(double y){
        this.y = y;
    }

    public double distanza(Punto p){
        return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
    }

    @Override
    public boolean inside(int x, int y, int w, int h){
        return getX() >= x &&  getX() <= x + w &&  getY() >= y && getY() <= y + h;
    }

    @Override
    public void translate(int offx, int offy){
        x += offx;
        y += offy;
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj){
        if(((Punto)(obj)).getX() != x) return false;
        if(((Punto)(obj)).getY() != y) return false;
        
        return true;
    }
}
