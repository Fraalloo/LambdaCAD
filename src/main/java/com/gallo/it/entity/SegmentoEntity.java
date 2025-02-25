package com.gallo.it.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.gallo.it.shape.Segmento;

public class SegmentoEntity extends Entity {
    Segmento s;
    
    public SegmentoEntity(String name, Segmento s, int brush, Color color){
        super(name,false,brush,color);
        setS(s);
    }
    
    public Segmento getS(){
        return s;
    }

    public void setS(Segmento s){
        this.s = s;
    }

    @Override
    public void draw(Graphics2D g2d){
        super.draw(g2d);
        g2d.drawLine((int)s.getP1().getX() - brush/2, (int)s.getP1().getY() - brush/2, (int)s.getP2().getX() - brush/2, (int)s.getP2().getY() - brush/2);
    }
    
    @Override
    public boolean inside(int x, int y, int w, int h){
        return s.inside(x, y, w, h);
    }

    @Override
    public void translate(int offx, int offy){
        s.translate(offx, offy);
    }
}