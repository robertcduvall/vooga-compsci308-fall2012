package vooga.shooter.level_editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.gui.MultiFieldJOptionPane;
import util.gui.NumericJTextField;
import util.input.core.MouseController;
import util.xml.XmlUtilities;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.Sprite;
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
    private static final String X_POSITION_KEY = "xpos";
    private static final String Y_POSITION_KEY = "ypos";
    private static final String WIDTH_KEY = "width";
    private static final String HEIGHT_KEY = "height";
    private static final String HEALTH_KEY = "health";

    private JFrame mainFrame; // The main window
    private JFileChooser levelChooser; // For saving and loading levels
    private JFileChooser imageChooser;  // For assigning images to Sprites
    private static Canvas myCanvas; // drawing canvas
    private Image myBackground;

    private MultiFieldJOptionPane<String> spriteOptionsPane;

    private File openFile; // currently open file
    private Level myLevel; // level object for editing

    /* Toolbar buttons, self-explanatory */
    JToolBar myToolBar;
    private JButton newBtn;
    private JButton openBtn;
    private JButton saveBtn;
    private JButton clearBtn;
    private JButton backgroundBtn;
    private JButton makeSpriteBtn;

    /* Listeners */
    private MouseListener myMouseListener;
    private MouseMotionListener myMouseMotionListener;
    private MouseListener myButtonListener;

    public static void main (String args[]) {

        new LevelEditor();

    }

    public LevelEditor () {

        myLevel = new Level();

        mainFrame = new JFrame("Level Editor");
        mainFrame.setPreferredSize(DEFAULT_SIZE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(Color.WHITE);

        myCanvas = new Canvas(this);
        myCanvas.start();

        mainFrame.getContentPane().add(myCanvas, BorderLayout.CENTER);
        setupToolbars();
        setupChoosers();
        initializeSpriteOptionsPane();

        /* Toolbar placement */
        mainFrame.getContentPane().add(myToolBar, BorderLayout.NORTH);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void initializeSpriteOptionsPane () {
        spriteOptionsPane = new MultiFieldJOptionPane<String>(mainFrame, "Sprite Options");
        spriteOptionsPane.addField(X_POSITION_KEY, "X Position:", new NumericJTextField(3));
        spriteOptionsPane.addField(Y_POSITION_KEY, "Y Position:", new NumericJTextField(3));
        spriteOptionsPane.addField(WIDTH_KEY, "Width", new NumericJTextField(3));
        spriteOptionsPane.addField(HEIGHT_KEY, "Height", new NumericJTextField(3));
        spriteOptionsPane.addField(HEALTH_KEY, "Health", new NumericJTextField(2));
    }

    private void setupChoosers () {

        levelChooser = new JFileChooser(System.getProperties().getProperty("user.dir"));
        FileNameExtensionFilter XMLFilter = new FileNameExtensionFilter("XML Level files", "xml");
        levelChooser.setFileFilter(XMLFilter);
        imageChooser = new JFileChooser(System.getProperties().getProperty("user.dir"));
        FileNameExtensionFilter ImageFilter =
                new FileNameExtensionFilter("gif and png image files", "gif", "png");
        imageChooser.setFileFilter(ImageFilter);

    }

    private void setupToolbars () {
        myToolBar = new JToolBar();

        /* Map file buttons */
        saveBtn = makeBtn("Save", "/vooga/shooter/resources/save.gif", "Save level");
        openBtn = makeBtn("Open...", "/vooga/shooter/resources/open.gif", "Open level...");
        newBtn = makeBtn("New", "/vooga/shooter/resources/new.gif", "New level");
        clearBtn =
                makeBtn("Clear", "/vooga/shooter/resources/clear.gif",
                        "Reset level (Delete all sprites)");
        backgroundBtn =
                makeBtn("Set Background", "/vooga/shooter/resources/background.gif",
                        "Set Background");
        makeSpriteBtn =
                makeBtn("Make Sprite", "/vooga/shooter/resources/makeSprite.gif", "Make Sprite");
        myToolBar.add(saveBtn);
        myToolBar.add(openBtn);
        myToolBar.add(newBtn);
        myToolBar.add(clearBtn);
        myToolBar.add(backgroundBtn);
        myToolBar.add(makeSpriteBtn);
    }

    /**
     * 
     * @param xSize the x dimension size of the new image
     * @param ySize the y dimension size of the new image
     * @param icon the icon to be scaled to the new size
     * @return the newly scaled ImageIcon
     */
    private ImageIcon makeScaledIcon (int xSize, int ySize, ImageIcon icon) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(xSize, ySize, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);
        return newIcon;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        Object source = e.getSource();

        if (source == newBtn) {
            newFile();
            openFile = null;
            myLevel = new Level(); // is this necessary????
        }
        else if (source == clearBtn) {
            myLevel = new Level();
            myBackground = null;
        }
        else if (source == saveBtn) {
            if (openFile == null) {
                // file has never ben saved, so we need to save as instead
                String file_path =
                        System.getProperty("user.dir") + "/src/vooga/shooter/levels/level1.xml";
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
            int response = imageChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                myLevel.setBackgroundImage(imageChooser.getSelectedFile());
                String backgroundPath = imageChooser.getSelectedFile().getPath();
                Image backgroundImage = (new ImageIcon(backgroundPath)).getImage();
                Image scaledImage =
                        backgroundImage.getScaledInstance(myCanvas.getWidth(),
                                                          myCanvas.getHeight(),
                                                          java.awt.Image.SCALE_SMOOTH);
                myBackground = scaledImage;
            }
        }

        else if (source == makeSpriteBtn) {
            makeSprite();
        }

    }

    /**
     * Prompts the user to sprite attributes and then adds an Enemy with the
     * given attributes to
     * the current Level.
     */
    private void makeSprite () {
        int response = imageChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {

            String imagePath = imageChooser.getSelectedFile().getPath();
            Image spriteImage = (new ImageIcon(imagePath)).getImage();
            
            spriteOptionsPane.display();
            
            Point position =
                    new Point(Integer.parseInt(spriteOptionsPane.getResult(X_POSITION_KEY)),
                              Integer.parseInt(spriteOptionsPane.getResult(Y_POSITION_KEY)));
            Dimension size = new Dimension(Integer.parseInt(spriteOptionsPane.getResult(WIDTH_KEY)),
                              Integer.parseInt(spriteOptionsPane.getResult(HEIGHT_KEY)));
            Dimension bounds = myCanvas.getSize();
            Point velocity = new Point(0,0);
            int health = Integer.parseInt(spriteOptionsPane.getResult(HEALTH_KEY));
            Enemy newEnemy = new Enemy(position, size, bounds, spriteImage, velocity, health);

            myLevel.addSprite(newEnemy);
        }
    }

    private void openFile (File file) {
        // TODO implement
        // Uses XML Utility
        // convert XML to Level object then display sprites
    }

    private void saveFile (File file) {
        // TODO implement
        // needs to use XML utility to convert current Level to File then save
        // that File
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
        g.drawImage(myBackground, 0, 0, null);
        myLevel.paintSprites(g, 0, 0);
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
