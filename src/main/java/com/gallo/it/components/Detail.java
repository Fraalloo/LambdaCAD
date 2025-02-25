package com.gallo.it.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.gallo.it.entity.Entity;

public class Detail extends JPanel {
    ActionListener listener;

    public Detail(EventListener list){
        listener = (ActionListener)list;
        setBackground(Color.lightGray);
        setFocusable(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(230, 300));
        setBorder(BorderFactory.createTitledBorder("Lista oggetti"));
    }

    public void reset(){
        repaint();
    }

    public void update(ArrayList<Entity> entities){
        removeAll();
        for(Entity e: entities){
            JButton txt = new JButton(e.getName());
            txt.setFont(new Font("Monospace",Font.BOLD,18));
            txt.setBackground(new Color(0,0,0,0));
            txt.setForeground(Color.black);
            txt.setBorderPainted(false);  
            txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
            txt.addActionListener(listener);     
            add(txt);
        }
        revalidate();
        repaint();
    }

    public void update(ArrayList<Entity> entities, ArrayList<String> names){
        removeAll();
        for(Entity e: entities){
            JButton txt = new JButton(e.getName());
            txt.setFont(new Font("Monospace",Font.BOLD,18));
            txt.setFont(new Font("Monospace",Font.BOLD,18));
            txt.setBackground(names.contains(e.getName()) ? Color.white : new Color(0,0,0,0));
            txt.setForeground(Color.black);
            txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
            txt.addActionListener(listener);     
            add(txt);
        }
        revalidate();
        repaint();
    }
}
