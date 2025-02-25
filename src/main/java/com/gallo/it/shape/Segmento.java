package com.gallo.it.shape;

import java.io.Serializable;

public class Segmento implements Shape, Serializable {
    private Punto p1;
    private Punto p2;

    public Segmento(Punto p1, Punto p2){
        setP1(p1);
        setP2(p2);
    }

    public Punto getP1(){
        return p1;
    }

    public void setP1(Punto p1){
        this.p1 = p1;
    }

    public Punto getP2(){
        return p2;
    }

    public void setP2(Punto p2){
        this.p2 = p2;
    }

    @Override
    public boolean inside(int x, int y, int w, int h){
        return p1.inside(x, y, w, h) && p2.inside(x, y, w, h);
    }

    @Override
    public void translate(int offx, int offy){
        p1.translate(offx, offy);
        p2.translate(offx, offy);
    }

    @Override
    public boolean equals(Object obj){
        if(((Segmento)(obj)).getP1().equals(p1)) return false;
        if(((Segmento)(obj)).getP2().equals(p2)) return false;
        
        return true;
    }

    @Override
    public String toString(){
        return "Segmento[" + p1 + ", " + p2 + "]";
    }
}