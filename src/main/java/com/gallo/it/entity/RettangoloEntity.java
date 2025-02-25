package com.gallo.it.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.gallo.it.shape.Rettangolo;

public class RettangoloEntity extends Entity {
    private Rettangolo r;
    
    public RettangoloEntity(String name, Rettangolo r, boolean fill, int brush, Color color){
        super(name,fill,brush,color);
        setR(r);
    }
    
    public Rettangolo getR(){
        return r;
    }

    public void setR(Rettangolo r){
        this.r = r;
    }

    @Override
    public void draw(Graphics2D g2d){
        super.draw(g2d);
        if(fill) g2d.fillRect((int)r.getP().getX(), (int)r.getP().getY(), (int)r.getWidth(), (int)r.getHeight());
        else g2d.drawRect((int)r.getP().getX(), (int)r.getP().getY(), (int)r.getWidth(), (int)r.getHeight());
    }
    
    @Override
    public boolean inside(int x, int y, int w, int h){
        return r.inside(x, y, w, h);
    }

    @Override
    public void translate(int offx, int offy){
        r.translate(offx, offy);
    }
}