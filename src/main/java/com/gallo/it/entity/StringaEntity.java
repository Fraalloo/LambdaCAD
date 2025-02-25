package com.gallo.it.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.gallo.it.shape.Punto;

public class StringaEntity extends Entity {
    Punto pt;
    String title;
    
    public StringaEntity(String name, String title, Punto pt, int brush, Color color){
        super(name,false,brush,color);
        setTitle(title);
        setPt(pt);
    }
    
    public Punto getPt(){
        return pt;
    }

    public void setPt(Punto pt){
        this.pt = pt;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    @Override
    public void draw(Graphics2D g2d){
        super.draw(g2d);
        g2d.setFont(new Font("Monospace", Font.PLAIN, brush)); 
        g2d.drawString(title, (int)pt.getX(), (int)pt.getY());
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
