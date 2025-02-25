package com.gallo.it.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.gallo.it.shape.Punto;

public class PuntoEntity extends Entity {
    Punto pt;
    
    public PuntoEntity(String name, Punto pt, int brush, Color color){
        super(name,false,brush,color);
        setPt(pt);
    }
    
    public Punto getPt(){
        return pt;
    }

    public void setPt(Punto pt){
        this.pt = pt;
    }

    @Override
    public void draw(Graphics2D g2d){
        super.draw(g2d);
        g2d.fillOval((int)pt.getX() - brush/2, (int)pt.getY() - brush/2, brush, brush);
    }
    
    @Override
    public boolean inside(int x, int y, int w, int h){
        return pt.inside(x, y, w, h);
    }

    @Override
    public void translate(int offx, int offy){
        pt.translate(offx, offy);
    }
}