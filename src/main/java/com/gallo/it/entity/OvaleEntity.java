package com.gallo.it.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.gallo.it.shape.Ovale;

public class OvaleEntity extends Entity {
    private Ovale o;
    
    public OvaleEntity(String name, Ovale o, boolean fill, int brush, Color color){
        super(name,fill,brush,color);
        setO(o);
    }
    
    public Ovale getO(){
        return o;
    }

    public void setO(Ovale o){
        this.o = o;
    }

    @Override
    public void draw(Graphics2D g2d){
        super.draw(g2d);
        if(fill) g2d.fillOval((int)o.getP().getX(), (int)o.getP().getY(), (int)o.getWidth(), (int)o.getHeight());
        else g2d.drawOval((int)o.getP().getX(), (int)o.getP().getY(), (int)o.getWidth(), (int)o.getHeight());
    }
    
    @Override
    public boolean inside(int x, int y, int w, int h){
        return o.inside(x, y, w, h);
    }

    @Override
    public void translate(int offx, int offy){
        o.translate(offx, offy);
    }
}