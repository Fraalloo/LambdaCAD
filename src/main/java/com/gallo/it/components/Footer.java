package com.gallo.it.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.Cursor;

public class Footer extends JPanel {
    private JPanel brush_panel = new JPanel();
    private JSlider brush = new JSlider(JSlider.HORIZONTAL, 1, 20, 5);
    private JLabel brush_text = new JLabel("Brush: ");

    private JButton[] btns = new JButton[12];
    private Color[] colors = {
        Color.black,
        Color.white,
        Color.blue,
        Color.cyan,
        Color.DARK_GRAY,
        Color.gray,
        Color.green,
        Color.magenta,
        Color.orange,
        Color.pink,
        Color.red,
        Color.yellow
    };

    private JLabel year = new JLabel("© Gallo " + LocalDate.now().getYear());

    public Footer(EventListener list){
        setBackground(Color.black);
        setLayout(new BorderLayout());
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        left.setBackground(Color.black);
        right.setBackground(Color.black);

        for(int i = 0; i < 12; i++){
            btns[i] = new JButton(" ");
            btns[i].setOpaque(false);
            btns[i].setBorderPainted(false);
            btns[i].setContentAreaFilled(true);
            btns[i].setBackground(colors[i]);
            btns[i].setForeground(colors[i]);
            btns[i].addActionListener((ActionListener)list);
            btns[i].setActionCommand(String.valueOf(i));
            btns[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            left.add(btns[i]);
        }

        brush.setBackground(Color.black);
        brush.setUI(new BasicSliderUI(brush){
            @Override
            public void paintThumb(Graphics g){
                g.setColor(Color.white);
                g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }

            @Override
            public void paintTrack(Graphics g){
                g.setColor(Color.white);
                g.fillRect(trackRect.x, trackRect.y + trackRect.height / 2 - 2, trackRect.width, 4);
            }
        });
        brush.addChangeListener((ChangeListener)list);
        brush.setFocusable(false);
        brush_text.setFont(new Font("Monospace", Font.BOLD,18));
        brush_text.setForeground(Color.white);
        brush_panel.add(brush_text);
        brush.setCursor(new Cursor(Cursor.HAND_CURSOR));
        brush_panel.add(brush);
        brush_panel.setBackground(Color.black);
        left.add(brush_panel);

        year.setForeground(Color.white);
        year.setFont(new Font("Monospace", Font.BOLD,18));
        right.add(year);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
    }

    public void setValue(int stroke){
        brush.setValue(stroke);
    }

    public Color[] getColors(){
        return colors;
    }
}