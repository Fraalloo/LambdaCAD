package com.gallo.it.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.gallo.it.shape.Rettangolo;

public class SelectionEntity extends Entity {
    private Rettangolo r;
    
    public SelectionEntity(Rettangolo r, boolean fill, int brush, Color color){
        super("ae",fill,brush,color);
        setR(r);
        selected = true;
    }
    
    public Rettangolo getR(){
        return r;
    }

    public void setR(Rettangolo r){
        this.r = r;
    }

    public void draw(Graphics2D g2d){
        super.draw(g2d);
        g2d.drawRect((int)r.getP().getX(), (int)r.getP().getY(), (int)r.getWidth(), (int)r.getHeight());
    }
    
    @Override
    public boolean inside(int x, int y, int w, int h){
        return false;
    }

    @Override
    public void translate(int offx, int offy){}
}