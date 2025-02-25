package com.gallo.it.components;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.EventListener;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;

public class Header extends JPanel {
    private JButton[] btns = {
        new JButton(new ImageIcon(getClass().getResource("/images/entity/pointer.bmp"))),
        new JButton(new ImageIcon(getClass().getResource("/images/entity/cattura.png"))),
        new JButton(new ImageIcon(getClass().getResource("/images/entity/punto.bmp"))),
        new JButton(new ImageIcon(getClass().getResource("/images/entity/segmento.bmp"))),
        new JButton(new ImageIcon(getClass().getResource("/images/entity/rettangolo.bmp"))),
        new JButton(new ImageIcon(getClass().getResource("/images/entity/ovale.bmp"))),
        new JButton(new ImageIcon(getClass().getResource("/images/entity/stringa.bmp"))),
        new JButton(new ImageIcon(getClass().getResource("/images/entity/fill.bmp"))),
        new JButton(new ImageIcon(getClass().getResource("/images/entity/grid.png")))
    };
    private String[] coms = {
        "Pointer",
        "Selection",
        "Punto",
        "Segmento",
        "Rettangolo",
        "Ovale",
        "Stringa",
        "Riempi",
        "Griglia"
    };
    private String current = coms[0];
    private JLabel current_label = new JLabel();
    
    public Header(EventListener list){        
        setBackground(Color.black);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        current_label.setForeground(Color.white);
        current_label.setFont(new Font("Monospaced",Font.BOLD,18));
        add(current_label);
        
        for(int i = 0; i < btns.length; i++){
            System.out.println(coms[i]);
            btns[i].setForeground(Color.white);
            btns[i].addActionListener((ActionListener)list);
            btns[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            btns[i].setActionCommand(coms[i]);
            btns[i].setContentAreaFilled(false);
            btns[i].setBorderPainted(true);
            add(btns[i]);

            int width = btns[i].getIcon().getIconWidth();
            int height = btns[i].getIcon().getIconHeight();
            btns[i].setPreferredSize(new Dimension(width, height));
            btns[i].setMinimumSize(new Dimension(width, height));
            btns[i].setMaximumSize(new Dimension(width, height));
        }   

        update(current);
    }
    
    public void update(String curr){
        current = curr;
        current_label.setText("Current: " + current);
        System.out.println(current_label.getText());
        for(int i = 0; i < "Rettangolo   ".length() - curr.length(); i++){
          current_label.setText(current_label.getText() + " ");  
        }
    }
}