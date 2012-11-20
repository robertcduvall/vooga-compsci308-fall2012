package vooga.shooter.level_editor;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import util.xml.XmlUtilities;
import util.xml.XmlWriter;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * LevelEditor.java
 * GUI for making and editing Levels for a 2D Shooter game.
 * 
 * @author Zachary Hopping
 * @author guytracy
 */
public class LevelEditor implements ActionListener, KeyListener {

    private static final Dimension FRAME_SIZE = new Dimension(1000, 800);
    private static final String IMAGE_LOCATION = "/vooga/shooter/images/";
    boolean compactToolbars = false;
    boolean borderedButtons = false;

    private JFrame mainFrame; // The main window
    private JFileChooser chooser; // For saving and loading levels
    private JSplitPane split;  // provides the movable divider next to Sprite
                              // chooser
    private JPanel chooserPanel; // panel for the Sprite chooser
    private JPanel leftPanel;
    private JPanel rightPanel;
    private Canvas myCanvas; // drawing canvas

    private File openFile; // currently open file
    private Level myLevel; // level object for editing

    /* Toolbar buttons, self-explanatory */
    JToolBar      myToolBar;
    private JButton newBtn;
    private JButton openBtn;
    private JButton saveBtn;
    private JButton clearBtn;
    private JButton backgroundBtn;

    /* Menu bar */
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu toolMenu;
    private JMenu helpMenu;
    private JMenuItem undoMI;
    private JMenuItem redoMI;
    private JMenuItem openMI;
    private JMenuItem newMI;
    private JMenuItem saveMI;
    private JMenuItem saveAsMI;
    private JMenuItem exitMI;
    private JMenuItem about;
    private JMenuItem howToUse;

    public static void main (String args[]) {
        new LevelEditor();
    }

    public LevelEditor () {
        // TODO initialize all variables and load a level to edit/make a new
        // level
        
        myLevel = new Level();
        
        mainFrame = new JFrame("Level Editor");
        mainFrame.setPreferredSize(FRAME_SIZE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(Color.WHITE);

        makeMenu();

        split = new JSplitPane();
        split.setDividerLocation(200);

        // myCanvas = new Canvas();
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new TitledBorder("Left Panel"));

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new TitledBorder("Right Panel"));
        
        setupImagePanel();

        split.setLeftComponent(leftPanel);
        split.setRightComponent(rightPanel);
        
        JPanel myToolPane = (JPanel)mainFrame.getContentPane();
        
        myToolPane.setLayout(new BorderLayout());

        setupMenus();
        setupToolbars();
        
        /* Toolbar placement */
        myToolPane.add(myToolBar, BorderLayout.NORTH);

        mainFrame.add(split, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    private void setupMenus () {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        toolMenu = new JMenu("Tools");
        editMenu = new JMenu("Edit");
        helpMenu = new JMenu("Help");
        openMI = new JMenuItem("Open...");
        newMI = new JMenuItem("New");
        saveMI = new JMenuItem("Save");
        saveAsMI = new JMenuItem("Save As...");
        exitMI = new JMenuItem("Exit");
        undoMI = new JMenuItem("Undo");
        redoMI = new JMenuItem("Redo");
        about = new JMenuItem("About");
        howToUse = new JMenuItem("How to use LevelEditor");

        openMI.addActionListener(this);
        newMI.addActionListener(this);
        saveMI.addActionListener(this);
        saveAsMI.addActionListener(this);
        exitMI.addActionListener(this);
        undoMI.addActionListener(this);
        redoMI.addActionListener(this);
        about.addActionListener(this);
        howToUse.addActionListener(this);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        fileMenu.add(openMI);
        fileMenu.add(newMI);
        fileMenu.add(saveMI);
        fileMenu.add(saveAsMI);
        fileMenu.add(exitMI);
        editMenu.add(undoMI);
        editMenu.add(redoMI);
        helpMenu.add(about);
        helpMenu.add(howToUse);
        mainFrame.setJMenuBar(menuBar);
    }

    private void setupToolbars () {
        myToolBar = new JToolBar();
        
        /* Map file buttons */
        saveBtn  = makeBtn("Save",    "/vooga/shooter/resources/save.gif",  "Save level");
        openBtn  = makeBtn("Open...", "/vooga/shooter/resources/open.gif",  "Open level...");
        newBtn   = makeBtn("New",     "/vooga/shooter/resources/new.gif",   "New level");
        clearBtn = makeBtn("Clear",   "/vooga/shooter/resources/clear.gif", "Reset level (Delete all sprites)");
        clearBtn = makeBtn("Clear",   "/vooga/shooter/resources/clear.gif", "Reset level (Delete all sprites)");
        backgroundBtn = makeBtn("Set",   "/vooga/shooter/resources/redo.gif", "Set Background");
        
        myToolBar.add(saveBtn);
        myToolBar.add(openBtn);
        myToolBar.add(newBtn);
        myToolBar.add(clearBtn);
        myToolBar.add(backgroundBtn);
    }
    
    private void setupImagePanel() {
        GridLayout experimentLayout = new GridLayout(0,2);
        leftPanel.setLayout(experimentLayout);   
        List<ImageIcon> results = loadImages(IMAGE_LOCATION);
        
        for (ImageIcon b: results) {
            JButton newButton = new JButton();
            newButton.setIcon(b);
            leftPanel.add(newButton);
        }
    }
    
    private List<ImageIcon> loadImages (String directory)
    {
        try
        {
            URL path = getClass().getResource(directory);
            List<ImageIcon> results = new ArrayList<ImageIcon>();

            for (String file : new File(path.toURI()).list())
            {
                //System.out.println(path + file);
                results.add(new ImageIcon(this.getClass().getResource("/vooga/shooter/images/" + file)));
            }
            return results;

        }
        catch (Exception e)
        {
            // should not happen
            System.out.println("this should not happen");
            return new ArrayList<ImageIcon>(); 
        }
    }

    @Override
    public void keyPressed (KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased (KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped (KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed (ActionEvent e) {
        Object source = e.getSource();

        if (source == newBtn) {
            newFile();
            openFile = null;
        }
        else if (source == clearBtn) {
            // clear current wave
        }
        else if (source == saveBtn) {
            if (openFile == null) {
                // file has never ben saved, so we need to save as instead
                String file_path = System.getProperty("user.dir") + "/src/vooga/shooter/levels/level1.xml";
                XmlUtilities.write(myLevel.pack(), file_path);
            }
            else {
                saveFile(openFile);
            }
        }
        else if (source == openBtn) {
            int success = chooser.showOpenDialog(mainFrame);
            if (success == JFileChooser.APPROVE_OPTION) {
                openFile(chooser.getSelectedFile());
            }
        }
        
        else if (source == backgroundBtn) {
            //int success = chooser.showOpenDialog(mainFrame);
            //if (success == JFileChooser.APPROVE_OPTION) {
            //    openFile(chooser.getSelectedFile());
            //}
            //myLevel.setBackgroundImage( IMAGE_LOCATION + "alienship.gif");
            JFileChooser backchooser = new JFileChooser(System.getProperties().getProperty("user.dir"));
            int response = backchooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                myLevel.setBackgroundImage(backchooser.getSelectedFile());
            }
            //ImageIcon backGroundIcon = new ImageIcon(this.getClass().getResource("/vooga/shooter/images/" + "alienship.gif"));
            //JButton newButton = new JButton();
            //newButton.setIcon(backGroundIcon);
            //rightPanel.add(newButton);
            //newBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
           // myCanvas.paint()
        }

    }

    private void openFile (File file) {
        // TODO implement
        // Uses XML Utility
        // convert XML to Level object then display sprites
    }

    private void saveFile (File file) {
        // TODO implement
        //needs to use XML utility to convert current Level to File then save that File
    }

    public void newFile () {
        // TODO implement
        // Use XML Utility
    }

    /**
     * Makes a JButton with the given icon and tooltop.
     * If the icon cannot be loaded, then the text will be used instead.
     * 
     * Adds this LevelEditor as an actionListener.
     * 
     * @return the new JButton
     **/
    private JButton makeBtn (String text, String path, String tooltip) {
        JButton newBtn;
        try {
            newBtn = new JButton(new ImageIcon(getClass().getResource(path)));
        }
        catch (Exception e) {
            newBtn = new JButton(text);
        }
        newBtn.setToolTipText(tooltip);
        newBtn.addActionListener(this);
        if (borderedButtons) {
            newBtn.setBorder(new LineBorder(Color.gray, 1, false));
        }
        else if (compactToolbars) {
            newBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        }
        // newBtn.setBorderPainted(false);
        return newBtn;
    }

    private JMenuBar makeMenu () {
        JMenuBar bar = new JMenuBar();
        bar.add(makeFileMenu());
        mainFrame.setJMenuBar(bar);
        return bar;
    }

    @SuppressWarnings("serial")
    private JMenu makeFileMenu () {
        JMenu result = new JMenu("File");
        result.add(new AbstractAction("New") { // this needs to come from
                                               // language resources folder
            @Override
            public void actionPerformed (ActionEvent e) {
                // go to start page
            }
        });
        result.add(new JSeparator());
        result.add(new AbstractAction("Quit") {
            @Override
            public void actionPerformed (ActionEvent e) {
                // end program
                System.exit(0);
            }
        });
        return result;
    }

}
