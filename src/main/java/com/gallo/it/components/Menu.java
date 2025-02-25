package com.gallo.it.components;

import java.awt.event.ActionListener;
import java.util.EventListener;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Menu extends JMenuBar {
    private JMenuItem[] fileMenus = {
        new JMenuItem("Nuovo Disegno"),
        new JMenuItem("Apri Disegno"),
        new JMenuItem("Salva Disegno"),
        new JMenuItem("Salva Disegno con Nome"),
        new JMenuItem("Stampa Disegno"),
        new JMenuItem("Esporta come PNG")
    };
    private String[] fileIcons = {
        "/images/filemenu/new.png",
        "/images/filemenu/open.png",
        "/images/filemenu/save.png",
        "/images/filemenu/saveas.png",
        "/images/filemenu/print.png",
        "/images/filemenu/export.png"
    };
    private KeyStroke[] fileStroke = {
        KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_MASK | InputEvent.SHIFT_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.META_MASK)
    };

    private JMenuItem[] disegnaMenus = {
        new JMenuItem("Cancella"),
        new JMenuItem("Griglia"),
        new JMenuItem("Riempi"),
        new JMenuItem("Colore avanzato"),
        new JMenuItem("Spessore avanzato")
    };
    private String[] disegnaIcons = {
        "/images/disegnamenu/delete.png",
        "/images/disegnamenu/grid.png",
        "/images/disegnamenu/fill.png",
        "/images/disegnamenu/color.png",
        "/images/disegnamenu/stroke.png",
    };
    private KeyStroke[] disegnaStroke = {
        KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.META_MASK)
    };

    private JMenuItem[] visualMenus = {
        new JMenuItem("Intestazione"),
        new JMenuItem("Lista oggetti"),
        new JMenuItem("Piè di pagina"),
        new JMenuItem("Reset zoom"),
        new JMenuItem("Reset posizione"),
    };
    private String[] visualIcons = {
        "/images/vismenu/1.png",
        "/images/vismenu/2.png",
        "/images/vismenu/1.png",
        "/images/vismenu/3.png",
        "/images/vismenu/3.png"
    };
    private KeyStroke[] visualStroke = {
        KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.META_MASK | InputEvent.SHIFT_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.META_MASK | InputEvent.SHIFT_MASK),
    };

    private JMenuItem[] optionMenus = {
        new JMenuItem("Undo"),
        new JMenuItem("Redo"),
        new JMenuItem("Rinomina"),
        new JMenuItem("Seleziona Tutto"),
        new JMenuItem("Informazioni"),
        new JMenuItem("GitHub")
    };
    private String[] optionIcons = {
        "/images/optmenu/undo.png",
        "/images/optmenu/redo.png",
        "/images/optmenu/rename.png",
        "/images/optmenu/tutto.png",
        "/images/optmenu/info.png",
        "/images/optmenu/git.png"
    };
    private KeyStroke[] optionStroke = {
        KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.META_MASK | InputEvent.SHIFT_MASK)
    };

    ActionListener list;

    public Menu(EventListener list){
        this.list = (ActionListener)list;

        add(createFileMenu());
        add(createDisegnaMenu());
        add(createVisualMenu());
        add(createOptionMenu());
    }

    public JMenu createFileMenu(){
        JMenu fileMenu = new JMenu("File (F)");

        for(int i = 0; i < fileMenus.length; i++){
            fileMenus[i].addActionListener(list);
            fileMenus[i].setIcon(new ImageIcon(getClass().getResource(fileIcons[i])));
            fileMenus[i].setAccelerator(fileStroke[i]);
        }

        fileMenu.add(fileMenus[0]);
        fileMenu.add(fileMenus[1]);
        fileMenu.addSeparator();
        fileMenu.add(fileMenus[2]);
        fileMenu.add(fileMenus[3]);
        fileMenu.addSeparator();
        fileMenu.add(fileMenus[4]);
        fileMenu.add(fileMenus[5]);

        return fileMenu;
    }

    public JMenu createDisegnaMenu(){
        JMenu disegnaMenu = new JMenu("Disegna (D)");

        for(int i = 0; i < disegnaMenus.length; i++){
            disegnaMenus[i].addActionListener(list);
            disegnaMenus[i].setIcon(new ImageIcon(getClass().getResource(disegnaIcons[i])));
            disegnaMenus[i].setAccelerator(disegnaStroke[i]);
        }

        disegnaMenu.add(disegnaMenus[0]);
        disegnaMenu.add(disegnaMenus[1]);
        disegnaMenu.addSeparator();
        disegnaMenu.add(disegnaMenus[2]);
        disegnaMenu.add(disegnaMenus[3]);
        disegnaMenu.add(disegnaMenus[4]);
        
        return disegnaMenu;
    }

    public JMenu createVisualMenu(){
        JMenu visualMenu = new JMenu("Visualizza (V)");

        for(int i = 0; i < visualMenus.length; i++){
            visualMenus[i].addActionListener(list);
            visualMenus[i].setIcon(new ImageIcon(getClass().getResource(visualIcons[i])));
            visualMenus[i].setAccelerator(visualStroke[i]);
        }

        visualMenu.add(visualMenus[0]);
        visualMenu.add(visualMenus[1]);
        visualMenu.add(visualMenus[2]);
        visualMenu.addSeparator();
        visualMenu.add(visualMenus[3]);
        visualMenu.add(visualMenus[4]);
        
        return visualMenu;
    }

    public JMenu createOptionMenu(){
        JMenu optionMenu = new JMenu("Option (O)");

        for(int i = 0; i < optionMenus.length; i++){
            optionMenus[i].addActionListener(list);
            optionMenus[i].setIcon(new ImageIcon(getClass().getResource(optionIcons[i])));
            optionMenus[i].setAccelerator(optionStroke[i]);
        }

        optionMenu.add(optionMenus[0]);
        optionMenu.add(optionMenus[1]);
        optionMenu.addSeparator();
        optionMenu.add(optionMenus[2]);
        optionMenu.add(optionMenus[3]);
        optionMenu.addSeparator();
        optionMenu.add(optionMenus[4]);
        optionMenu.add(optionMenus[5]);

        return optionMenu;
    }
}
