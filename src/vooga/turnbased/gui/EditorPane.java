package vooga.turnbased.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.JTextComponent;
import org.w3c.dom.Element;
import vooga.turnbased.gamecreation.LevelEditor;
import vooga.turnbased.gamecreation.PlayerEditor;

/**
 * @author Mark Hoffman
 * 
 */
@SuppressWarnings("serial")
public class EditorPane extends DisplayPane {

    private static final String USER_DIR = "user.dir";
    private static final String[] GAME_SETUP = {"Dimension Width: ", "Dimension Height: ",
        "Viewable Width: ", "Viewable Height: ", "Background Image: "};
    private static final String[] GAME_SETUP_DEFAULTS = {"20", "20", "15", "11",
        "src/vooga/turnbased/resources/image/grass.png"};
    private static final String[] OBJECTS = {"Class: ", "Condition: ",
        "Images: ", "Stats: ", "Name: "};
    private static final String[] OBJECTS_DEFAULTS = {"", "", "", "", ""};
    private static final Point DISPLAY_MAP_ORIGIN = new Point (35, 35);
    private static final int BOX_SIZE = 25;
    static File sprite = new File("src/vooga/turnbased/resources/image/enemy/trainer066.png");
    private static final Image SPRITE_IMG = new ImageIcon(sprite.getAbsolutePath()).getImage();
    static File player = new File("src/vooga/turnbased/resources/image/player.png");
    private static final Image PLAYER_IMG = new ImageIcon(player.getAbsolutePath()).getImage();
    File flashingBox = new File("src/vooga/turnbased/resources/image/flashing-box.png");
    Image flash = new ImageIcon(flashingBox.getAbsolutePath()).getImage();
    private Point[][] GRID;
    private Point myCurrentTile;
    private Image myBackground;
    private Dimension myDimension;
    private Map<Point, List<Image>> myImageMap;
    private int myMapCounter = 1;
    private List<Point> myPaintedSprites = new ArrayList<Point>();
    private Point myPlayerPoint;
    private boolean displayPlayer;
    private PlayerEditor myPlayerEditor;

    /**
     * 
     * @param gameWindow
     *            The game window that calls this editor pane
     */
    public EditorPane(GameWindow gameWindow) {
        super(gameWindow);
        addInitialButtons();
        addMouseListener(new GameMouseListener());
        myDimension = new Dimension (-1, -1);
        myCurrentTile = new Point(-1, -1);
        myPlayerPoint = new Point(-1, -1);
        myImageMap = new HashMap<Point, List<Image>>();
    }

    private void addInitialButtons () {
        addMenuButton();
        JButton newLevelButton = new JButton("Create Level");
        newLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                LevelEditor l = makeFile();
                editLevelDocument(l);
            }
        });
        add(newLevelButton);
    }
    
    /**
     * paint components of the Canvas
     * 
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < myDimension.width; i++) {
            for (int j = 0; j < myDimension.height; j++) {
                int x = DISPLAY_MAP_ORIGIN.x + i * BOX_SIZE;
                int y = DISPLAY_MAP_ORIGIN.y + j * BOX_SIZE;
                if(myBackground != null) {
                    g.drawImage(myBackground, x, y, BOX_SIZE, BOX_SIZE, null);
                }
                if (myCurrentTile.x == i && myCurrentTile.y == j)
                    g.drawImage(flash, x, y, BOX_SIZE, BOX_SIZE, null);
                GRID[i][j] = new Point(x, y);
            }
        }
        for (Point s : myPaintedSprites) {
            g.drawImage(SPRITE_IMG, s.x, s.y, BOX_SIZE, BOX_SIZE, null);
        }
        if (displayPlayer) {
            g.drawImage(PLAYER_IMG, myPlayerPoint.x, myPlayerPoint.y, BOX_SIZE, BOX_SIZE, null);
        }
    }

    private LevelEditor makeFile () {
        JFileChooser fc = new JFileChooser(System.getProperty(USER_DIR));
        fc.addChoosableFileFilter(new XmlFileFilter());
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            LevelEditor newFile = new LevelEditor(fc.getSelectedFile().toString());
            return newFile;
        }
        return null;
    }

    private void editLevelDocument(final LevelEditor l) {
        removeAll();
        repaint();
        displayAndGetSetupInformation(GAME_SETUP, GAME_SETUP_DEFAULTS, l);
        
        
        JPanel p = new JPanel(new GridLayout(20,20));
        addMenuButton();
        JButton spriteButton = setUpSpriteButton(l, OBJECTS, OBJECTS_DEFAULTS);
        add(spriteButton);
        JButton nextMap = addNewMapButton(l);
        add(nextMap);
        JButton playerButton = addPlayerButton();
        add(playerButton);
        JButton finishedButton = addDoneButton(l);
        add(finishedButton);
        add(p);
        validate();
    }

    private JButton addPlayerButton () {
        JButton b = new JButton("Make Player");
        b.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JFileChooser fc = new JFileChooser(System.getProperty(USER_DIR));
                fc.addChoosableFileFilter(new XmlFileFilter());
                int returnVal = fc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    myPlayerEditor = new PlayerEditor(fc.getSelectedFile().toString());
                    //TODO
                    Point player = GRID[myCurrentTile.x][myCurrentTile.y];
                    myPlayerPoint = new Point(player.x, player.y);
                    displayPlayer = true;
                    repaint();
                    editPlayer(myPlayerEditor);
                }
                repaint();
            }
        });
        return b;
    }

    private void editPlayer (PlayerEditor p) {
        Map<String, String> imagePaths = getHardcodedImagePaths();
        p.addMapObject("", "map", "vooga.turnbased.gameobject.mapobject.MapPlayerObject",
                "NO_ACTION", myCurrentTile.x, myCurrentTile.y, imagePaths);
        final int NUM_PAIRS = OBJECTS.length;
        final JPanel P = setUpJPanel(OBJECTS, OBJECTS_DEFAULTS, NUM_PAIRS);
        InputDisplayUtil.makeCompactGrid(P, NUM_PAIRS, 2, 6, 35, 6, 6);
        final JFrame FRAME = new JFrame(
                "Player Information (Select Add Object when all fields are ready)");
        JButton nextButton = makeNextButtonAndAddXml(NUM_PAIRS, P);
        setUpFrameAndPanel(P, FRAME, nextButton);
    }

    private JButton makeNextButtonAndAddXml (final int NUM_PAIRS, final JPanel P) {
        JButton nextButton = new JButton("Add Battle Object");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                String[] returnedValues = new String[NUM_PAIRS];
                Component[] allComponents = P.getComponents();
                int index = 0;
                for (Component current : allComponents) {
                    if (current.getClass().getName().contains("JTextField")) {
                        returnedValues[index] = ((JTextComponent) current).getText();
                        index++;
                    }
                }
                addObjectXmlInformation(returnedValues, myPlayerEditor);
                newPopUpMessage(
                        "Successfully Added Battle Object!", "To add another object, change the " +
                        "fields to desired values.  When done, close the window to continue " +
                        "game building.");
            }
        });
        return nextButton;
    }

    protected void addObjectXmlInformation (String[] returnedValues,
            PlayerEditor p) {
        p.addBattleObject("", "battle", returnedValues[0], returnedValues[1],
                returnedValues[3], returnedValues[4], returnedValues[2]);
    }

    private Map<String, String> getHardcodedImagePaths () {
        // TODO: find more dynamic way to do this
        Map<String, String> imagePaths = new HashMap<String,String>();
        imagePaths.put("down", "src/vooga/turnbased/resources/image/player/Down.png");
        imagePaths.put("down1", "src/vooga/turnbased/resources/image/player/Down1.png");
        imagePaths.put("down2", "src/vooga/turnbased/resources/image/player/Down2.png");
        imagePaths.put("up", "src/vooga/turnbased/resources/image/player/Up.png");
        imagePaths.put("up1", "src/vooga/turnbased/resources/image/player/Up1.png");
        imagePaths.put("up2", "src/vooga/turnbased/resources/image/player/Up2.png");
        imagePaths.put("right", "src/vooga/turnbased/resources/image/player/Right.png");
        imagePaths.put("right1", "src/vooga/turnbased/resources/image/player/Right1.png");
        imagePaths.put("right2", "src/vooga/turnbased/resources/image/player/Right2.png");
        imagePaths.put("left", "src/vooga/turnbased/resources/image/player/Left.png");
        imagePaths.put("left1", "src/vooga/turnbased/resources/image/player/Left1.png");
        imagePaths.put("left2", "src/vooga/turnbased/resources/image/player/Left2.png");
        return imagePaths;
    }

    private JButton setUpSpriteButton (final LevelEditor l, final String[] objects,
            final String[] objectsDefaultValues) {
        JButton spriteButton = new JButton("Add Sprite");
        spriteButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                displayAndGetSpriteInfo(objects, objectsDefaultValues, l);
            }
        });
        return spriteButton;
    }

    private void displayAndGetSpriteInfo (String[] objects,
            String[] objectsDefaultValues, LevelEditor l) {
        final int NUM_PAIRS = objects.length;
        final JPanel P = setUpJPanel(objects, objectsDefaultValues, NUM_PAIRS);
        InputDisplayUtil.makeCompactGrid(P, NUM_PAIRS, 2, 6, 35, 6, 6);
        final JFrame FRAME = new JFrame(
                "Sprite Information (Select Add Object when all fields are ready)");
        Element sprite = l.addSprite();
        JButton nextButton = makeNextButtonAndAddObjectXml(l, sprite, NUM_PAIRS, P, FRAME);
        JButton doneButton = makeDoneButton(FRAME);
        setUpFrameAndPanel(P, FRAME, nextButton, doneButton);
    }

    private JButton makeDoneButton (final JFrame FRAME) {
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                FRAME.dispose();
            }
        });
        return doneButton;
    }

    private JButton makeNextButtonAndAddObjectXml (final LevelEditor l,
            final Element sprite, final int NUM_PAIRS, final JPanel P, final JFrame FRAME) {
        Point toPaint = GRID[myCurrentTile.x][myCurrentTile.y];
        myPaintedSprites.add(toPaint);
        repaint();
        JButton nextButton = new JButton("Add Object");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                String[] returnedValues = new String[NUM_PAIRS];
                Component[] allComponents = P.getComponents();
                int index = 0;
                for (Component current : allComponents) {
                    if (current.getClass().getName().contains("JTextField")) {
                        returnedValues[index] = ((JTextComponent) current).getText();
                        index++;
                    }
                }
                addObjectXmlInformation(returnedValues, l, sprite);
                newPopUpMessage(
                        "Successfully Added Object!", "To add another object, change the " +
                        "fields to desired values.  When done, close the window to continue " +
                        "game building.");
            }
        });
        return nextButton;
    }

    private void newPopUpMessage (String title, String message) {
        JFrame j = new JFrame(title);
        JLabel l = new JLabel("<HTML>" + message + "</HTML>");
        j.add(l);
        j.pack();
        j.setSize(400, 200);
        j.setVisible(true);
    }

    private void addObjectXmlInformation (String[] returnedValues,
                    LevelEditor l, Element sprite) {
        Integer x = myCurrentTile.x;
        Integer y = myCurrentTile.y;
        String mode = "";
        //TODO: Better way than this to figure out correct mode
        if (returnedValues[0].toLowerCase().contains("map")) {
            mode = "map" + ((Integer) myMapCounter).toString();
        }
        else if (returnedValues[0].toLowerCase().contains("battle")) {
            mode = "battle";
        }
        l.addObject(sprite, "", mode, returnedValues[0],
                returnedValues[1], x.toString(), y.toString(), returnedValues[2],
                returnedValues[3], returnedValues[4]);
    }

    private void displayAndGetSetupInformation (String[] labels, String[] defaultValues,
            final LevelEditor l) {

        final int NUM_PAIRS = labels.length;
        final JPanel P = setUpJPanel(labels, defaultValues, NUM_PAIRS);
        InputDisplayUtil.makeCompactGrid(P, NUM_PAIRS, 2, 6, 6, 6, 6);
        final JFrame FRAME = new JFrame("Background Information (Default Values shown)");
        JButton doneButton = makeDoneButtonAndAddBackgroundXml(l, NUM_PAIRS, P,
                FRAME);
        setUpFrameAndPanel(P, FRAME, doneButton);
    }

    private JButton makeDoneButtonAndAddBackgroundXml (final LevelEditor l,
            final int NUM_PAIRS, final JPanel P, final JFrame FRAME) {
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                String[] returnedValues = new String[NUM_PAIRS];
                Component[] allComponents = P.getComponents();
                int index = 0;
                for (Component current : allComponents) {
                    if (current.getClass().getName().contains("JTextField")) {
                        returnedValues[index] = ((JTextComponent) current).getText();
                        index++;
                    }
                }
                addBackgroundXmlInformation(returnedValues, l);
                FRAME.dispose();
            }
        });
        return doneButton;
    }

    private JPanel setUpJPanel (String[] labels, String[] defaultValues,
            final int NUM_PAIRS) {
        final JPanel P = new JPanel(new SpringLayout());
        for (int i = 0; i < NUM_PAIRS; i++) {
            JLabel l1 = new JLabel(labels[i], JLabel.TRAILING);
            P.add(l1);
            JTextField textField = new JTextField(defaultValues[i], 10);
            l1.setLabelFor(textField);
            P.add(textField);
        }
        return P;
    }

    private void setUpFrameAndPanel (final JPanel P, final JFrame FRAME,
            JButton doneButton) {
        P.add(doneButton);
        P.setOpaque(true);
        FRAME.setContentPane(P);
        FRAME.pack();
        FRAME.setSize(new Dimension(600, 600));
        FRAME.setVisible(true);
    }

    private void setUpFrameAndPanel (final JPanel P, final JFrame FRAME,
            JButton nextButton, JButton doneButton){
        P.add(nextButton);
        setUpFrameAndPanel(P, FRAME, doneButton);
    }

    private void addBackgroundXmlInformation (String[] returnedValues, LevelEditor l) {
        for (String now : returnedValues) {
            System.out.println(now);
        }
        String imagePath = returnedValues[4];
        File imageFile = new File(imagePath);
        myBackground = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        myDimension = new Dimension(Integer.parseInt(returnedValues[0]),
                Integer.parseInt(returnedValues[1]));
        GRID = new Point[myDimension.width][myDimension.height];
        repaint();
        l.addDimensionTag(returnedValues[0], returnedValues[1]);
        l.addCameraDimension(returnedValues[2], returnedValues[3]);
        l.addBackgroundImage(returnedValues[4]);
        l.initialize();
    }
 
    private void addMenuButton () {
        JButton menuButton = new JButton("Back to menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                getGameWindow().changeActivePane(GameWindow.MENU);
            }
        });
        add(menuButton);
    }

    private JButton addDoneButton (final LevelEditor l) {
        JButton doneButton = new JButton("Finished");
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                l.saveXmlDocument();
                myPlayerEditor.modifyModes(myMapCounter);
                myPlayerEditor.saveXmlDocument();
                clear();
                getGameWindow().changeActivePane(GameWindow.MENU);
            }

        });
        return doneButton;
    }
    
    private JButton addNewMapButton(final LevelEditor l) {
        JButton doneButton = new JButton("Make another map");
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myMapCounter++;
                String mapNumber = ((Integer)myMapCounter).toString();
                l.addMode("map" + mapNumber, "vooga.turnbased.gamecore.gamemodes.MapMode",
                        "entermap" + mapNumber);
                myPaintedSprites.clear();
                repaint();
            }
        });
        return doneButton;
    }
    
    private class GameMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked (MouseEvent e) {
            int xMouse = e.getX();
            int yMouse = e.getY();
            for (int i = 0; i < myDimension.width; i++) {
                for (int j = 0; j < myDimension.height; j++) {
                    int xGrid = GRID[i][j].x;
                    int yGrid = GRID[i][j].y;
                    if (xGrid < xMouse && xMouse < xGrid + BOX_SIZE && yGrid < yMouse
                            && yMouse < yGrid + BOX_SIZE) {
                        myCurrentTile = new Point(i, j);
                        repaint();
                    }
                }
            }
        }
    }

    private void clear () {
        removeAll();
        addInitialButtons();
        validate();
        repaint();
        myDimension = new Dimension (-1, -1);
        myCurrentTile = new Point(-1, -1);
        myPaintedSprites.clear();
    }
}
