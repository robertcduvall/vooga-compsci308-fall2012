package vooga.shooter.level_editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.w3c.dom.Document;
import util.gui.MultiFieldJOptionPane;
import util.gui.NumericJTextField;
import util.input.core.KeyboardController;
import util.input.core.MouseController;
import util.reflection.Reflection;
import util.xml.XmlUtilities;
import java.io.*;
import java.util.Vector;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.intelligence.*;
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
    private static final String SAVE_AS_KEY = "save as";
    private static final String AI_KEY = "AI Type";
    /*The AI types are hard coded at this point because I was unable to find a way to
     * dynamically load the class names from a file path or otherwise.
     */
    private static final String[] AIOptions = {"Bounce", "Chase", "Random"};

    private JFrame mainFrame; // The main window
    private JFileChooser levelChooser; // For loading levels
    private JFileChooser imageChooser;  // For assigning images to Sprites
    private static Canvas myCanvas; // drawing canvas
    private Image myBackground;

    private MultiFieldJOptionPane<String> spriteOptionsPane;
    private Sprite myCurrentSprite;

    private File myOpenFile; // currently open file
    private Level myLevel; // level object for editing
    private Player myPlayer; // required player Sprite
    
    private static final Dimension DEFAULT_PLAYER_SIZE = new Dimension(20, 20);
    private static final int DEFAULT_PLAYER_HEALTH = 10;
    private static final String DEFAULT_PLAYER_IMAGEPATH = "vooga/shooter/images/spaceship.gif";
    private static final int DEFAULT_PLAYER_START_HEIGHT = 75;
    private static final Point DEFAULT_PLAYER_VELOCITY = new Point(0,0);

    /* Toolbar buttons, self-explanatory */
    private JToolBar myToolBar;
    private JButton newBtn;
    private JButton openBtn;
    private JButton saveBtn;
    private JButton clearBtn;
    private JButton backgroundBtn;
    private JButton makeSpriteBtn;
    private JButton deleteSpriteBtn;
    private JButton showInfoBtn;
    private JButton helpBtn;

    /* Listeners */
    private LevelEditorMouseListener myMouseListener;

    public static void main (String args[]) {
        new LevelEditor();
    }
    
    public LevelEditor () {

        mainFrame = new JFrame("Level Editor");
        mainFrame.setPreferredSize(DEFAULT_SIZE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(Color.WHITE);

        myCanvas = new Canvas(this);
        myCanvas.start();
        
        myLevel = new Level();
        initializePlayer();

        mainFrame.getContentPane().add(myCanvas, BorderLayout.CENTER);
        setupToolbars();
        setupChoosers();
        makeEnemyOptionsPane();
        myMouseListener = new LevelEditorMouseListener();
        myCanvas.addMouseListener(myMouseListener);
        
        /* Toolbar placement */
        mainFrame.getContentPane().add(myToolBar, BorderLayout.NORTH);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    /**
     * Initializes the required player sprite for the level.
     */
    private void initializePlayer() {
        myPlayer =
                new Player(new Point(myCanvas.getWidth()/2, myCanvas.getHeight() - DEFAULT_PLAYER_START_HEIGHT), DEFAULT_PLAYER_SIZE,
                           new Dimension(myCanvas.getWidth(), myCanvas.getHeight()),
                           DEFAULT_PLAYER_IMAGEPATH, DEFAULT_PLAYER_VELOCITY, DEFAULT_PLAYER_HEALTH);
        myLevel.addSprite(myPlayer);
        myLevel.setPlayer(myPlayer);
        myCurrentSprite = myPlayer;
    }
    
    /**
     * Makes an empty options pane for entering Enemy attributes.
     */
    private void makeEnemyOptionsPane () {
        spriteOptionsPane = new MultiFieldJOptionPane<String>(mainFrame, "Sprite Options");
        spriteOptionsPane.addField(X_POSITION_KEY, "X Position:", new NumericJTextField(3));
        spriteOptionsPane.addField(Y_POSITION_KEY, "Y Position:", new NumericJTextField(3));
        spriteOptionsPane.addField(WIDTH_KEY, "Width", new NumericJTextField(3));
        spriteOptionsPane.addField(HEIGHT_KEY, "Height", new NumericJTextField(3));
        spriteOptionsPane.addField(HEALTH_KEY, "Health", new NumericJTextField(2));
        spriteOptionsPane.addField(AI_KEY, "AI", new JComboBox(AIOptions));
    }
    
    /**
     * Makes the Sprite Options Pane with location information from a Point
     * @param p point from which we extract location information
     */
    private void makeEnemyOptionsPane (Point p) {
        spriteOptionsPane = new MultiFieldJOptionPane<String>(mainFrame, "Sprite Options");
        spriteOptionsPane.addField(X_POSITION_KEY, "X Position:", new NumericJTextField(Integer.toString(p.x), 3));
        spriteOptionsPane.addField(Y_POSITION_KEY, "Y Position:", new NumericJTextField(Integer.toString(p.y), 3));
        spriteOptionsPane.addField(WIDTH_KEY, "Width", new NumericJTextField(3));
        spriteOptionsPane.addField(HEIGHT_KEY, "Height", new NumericJTextField(3));
        spriteOptionsPane.addField(HEALTH_KEY, "Health", new NumericJTextField(2));
        spriteOptionsPane.addField(AI_KEY, "AI", new JComboBox(AIOptions));
    }
    
    /**
     * Makes values in the Sprite Options Pane based on a sprite's attributes.
     * @param s the selected sprite who's attributes are used to fill the pane
     */
    private void makeEnemyOptionsPane (Sprite s) {
        spriteOptionsPane = new MultiFieldJOptionPane<String>(mainFrame, "Sprite Options");
        spriteOptionsPane.addField(X_POSITION_KEY, "X Position:", new NumericJTextField(Integer.toString(s.getLeft()), 3));
        spriteOptionsPane.addField(Y_POSITION_KEY, "Y Position:", new NumericJTextField(Integer.toString(s.getTop()), 3));
        spriteOptionsPane.addField(WIDTH_KEY, "Width", new NumericJTextField(Integer.toString(s.getSize().width), 3));
        spriteOptionsPane.addField(HEIGHT_KEY, "Height", new NumericJTextField(Integer.toString(s.getSize().height), 3));
        spriteOptionsPane.addField(HEALTH_KEY, "Health", new NumericJTextField(Integer.toString(s.getCurrentHealth()), 2));
        spriteOptionsPane.addField(AI_KEY, "AI", new JComboBox(AIOptions));
    }
    
    /**
     * Makes a Player options pane based on the current player's attributes.
     * @param s the current player sprite
     */
    private void makePlayerOptionsPane(Sprite s) {
        spriteOptionsPane = new MultiFieldJOptionPane<String>(mainFrame, "Player Options");
        spriteOptionsPane.addField(X_POSITION_KEY, "X Position:", new NumericJTextField(Integer.toString(s.getLeft()), 3));
        spriteOptionsPane.addField(Y_POSITION_KEY, "Y Position:", new NumericJTextField(Integer.toString(s.getTop()), 3));
        spriteOptionsPane.addField(WIDTH_KEY, "Width", new NumericJTextField(Integer.toString(s.getSize().width), 3));
        spriteOptionsPane.addField(HEIGHT_KEY, "Height", new NumericJTextField(Integer.toString(s.getSize().height), 3));
        spriteOptionsPane.addField(HEALTH_KEY, "Health", new NumericJTextField(Integer.toString(s.getCurrentHealth()), 2));
    }

    private void setupChoosers () {
        levelChooser = new JFileChooser(System.getProperties().getProperty(
                "user.dir"));
        FileNameExtensionFilter XMLFilter = new FileNameExtensionFilter(
                "XML Level files", "xml");
        levelChooser.setFileFilter(XMLFilter);
        imageChooser = new JFileChooser(System.getProperties().getProperty(
                "user.dir"));
        FileNameExtensionFilter ImageFilter = new FileNameExtensionFilter(
                "gif and png image files", "gif", "png");
        imageChooser.setFileFilter(ImageFilter);
    }

    private void setupToolbars () {
        myToolBar = new JToolBar();

        /* Map file buttons */
        saveBtn = makeBtn("Save", "/vooga/shooter/resources/save.gif",
                "Save level");
        openBtn = makeBtn("Open...", "/vooga/shooter/resources/open.gif",
                "Open level...");
        newBtn = makeBtn("New", "/vooga/shooter/resources/new.gif", "New level");
        clearBtn =
                makeBtn("Clear", "/vooga/shooter/resources/clear.gif",
                        "Reset level (Delete all sprites)");
        backgroundBtn =
                makeBtn("Set Background", "/vooga/shooter/resources/background.gif",
                        "Set Background");
        makeSpriteBtn =
                makeBtn("New Sprite", "/vooga/shooter/resources/makeSprite.gif", "Make Sprite");
        deleteSpriteBtn = makeBtn("Delete Sprite", "/vooga/shooter/resources/deleteSprite.gif", "Delete Sprite");
        showInfoBtn = makeBtn("Info", "/vooga/shooter/resources/showInfo.gif", "Show Current Level Info");
        helpBtn = makeBtn("Editor Help", "/vooga/shooter/resources/help.gif", "Editor Help");
        myToolBar.add(saveBtn);
        myToolBar.add(openBtn);
        myToolBar.add(newBtn);
        myToolBar.add(clearBtn);
        myToolBar.add(backgroundBtn);
        myToolBar.add(makeSpriteBtn);
        myToolBar.add(deleteSpriteBtn);
        myToolBar.add(showInfoBtn);
        myToolBar.add(helpBtn);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        Object source = e.getSource();

        if (source == newBtn) {
            newFile();
            myOpenFile = null;
            myLevel = new Level();
            myBackground = null;
            initializePlayer();
        }
        else if (source == clearBtn) {
            myLevel = new Level();
            myBackground = null;
            initializePlayer();
        }
        else if (source == saveBtn) {
            if (myOpenFile == null) {
                // file has never been saved, so we need to save as instead
                MultiFieldJOptionPane<String> saveAsOptionsPane = new MultiFieldJOptionPane<String>(
                        mainFrame, "Save Xml File as");
                saveAsOptionsPane.addField(SAVE_AS_KEY, "Save as:",
                        new JTextField(10));
                saveAsOptionsPane.display();
                String fileNameString = saveAsOptionsPane
                       .getResult(SAVE_AS_KEY);
                String filePath = System.getProperty("user.dir")
                        + "/src/vooga/shooter/levels/" + fileNameString
                        + ".xml";
                myOpenFile = new File(filePath);
                saveFile(myOpenFile);
                System.out.println("save as");
            }
            else {
                //standard save
                saveFile(myOpenFile);
                System.out.println("save");
            }
        }
        else if (source == openBtn) {
            int success = levelChooser.showOpenDialog(mainFrame);
            if (success == JFileChooser.APPROVE_OPTION) {
                myOpenFile = levelChooser.getSelectedFile();
                openFile(levelChooser.getSelectedFile());
            }
        }

        else if (source == backgroundBtn) {
            int response = imageChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                myLevel.setBackgroundImage(imageChooser.getSelectedFile());
                String backgroundPath = imageChooser.getSelectedFile()
                        .getPath();
                Image backgroundImage = (new ImageIcon(backgroundPath))
                        .getImage();
                Image scaledImage = backgroundImage.getScaledInstance(
                        myCanvas.getWidth(), myCanvas.getHeight(),
                        java.awt.Image.SCALE_SMOOTH);
                myBackground = scaledImage;
            }
        }

        else if (source == makeSpriteBtn) {
            Enemy newEnemy = makeEnemy();
            myLevel.addSprite(newEnemy);
            myCurrentSprite = newEnemy;
        }
        
        else if (source == deleteSpriteBtn) {
            if(myCurrentSprite instanceof Enemy) {
                myLevel.removeSprite(myCurrentSprite);
                myCurrentSprite = null;
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Error: A level requires a Player! \n" +
                		"Player sprite may be edited but not deleted.", "DELETE ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        else if (source == showInfoBtn) {
            showLevelInfo();
        }
        
        else if (source == helpBtn) {
               JOptionPane.showMessageDialog(null, "Left click an existing sprite to select it."   + "\n" +
                                                      "A selected sprite can be deleted by clicking the delete button." + "\n" +
                                       "Right click to create a sprite at the pointer's location." + "\n" +
                                         "Click save to save your level to XML.");
        }
        
        else {
            throw new LevelEditorException("Invalid action source", source);
        }
    }
    
    /**
     * Displays information about all of the Sprites in the current Level, in table form.
     */
    private void showLevelInfo() {
        //count Sprites(need to count with a for loop because getSpriteList returns Iterable<Sprite>)
        int count = 1;
        for(Sprite s: myLevel.getSpriteList()) {
            count++;
        }
        //initialize table and attribute titles
        JFrame frame = new JFrame("Current Sprite Information");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JTable table = new JTable(count,6);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
       
        table.setValueAt("Index",0,0);
        table.setValueAt("X Position",0,1);
        table.setValueAt("Y Position", 0, 2);
        table.setValueAt("Width",0,3);
        table.setValueAt("Height", 0, 4);
        table.setValueAt("Health",0,5);
        //fill in Sprite attributes
        int index = 1;
        for(Sprite s: myLevel.getSpriteList()) {
            table.setValueAt(index,index, 0);
            table.setValueAt(s.getLeft(), index, 1);
            table.setValueAt(s.getTop(), index, 2);
            table.setValueAt(s.getSize().width, index, 3);
            table.setValueAt(s.getSize().height, index, 4);
            table.setValueAt(s.getCurrentHealth(), index, 5);
            index++;
        }
        JScrollPane pane = new JScrollPane(table);
        panel.add(pane, BorderLayout.CENTER);
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Prompts the user to enter new attributes for the selected sprite.
     * If changes are entered, the selected sprite is replaced with a new Sprite
     * with the newly entered attributes.
     */
    private void editCurrentSprite() {
        Enemy newEnemy = makeEnemy();
        myLevel.removeSprite(myCurrentSprite);
        myLevel.addSprite(newEnemy);
        myCurrentSprite = newEnemy;
    }
    
    /**
     * Prompts the user to enter new attributes for the player.
     * Replaces player with new player generated from entered attributes.
     */
    private void editPlayer() {
        myPlayer = makePlayer();
        myLevel.removeSprite(myCurrentSprite);
        myLevel.setPlayer(myPlayer);
        myLevel.addSprite(myPlayer);
        myCurrentSprite = myPlayer;
    }
    
    /**
     * Shows image chooser and Player options pane so user can enter new attributes for Player.
     * Returns a new player object with the given attributes.
     * @return the new player object
     */
    private Player makePlayer() {
        int response = imageChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {

            // The expected imagePath format is a relative path
            // starting at the src directory. We will change the
            // full path to match that format.
            String imagePath = imageChooser.getSelectedFile().getPath();
            
            // A simple check to see if it's a valid path (not foolproof).
            if (!imagePath.contains("src/")) {
                throw new LevelEditorException("The image file for sprites must be in the src directory!");
            }
            imagePath = imagePath.split("src/")[1];
            
            spriteOptionsPane.display();
            
            Point position =
                    new Point(Integer.parseInt(spriteOptionsPane.getResult(X_POSITION_KEY)),
                              Integer.parseInt(spriteOptionsPane.getResult(Y_POSITION_KEY)));
            Dimension size = new Dimension(Integer.parseInt(spriteOptionsPane.getResult(WIDTH_KEY)),
                              Integer.parseInt(spriteOptionsPane.getResult(HEIGHT_KEY)));
            Dimension bounds = myCanvas.getSize();
            Point velocity = new Point(0,0);
            int health = Integer.parseInt(spriteOptionsPane.getResult(HEALTH_KEY));
            Player newPlayer = new Player(position, size, bounds, imagePath, velocity, health);
            return newPlayer;
        }
        throw new LevelEditorException("Invalid attributes selected.");
    }

    /**
     * Prompts the user to input sprite attributes and then returns a Sprite object with the given
     * attributes.
     */
    private Enemy makeEnemy () {
        int response = imageChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {

            // The expected imagePath format is a relative path
            // starting at the src directory. We will change the
            // full path to match that format.
            String imagePath = imageChooser.getSelectedFile().getPath();
            
            // A simple check to see if it's a valid path (not foolproof).
            if (!imagePath.contains("src/")) {
                throw new LevelEditorException("The image file for sprites must be in the src directory!");
            }
            imagePath = imagePath.split("src/")[1];
            
            spriteOptionsPane.display();
            
            Point position =
                    new Point(Integer.parseInt(spriteOptionsPane.getResult(X_POSITION_KEY)),
                              Integer.parseInt(spriteOptionsPane.getResult(Y_POSITION_KEY)));
            Dimension size = new Dimension(Integer.parseInt(spriteOptionsPane.getResult(WIDTH_KEY)),
                              Integer.parseInt(spriteOptionsPane.getResult(HEIGHT_KEY)));
            Dimension bounds = myCanvas.getSize();
            Point velocity = new Point(0,0);
            int health = Integer.parseInt(spriteOptionsPane.getResult(HEALTH_KEY));
            Enemy newEnemy = new Enemy(position, size, bounds, imagePath, velocity, health);
            AI ai = (AI) Reflection.createInstance("vooga.shooter.gameObjects.intelligence."
                    + spriteOptionsPane.getResult(AI_KEY) + "AI", newEnemy, myLevel.getPlayer());
            newEnemy.setAI(ai);
            return newEnemy;
        }
        throw new LevelEditorException("Invalid attributes selected.");

    }

    private void openFile (File file) {

        myLevel = LevelFactory.loadLevel(file);
        
        myBackground = myLevel.getBackgroundImage();
        myBackground = myBackground.getScaledInstance(myCanvas.getWidth(), myCanvas.getHeight(),
                java.awt.Image.SCALE_SMOOTH);
        
    }

    private void saveFile (File file) {
        Document levelDoc = LevelFactory.storeLevel(myLevel);
        XmlUtilities.write(levelDoc, file.getPath());
    }

    public void newFile () {
        // TODO implement
        // Use XML Utility
    }

    /**
     * Makes a JButton with the given icon and tool tip.
     * If the icon cannot be loaded, then the text will be used instead.
     * 
     * Adds this Level Editor as an actionListener.
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
        newBtn.setBorder(BorderFactory.createRaisedBevelBorder());
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
    public void setMouseListener (MouseController m) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setKeyboardListener (KeyboardController k) {
        // TODO Auto-generated method stub

    }
    
    /**
     * Checks if a given point intersects any Sprites in the current Level.
     * @param p point to check intersection
     * @return the Sprite object the point intersects or null if no intersection occurs
     */
    private Sprite doesPointIntersectSprite(Point p) {
        int xPos = p.x;
        int yPos = p.y;
        for(Sprite s: myLevel.getSpriteList()) {
            if(xPos <= s.getRight() && xPos >= s.getLeft() && yPos >= s.getTop() && yPos <= s.getBottom()) {
                return s;
            }
        }
        return null;
    }
    /**
     * Inner class to handle mouse interaction with the Level canvas.
     * @author Zachary Hopping
     *
     */
    private class LevelEditorMouseListener implements MouseListener {

        @Override
        public void mouseClicked (MouseEvent e) {

        }

        @Override
        public void mouseEntered (MouseEvent e) {

        }

        @Override
        public void mouseExited (MouseEvent e) {

        }

        @Override
        public void mousePressed (MouseEvent e) {
            Point p = new Point(e.getX(), e.getY());
            myCurrentSprite = doesPointIntersectSprite(p);
            if (e.getButton() == MouseEvent.BUTTON1) {
                System.out.println("Left click at point (" + e.getX() + ", " + e.getY() + ")");
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
                System.out.println("Right click at point (" + e.getX() + ", " + e.getY() + ")");
                if(myCurrentSprite != null) {
                    if(myCurrentSprite instanceof Player) {
                        makePlayerOptionsPane(myCurrentSprite);
                        editPlayer();
                    } else {
                        makeEnemyOptionsPane(myCurrentSprite);
                        editCurrentSprite();
                    }
                } else {
                    makeEnemyOptionsPane(p);
                    Enemy newEnemy = makeEnemy();
                    myLevel.addSprite(newEnemy);
                    myCurrentSprite = newEnemy;
                }
            }
        }

        @Override
        public void mouseReleased (MouseEvent e) {

        }

    }
}
