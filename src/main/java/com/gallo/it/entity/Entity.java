package com.gallo.it.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected String name;
    protected boolean fill;
    protected int brush;
    protected Color color;
    protected double scaleFactor;

    protected static final float[] dashPattern = {10, 5};
    protected Color selectedColor = new Color(0, 127, 225, 75);
    protected boolean selected = false;

    public Entity(String name, boolean fill, int brush, Color color){
        setName(name);
        setFill(fill);
        setBrush(brush);
        setColor(color);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public boolean getFill(){
        return fill;
    }

    public void setFill(boolean fill){
        this.fill = fill;
    }
    
    public int getBrush(){
        return brush;
    }

    public void setBrush(int brush){
        this.brush = brush;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public double getScaleFactor(){
        return scaleFactor;
    }

    public void setScaleFactor(double scaleFactor){
        this.scaleFactor = scaleFactor;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }
    
    public void draw(Graphics2D g2d){
        g2d.setColor(selected ? selectedColor : color);
        if(selected){
            BasicStroke dashedStroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dashPattern, 0);
            g2d.setStroke(dashedStroke);
        }else g2d.setStroke(new BasicStroke(brush));
    }

    public abstract boolean inside(int x, int y, int w, int h);
    public abstract void translate(int offx, int offy);
}