package com.gallo.it.components;

import java.util.ArrayList;
import java.util.EventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

import com.gallo.it.entity.Entity;

public class Draw extends JPanel {
    private int gridSize = 20;
    private Color gridColor = new Color(230, 230, 230);
    private boolean grid = true;
    private double scaleFactor = 1.0;
    private int offsetX = 0;
    private int offsetY = 0;

    private ArrayList<Entity> list = new ArrayList<>();
    private Entity current_entity;
    
    public Draw(EventListener list){
        setBackground(Color.white);
        setFocusable(true);
        addMouseListener((MouseListener)list);
        addMouseMotionListener((MouseMotionListener)list);
        addMouseWheelListener((MouseWheelListener)list);
    }

    public ArrayList<Entity> getList(){
        return list;
    }

    public void addList(Entity e){
        list.add(e);
    }

    public Entity getCurrentEntity(){
        return current_entity;
    }
    
    public void setCurrentEntity(Entity current_entity){
        this.current_entity = current_entity;
    }

    public void exportToPNG(File file) throws IOException {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        printAll(g2d);
        g2d.dispose();
        
        ImageIO.write(image, "png", file);
    }

    public void save(File file) throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(list);
        oos.close();
    }
    
    @SuppressWarnings("unchecked")
    public void load(File file) throws ClassNotFoundException, FileNotFoundException, IOException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        list.addAll((ArrayList<Entity>)ois.readObject());
        ois.close();
        repaint();
    }
    
    public void reset(){
        list.clear();
        repaint();
    }

    public void zoomin(){
        scaleFactor *= 1.1;
        repaint();
    }

    public void zoomout(){
        scaleFactor /= 1.1;
        repaint();
    }

    public double getZoom(){
        return scaleFactor;
    }

    public void resetZoom(){
        scaleFactor = 1;
    }

    public void toggleGrid(){
        grid = !grid;
        repaint();
    }
    
    public void setOffset(int x, int y){
        offsetX = x;
        offsetY = y;
    }

    public int getOffsetX(){
        return offsetX;
    }

    public int getOffsetY(){
        return offsetY;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        if(grid){
            g2d.setColor(gridColor);
            for(int x = 0; x < getWidth(); x += gridSize){
                g2d.drawLine(x, 0, x, getHeight());
            }
            for(int y = 0; y < getHeight(); y += gridSize){
                g2d.drawLine(0, y, getWidth(), y);
            }
        }


        AffineTransform oldTransform = g2d.getTransform();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(offsetX, offsetY);
        g2d.scale(scaleFactor, scaleFactor);
        
        for(Entity e: list){
            e.draw(g2d);
        } 
        
        if(current_entity != null) current_entity.draw(g2d);
        g2d.setTransform(oldTransform);
    }
}