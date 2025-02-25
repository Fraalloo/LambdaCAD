package com.gallo.it.components;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.InputEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import com.gallo.it.entity.Entity;
import com.gallo.it.entity.OvaleEntity;
import com.gallo.it.entity.PuntoEntity;
import com.gallo.it.entity.RettangoloEntity;
import com.gallo.it.entity.SegmentoEntity;
import com.gallo.it.entity.SelectionEntity;
import com.gallo.it.entity.StringaEntity;
import com.gallo.it.shape.Ovale;
import com.gallo.it.shape.Punto;
import com.gallo.it.shape.Rettangolo;
import com.gallo.it.shape.Segmento;

public class App extends JFrame implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener, ChangeListener {
    private Header head = new Header(this);
    private Footer foot = new Footer(this);
    private Draw draw = new Draw(this);
    private Detail det = new Detail(this);
    private Menu menu = new Menu(this);
    private JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, det, draw);

    private static final String POINT = "Punto";
    private static final String SEG = "Segmento";
    private static final String RECT = "Rettangolo";
    private static final String OVAL = "Ovale";
    private static final String SEL = "Selection";
    private static final String PTR = "Pointer";
    private static final String STR = "Stringa";

    private Punto temp;
    private Color currentColor = Color.black;
    private String current = PTR;
    private ArrayList<String> currentNames = new ArrayList<>();
    private File file;
    private boolean fill = false;
    private int brush = 5;
    private ArrayList<Entity> remBuffer = new ArrayList<>();
    private ArrayList<Entity> undoBuffer = new ArrayList<>();

    private int translateX = 0;
    private int translateY = 0;
    private int prevX;
    private int prevY;

    private boolean headerOn = true;
    private boolean listOn = true;
    private boolean footerOn = true;
    
    public App(){
        super("LambdaCAD - Untitled Lambda Project");

        setIconImage(new ImageIcon(getClass().getResource("/images/logo/logo.png")).getImage());
        setupKeyBindings();
        setSize(700,700);
        setLayout(new BorderLayout());

        add(head, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
        add(foot, BorderLayout.SOUTH);
        setJMenuBar(menu);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        JFileChooser fileChooser = new JFileChooser();
        if(e.getActionCommand().equals("Esporta come PNG")) fileChooser.setFileFilter(new FileNameExtensionFilter("File immagine (*.png)", "png"));
        else fileChooser.setFileFilter(new FileNameExtensionFilter("File di disegno (*.lambda)", "lambda"));
        int result;
        
        switch(e.getActionCommand()){
            case "Apri Disegno":
                result = fileChooser.showOpenDialog(this);
                try{
                    if(result == JFileChooser.APPROVE_OPTION){
                        file = fileChooser.getSelectedFile();
        
                        if(!file.getName().toLowerCase().endsWith(".lambda")) JOptionPane.showMessageDialog(null, "Seleziona solo file .lambda!", "Errore", JOptionPane.ERROR_MESSAGE);
                        else draw.load(file);
                        setTitle("LambdaCAD - " + file.getName().substring(0, file.getName().length() - 7));
                        draw.repaint();
                        det.update(draw.getList());
                    }
                }catch(ClassNotFoundException|IOException ex){
                    ex.printStackTrace();
                }
                break;
            case "Salva Disegno con Nome":
                result = fileChooser.showSaveDialog(this);
                try{
                    if(result == JFileChooser.APPROVE_OPTION){
                        file = fileChooser.getSelectedFile();
        
                        if(!file.getName().toLowerCase().endsWith(".lambda")) file = new File(file.getAbsolutePath() + ".lambda");
                        draw.save(file);
                        setTitle("LambdaCAD - " + file.getName().substring(0, file.getName().length() - 7));
                    }
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                break;
            case "Salva Disegno":
                if(file == null) break;
                try{
                    draw.save(file);
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                break;
            case "Esporta come PNG":
                result = fileChooser.showSaveDialog(this);
                try{
                    if(result == JFileChooser.APPROVE_OPTION){
                        File selectedFile = fileChooser.getSelectedFile();
        
                        if(!selectedFile.getName().toLowerCase().endsWith(".png")) selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
                        draw.exportToPNG(selectedFile);
                    }
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                break;
            case "Nuovo Disegno":
                Counter.reset();
                draw.reset();
                draw.repaint();
                det.update(draw.getList());
                setTitle("LambdaCAD - Untitled Lambda Project");
                file = null;
                break;
            case "Stampa Disegno":
                PrinterJob printerJob = PrinterJob.getPrinterJob();
                PageFormat pageFormat = printerJob.defaultPage();
                pageFormat.setOrientation(PageFormat.LANDSCAPE);

                printerJob.setPrintable(new Printable(){
                    @Override
                    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
                        if(pageIndex > 0) return NO_SUCH_PAGE;
        
                        Graphics2D g2d = (Graphics2D)g.create();
                        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                        
                        double zoomFactor = draw.getZoom();
                        double translateX = draw.getOffsetX();
                        double translateY = draw.getOffsetY();
                        
                        double panelWidth = draw.getWidth();
                        double panelHeight = draw.getHeight();
                        
                        double contentWidth = panelWidth * zoomFactor;
                        double contentHeight = panelHeight * zoomFactor;
                        
                        double scaleX = pageFormat.getImageableWidth() / contentWidth;
                        double scaleY = pageFormat.getImageableHeight() / contentHeight;
                        double additionalScale = Math.min(scaleX, scaleY);
                        
                        double printedWidth = contentWidth * additionalScale;
                        double printedHeight = contentHeight * additionalScale;
                        
                        double offsetCenterX = (pageFormat.getImageableWidth() - printedWidth) / 2;
                        double offsetCenterY = (pageFormat.getImageableHeight() - printedHeight) / 2;
                        g2d.translate(offsetCenterX, offsetCenterY);
                        
                        g2d.translate(-translateX, -translateY);
                        g2d.scale(zoomFactor, zoomFactor);
                        g2d.scale(additionalScale, additionalScale);
                        
                        draw.printAll(g2d);
                        
                        g2d.dispose();
                        return PAGE_EXISTS;
                    }
                }, pageFormat);

                if(printerJob.printDialog()){
                    try{
                        printerJob.print();
                    }catch(PrinterException ex){
                        ex.printStackTrace();
                    }
                }
                break;
            case "Riempi":
                if(currentNames.isEmpty()) fill = !fill;
                else{
                    ArrayList<Entity> list = draw.getList();
                    for(Entity ent: list){
                        if(currentNames.contains(ent.getName())) ent.setFill(!ent.getFill());
                    }
                    resetSelection();
                }
                currentNames.clear();
                draw.repaint();
                break;
            case "Griglia":
                draw.toggleGrid();
                break;
            case "Colore avanzato":
                JColorChooser colorChooser = new JColorChooser(currentColor);
                colorChooser.setPreviewPanel(new JPanel());
                JDialog dialog = JColorChooser.createDialog(
                    null, 
                    "Seleziona un colore", 
                    true,   
                    colorChooser,
                    (ActionEvent ev) -> {
                        Color selectedColor = colorChooser.getColor();
                        if(selectedColor != null) currentColor = selectedColor;
                    },
                    null
                );
                dialog.setVisible(true);

                if(currentNames.size() != 0){
                    ArrayList<Entity> list = draw.getList();

                    for(Entity ent: list){
                        if(currentNames.contains(ent.getName())) ent.setColor(currentColor);
                    }
                    resetSelection();
                }
                break;
            case "Spessore avanzato":
                String input = JOptionPane.showInputDialog(
                    null, 
                    "Inserisci uno spessore compreso tra 1 e 20:", 
                    "Input Spessore", 
                    JOptionPane.QUESTION_MESSAGE
                );
                if(input == null) break;
                int numero;
                try{
                    numero = Integer.parseInt(input);
                    if(numero >= 1 && numero <= 20){
                        brush = numero;
                    }else{
                        JOptionPane.showMessageDialog(
                            null, 
                            "Lo spessore deve essere compreso tra 1 e 20. Riprova.", 
                            "Errore", 
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        null, 
                        "Inserisci un spessore valido.", 
                        "Errore", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                foot.setValue(brush);
                if(currentNames.size() != 0){
                    ArrayList<Entity> list = draw.getList();

                    for(Entity ent: list){
                        if(currentNames.contains(ent.getName())) ent.setBrush(brush);
                    }
                    resetSelection();
                }
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
            case "11": 
                currentColor = foot.getColors()[Integer.parseInt(e.getActionCommand())];
                if(currentNames.size() != 0){
                    ArrayList<Entity> list = draw.getList();

                    for(Entity ent: list){
                        if(currentNames.contains(ent.getName())) ent.setColor(currentColor);
                    }
                    resetSelection();
                }
                break;
            case "Pointer":
            case "Selection":
            case "Punto":
            case "Segmento":
            case "Rettangolo":
            case "Stringa":
            case "Ovale":
                current = e.getActionCommand();
                head.update(current);
                head.revalidate();
                break;
            case "GitHub":
                if(Desktop.isDesktopSupported()){
                    try{
                        Desktop.getDesktop().browse(new URI("https://github.com/Fraalloo/LambdaCAD"));
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }else JOptionPane.showMessageDialog(
                    null, 
                    "Impossibile aprire il Browser", 
                    "Errore", 
                    JOptionPane.ERROR_MESSAGE
                );
                break;
            case "Rinomina":
                File fileNuovo = new File(
                    file.getParentFile(),
                    JOptionPane.showInputDialog(
                        null, 
                        "Inserisci il nuovo nome:", 
                        "Input nome", 
                        JOptionPane.QUESTION_MESSAGE
                    )
                );
                if(!fileNuovo.getName().toLowerCase().endsWith(".lambda")) fileNuovo = new File(fileNuovo.getAbsolutePath() + ".lambda");

                try{
                    Files.move(file.toPath(), fileNuovo.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    setTitle("LambdaCAD - " + fileNuovo.getName().substring(0, fileNuovo.getName().length() - 7));
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(
                        null, 
                        "Impossibile rinominare il file", 
                        "Errore", 
                        JOptionPane.ERROR_MESSAGE
                    );
                    ex.printStackTrace();
                }
                break;
            case "Intestazione":
                if(headerOn) remove(head);
                else add(head, BorderLayout.NORTH);
                headerOn = !headerOn;
                repaint();
                revalidate();
                break;
            case "Lista oggetti":
                if(listOn){
                    remove(split);
                    add(draw, BorderLayout.CENTER);
                }else{
                    remove(draw);
                    split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, det, draw);
                    add(split, BorderLayout.CENTER);
                }
                listOn = !listOn;
                repaint();
                revalidate();
                break;
            case "Piè di pagina":
                if(footerOn) remove(foot);
                else add(foot, BorderLayout.SOUTH);
                footerOn = !footerOn;
                repaint();
                revalidate();
                break;
            case "Reset zoom":
                draw.resetZoom();
                draw.repaint();
                break;
            case "Reset posizione":
                draw.setOffset(0, 0);
                draw.repaint();
                break;
            case "Cancella":
                getRootPane().getActionMap().get("delete").actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "delete"));
                break;
            case "Informazioni":
                String descrizione = "LambdaCAD è un software di disegno e CAD che utilizza un’estensione personalizzata (.lambda) per salvare i progetti.\nPresenta un’interfaccia semplice con un’area di lavoro a griglia, strumenti di disegno e un menu ricco di funzionalità,\ncome la possibilità di creare nuovi disegni, salvare, esportare in PNG,\nmodificare il colore e lo spessore delle linee, e molto altro.";
                JOptionPane.showMessageDialog(this, descrizione, "Descrizione Software", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Seleziona Tutto":
                ArrayList<Entity> list = draw.getList();

                if(currentNames.size() != list.size()){
                    currentNames.clear();
                    for(Entity ent: list){
                        currentNames.add(ent.getName());
                        ent.setSelected(true);
                    }
                }else{
                    for(Entity ent: list){
                        ent.setSelected(false);
                    }
                    currentNames.clear();
                }

                draw.repaint();
                det.update(list, currentNames);
                break;
            case "Undo":
                ArrayList<Entity> entities = draw.getList();
                if(!remBuffer.isEmpty()){
                    for(Entity rem: remBuffer){
                        rem.setSelected(false);
                        entities.add(rem);
                    }
                }else if(!entities.isEmpty()) undoBuffer.add(entities.remove(entities.size() - 1));
                draw.repaint();
                det.update(entities);
                break;
            case "Redo":
                if(!undoBuffer.isEmpty()){
                    draw.getList().add(undoBuffer.remove(0));
                    draw.repaint();
                    det.update(draw.getList());
                }
                break;
            default:
                String name = e.getActionCommand();
                JButton btn = (JButton)(e.getSource());

                if(currentNames.contains(name)){
                    currentNames.remove(name);
                    btn.setBackground(new Color(0,0,0,0));
                }else{
                    btn.setBackground(Color.white);
                    currentNames.add(name);
                }

                ArrayList<Entity> ents = draw.getList();
                for(Entity ent: ents){
                    if(currentNames.contains(ent.getName())) ent.setSelected(true);
                    else ent.setSelected(false);
                }
                draw.repaint();
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        if(!current.equals(PTR)) return; // TODO: FIX

        prevX = e.getX();
        prevY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e){
        if(current == null) return;
        String name = current + " " + Counter.getCount();
        switch(e.getButton()){
            case MouseEvent.BUTTON1:
                if(!current.equals(SEL)) resetSelection();

                switch(current){
                    case POINT:
                        draw.addList(new PuntoEntity(
                            name,
                            new Punto((e.getX()  - draw.getOffsetX())/ draw.getZoom(), (e.getY()  - draw.getOffsetY())/ draw.getZoom()),
                            brush,
                            currentColor
                        ));
                        break;
                    case SEG:
                        if(temp == null) temp = new Punto((e.getX()  - draw.getOffsetX())/ draw.getZoom(), (e.getY()  - draw.getOffsetY())/ draw.getZoom());
                        else{
                            draw.addList(new SegmentoEntity(
                                name,
                                new Segmento(temp, new Punto((e.getX() - draw.getOffsetX()) / draw.getZoom(),(e.getY() - draw.getOffsetY()) / draw.getZoom())),
                                brush,
                                currentColor
                            ));
                            temp = null;
                            draw.setCurrentEntity(null);
                        }
                        break;
                    case RECT:
                        if(temp == null) temp = new Punto((e.getX()  - draw.getOffsetX())/ draw.getZoom(), (e.getY()  - draw.getOffsetY())/ draw.getZoom());
                        else{
                            draw.addList(new RettangoloEntity(
                                name,
                                new Rettangolo(
                                    new Punto(
                                        Math.min(temp.getX(), (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                                        Math.min(temp.getY(), (e.getY() - draw.getOffsetY()) / draw.getZoom())                            
                                    ),
                                    Math.abs(temp.getX() - (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                                    Math.abs(temp.getY() - (e.getY() - draw.getOffsetY()) / draw.getZoom())
                                ),
                                fill,
                                brush,
                                currentColor
                            ));
                            temp = null;
                            draw.setCurrentEntity(null);
                        }
                        break;
                    case OVAL:
                        if(temp == null) temp = new Punto((e.getX()  - draw.getOffsetX())/ draw.getZoom(), (e.getY()  - draw.getOffsetY())/ draw.getZoom());
                        else{
                            draw.addList(new OvaleEntity(
                                name,
                                new Ovale(
                                    new Punto(
                                        Math.min(temp.getX(), (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                                        Math.min(temp.getY(), (e.getY() - draw.getOffsetY()) / draw.getZoom())                            
                                    ),
                                    Math.abs(temp.getX() - (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                                    Math.abs(temp.getY() - (e.getY() - draw.getOffsetY()) / draw.getZoom())
                                ),
                                fill,
                                brush,
                                currentColor
                            ));
                            temp = null;
                            draw.setCurrentEntity(null);
                        }
                    break;
                    case PTR:
                        temp = null;
                        draw.setCurrentEntity(null);
                        break;
                    case SEL:
                        if(temp == null) temp = new Punto((e.getX()  - draw.getOffsetX())/ draw.getZoom(), (e.getY()  - draw.getOffsetY())/ draw.getZoom());
                        else{
                            ArrayList<Entity> list = draw.getList();
                            
                            for(Entity ent: list){
                                if(ent.inside(
                                    (int)Math.min(temp.getX(), (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                                    (int)Math.min(temp.getY(), (e.getY() - draw.getOffsetY()) / draw.getZoom()),
                                    (int)Math.abs(temp.getX() - (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                                    (int)Math.abs(temp.getY() - (e.getY() - draw.getOffsetY()) / draw.getZoom())
                                )){
                                    ent.setSelected(true);
                                    currentNames.add(ent.getName());
                                }
                            }
                            draw.setCurrentEntity(null);
                            current = PTR;
                            head.update(current);
                            det.update(list, currentNames);
                            head.repaint();
                            head.revalidate();
                            temp = null;
                        }
                        break;
                    case STR:
                        if(temp == null) temp = new Punto((e.getX()  - draw.getOffsetX())/ draw.getZoom(), (e.getY()  - draw.getOffsetY())/ draw.getZoom());
                        String text = JOptionPane.showInputDialog(
                            null, 
                            "Inserisci il testo:", 
                            "Input testo", 
                            JOptionPane.QUESTION_MESSAGE
                        );
                        if(text == null) break;
                        String sizeStr = JOptionPane.showInputDialog(
                            null, 
                            "Inserisci la dimensione del testo:", 
                            "Input dimensione del testo", 
                            JOptionPane.QUESTION_MESSAGE
                        );
                        if(sizeStr == null) break;
                        int size = 0;
                        try{
                            size = Integer.parseInt(sizeStr);
                            if(size <= 0){
                                JOptionPane.showMessageDialog(
                                    null, 
                                    "La dimensione deve essere maggiore di 0. Riprova.", 
                                    "Errore", 
                                    JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }catch(NumberFormatException ex) {
                            JOptionPane.showMessageDialog(
                                null, 
                                "Inserisci una dimensione valida.", 
                                "Errore", 
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                        
                        draw.addList(new StringaEntity(name, text, temp, size, currentColor));

                        break;
                }
                                
                break;
            case MouseEvent.BUTTON3:
                temp = null;
                draw.setCurrentEntity(null);
                Counter.decrease();
                break;
        }
        
        repaint();
        if(temp == null && !current.equals(PTR)){
            det.update(draw.getList());
            remBuffer.clear();
        }else Counter.decrease();
    }
    @Override
    public void mouseClicked(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseMoved(MouseEvent e){
        if(temp == null) return;
        
        switch(current){
             case SEG:
                draw.setCurrentEntity(new SegmentoEntity(
                    "",
                    new Segmento(temp, new Punto((e.getX() - draw.getOffsetX()) / draw.getZoom(),(e.getY() - draw.getOffsetY()) / draw.getZoom())),
                    brush,
                    currentColor
                ));
                break;
            case RECT:
                draw.setCurrentEntity(new RettangoloEntity(
                    "",
                    new Rettangolo(
                        new Punto(
                            Math.min(temp.getX(), (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                            Math.min(temp.getY(), (e.getY() - draw.getOffsetY()) / draw.getZoom())                            
                        ),
                        Math.abs(temp.getX() - (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                        Math.abs(temp.getY() - (e.getY() - draw.getOffsetY()) / draw.getZoom())
                    ),
                    fill,
                    brush,
                    currentColor
                ));
                break;
            case OVAL:
                draw.setCurrentEntity(new OvaleEntity(
                    "",
                    new Ovale(
                        new Punto(
                            Math.min(temp.getX(), (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                            Math.min(temp.getY(), (e.getY() - draw.getOffsetY()) / draw.getZoom())                            
                        ),
                        Math.abs(temp.getX() - (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                        Math.abs(temp.getY() - (e.getY() - draw.getOffsetY()) / draw.getZoom())
                    ),
                    fill,
                    brush,
                    currentColor
                ));
                break;
            case SEL:
                draw.setCurrentEntity(new SelectionEntity(
                    new Rettangolo(
                        new Punto(
                            Math.min(temp.getX(), (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                            Math.min(temp.getY(), (e.getY() - draw.getOffsetY()) / draw.getZoom())                            
                        ),
                        Math.abs(temp.getX() - (e.getX() - draw.getOffsetX()) / draw.getZoom()),
                        Math.abs(temp.getY() - (e.getY() - draw.getOffsetY()) / draw.getZoom())
                    ),
                    false,
                    brush,
                    currentColor
                ));
                break;
        }
        
        repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e){
        if(!current.equals(PTR)) return;

        int dx = e.getX() - prevX;
        int dy = e.getY() - prevY;

        translateX += dx;
        translateY += dy;

        prevX = e.getX();
        prevY = e.getY();

        if(currentNames.isEmpty()) draw.setOffset(translateX, translateY);
        else{
            ArrayList<Entity> list = draw.getList();
            for(Entity ent: list){
                if(currentNames.contains(ent.getName())) ent.translate(dx, dy);
            }
        }
        draw.repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        if(e.getPreciseWheelRotation() < 0) draw.zoomin();
        else draw.zoomout();
    }

    @Override
    public void stateChanged(ChangeEvent e){
        brush = ((JSlider)e.getSource()).getValue();
        if(currentNames.size() != 0){
            ArrayList<Entity> list = draw.getList();

            for(Entity ent: list){
                if(currentNames.contains(ent.getName())) ent.setBrush(brush);
            }
            resetSelection();
        }
    }

    private void setupKeyBindings(){
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.META_MASK), "delete");
        actionMap.put("delete", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(currentNames.isEmpty()) return;

                ArrayList<Entity> list = draw.getList();
                int len = list.size();
                for(int i = 0; i < len; i++){
                    if(currentNames.contains(list.get(i).getName())){
                        remBuffer.add(list.remove(i));
                        currentNames.remove(0);
                        if(currentNames.isEmpty()) break;
                        i--;
                    }
                }

                currentNames.clear();
                det.update(list);
                draw.repaint();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.META_MASK), "zoom_in");
        actionMap.put("zoom_in", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                draw.zoomin();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.META_MASK), "zoom_out");
        actionMap.put("zoom_out", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                draw.zoomout();
            }
        });
    }

    public void resetSelection(){
        ArrayList<Entity> list = draw.getList();
        for(Entity ent: list){
            ent.setSelected(false);
        }
        currentNames.clear();
        det.update(list);
        repaint();
    }
}