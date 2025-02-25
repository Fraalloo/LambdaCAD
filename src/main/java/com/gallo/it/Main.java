package com.gallo.it;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.gallo.it.components.App;

public class Main{
    public static void main(String args[]){     
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new App();
        });
    }
}