package vooga.shooter.level_editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.input.core.MouseController;
import util.xml.XmlUtilities;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import vooga.shooter.graphics.*;
import vooga.shooter.graphics.Canvas;


/**
 * LevelEditor.java
 * GUI for making and editing Levels for a 2D Shooter game.
 * 
 * @author Zachary Hopping
 * @author guytracy
 */
public class LevelEditor implements DrawableComponent, ActionListener {
    
    private static final Dimension DEFAULT_SIZE = new Dimension(600, 400);
    private static final String IMAGE_LOCATION = "/vooga/shooter/images/";

    private JFrame mainFrame; // The main window
    private JFileChooser levelChooser; // For saving and loading levels
    private JFileChooser imageChooser;  //For assigning images to Sprites
    private static Canvas myCanvas; // drawing canvas

    private File openFile; // currently open file
    private Level myLevel; // level object for editing

    /* Toolbar buttons, self-explanatory */
    JToolBar      myToolBar;
    private JButton newBtn;
    private JButton openBtn;
    private JButton saveBtn;
    private JButton clearBtn;
    private JButton backgroundBtn;
    private JButton makeSpriteBtn;
    
    /*Listeners*/
    private MouseListener myMouseListener;
    private MouseMotionListener myMouseMotionListener;
    private MouseListener myButtonListener;

    public static void main (String args[]) {
        myCanvas = new Canvas(new LevelEditor());
    }

    public LevelEditor () {
        // TODO initialize all variables and load a level to edit/make a new
        // level
        
        myLevel = null;
        
        mainFrame = new JFrame("Level Editor");
        mainFrame.setPreferredSize(DEFAULT_SIZE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(Color.WHITE);
        
        JPanel myToolPane = (JPanel)mainFrame.getContentPane();
        
        myToolPane.setLayout(new BorderLayout());
        setupToolbars();
        setupChoosers();
        
        /* Toolbar placement */
        myToolPane.add(myToolBar, BorderLayout.NORTH);

        mainFrame.pack();
        mainFrame.setVisible(true);

    }
    
    private void setupChoosers () {
        levelChooser = new JFileChooser(System.getProperties().getProperty("user.dir"));
        FileNameExtensionFilter XMLFilter = new FileNameExtensionFilter("XML Level files", "xml");
        levelChooser.setFileFilter(XMLFilter);
        imageChooser = new JFileChooser(System.getProperties().getProperty("user.dir"));
        FileNameExtensionFilter ImageFilter = new FileNameExtensionFilter("gif and png image files", "gif","png");
        imageChooser.setFileFilter(ImageFilter);
                                                             
    }

    private void setupToolbars () {
        myToolBar = new JToolBar();
        
        /* Map file buttons */
        saveBtn  = makeBtn("Save",    "/vooga/shooter/resources/save.gif",  "Save level");
        openBtn  = makeBtn("Open...", "/vooga/shooter/resources/open.gif",  "Open level...");
        newBtn   = makeBtn("New",     "/vooga/shooter/resources/new.gif",   "New level");
        clearBtn = makeBtn("Clear",   "/vooga/shooter/resources/clear.gif", "Reset level (Delete all sprites)");
        backgroundBtn = makeBtn("Set Background",   "/vooga/shooter/resources/background.gif", "Set Background");
        makeSpriteBtn = makeBtn("Make Sprite", "/vooga/shooter/resources/makeSprite.gif", "Make Sprite");
        myToolBar.add(saveBtn);
        myToolBar.add(openBtn);
        myToolBar.add(newBtn);
        myToolBar.add(clearBtn);
        myToolBar.add(backgroundBtn);
        myToolBar.add(makeSpriteBtn);
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
                ImageIcon icon = new ImageIcon(this.getClass().getResource("/vooga/shooter/images/" + file));
                results.add(makeScaledIcon(50,50,icon));
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
    
    /**
     * 
     * @param xSize the x dimension size of the new image
     * @param ySize the y dimension size of the new image
     * @param icon the icon to be scaled to the new size
     * @return the newly scaled ImageIcon
     */
    private ImageIcon makeScaledIcon(int xSize, int ySize, ImageIcon icon) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);
        return newIcon;
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
            int success = levelChooser.showOpenDialog(mainFrame);
            if (success == JFileChooser.APPROVE_OPTION) {
                openFile(levelChooser.getSelectedFile());
            }
        }
        
        else if (source == backgroundBtn) {
            
            //JFileChooser backchooser = new JFileChooser(System.getProperties().getProperty("user.dir"));
            int response = imageChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                myLevel.setBackgroundImage(imageChooser.getSelectedFile());
            }

        }
        
        else if (source == makeSpriteBtn) {
            int response = imageChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                
                
                //imageChooser.getSelectedFile());
            }
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
        newBtn.setBorder(new LineBorder(Color.gray, 1, false));
        return newBtn;
    }

    @Override
    public void update () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void paint (Graphics g) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setMouseListener (MouseMotionListener m) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setKeyboardListener (KeyListener k) {
        // TODO Auto-generated method stub
        
    }

    
}
