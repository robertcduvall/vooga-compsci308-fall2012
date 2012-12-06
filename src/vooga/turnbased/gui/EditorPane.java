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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.JTextComponent;
import org.w3c.dom.Element;
import util.projectsearcher.SubClassFinder;
import vooga.turnbased.gamecreation.LevelEditor;
import vooga.turnbased.gamecreation.PlayerEditor;
import vooga.turnbased.gameobject.GameObject;

/**
 * @author Mark Hoffman
 * 
 */
@SuppressWarnings("serial")
public class EditorPane extends DisplayPane {

    private static final String MAKE_CLASS_OPTIONS = "MAKE_CLASS_OPTIONS";
    private static final String MAKE_CONDITION_OPTIONS = "MAKE_CONDITION_OPTIONS";
    private static final String USER_DIR = "user.dir";
    private static final String[] GAME_SETUP = {"Dimension Width: ", "Dimension Height: ",
        "Viewable Width: ", "Viewable Height: ", "Background Image: "};
    private static final String[] GAME_SETUP_DEFAULTS = {"18", "20", "15", "11",
        "src/vooga/turnbased/resources/image/grass.png"};
    private static final String[] OBJECTS = {"Class: ", "Condition: ",
        "Images: ", "Stats: ", "Name: "};
    private static final String[] OBJECTS_DEFAULTS = {
        MAKE_CLASS_OPTIONS, MAKE_CONDITION_OPTIONS,
        "src/vooga/turnbased/resources/image/[specific image path]",
        "maxHealth:30, health:30, attack:10, defense:10", ""};
    private static final Point DISPLAY_MAP_ORIGIN = new Point(35, 35);
    private static final int BOX_SIZE = 25;
    private static final File SPRITE = new File(
            "src/vooga/turnbased/resources/image/enemy/trainer066.png");
    private static final Image SPRITE_IMG = new ImageIcon(SPRITE.getAbsolutePath()).getImage();
    private static final File PLAYER = new File("src/vooga/turnbased/resources/image/player.png");
    private static final Image PLAYER_IMG = new ImageIcon(PLAYER.getAbsolutePath()).getImage();
    private static final File FLASHING_BOX = new File(
            "src/vooga/turnbased/resources/image/flashing-box.png");
    private static final Image FLASH_BOX = new ImageIcon(FLASHING_BOX.getAbsolutePath()).getImage();
    private static final GridLayout GRID_LAYOUT = new GridLayout(20, 20);
    private static final String NO_ACTION = "NO_ACTION";
    private static final String MAP = "map";
    private static final String DONE = "done";
    private static final int SIX = 6;
    private static final int THIRTY_FIVE = 35;
    private static final int TWO_HUNDRED = 200;
    private static final int FOUR_HUNDRED = 400;
    private static final int SIX_HUNDRED = 600;
    private Point[][] myGrid;
    private Point myCurrentTile;
    private Image myBackground;
    private Dimension myDimension;
    private int myMapCounter = 1;
    private List<Point> myPaintedSprites = new ArrayList<Point>();
    private Point myPlayerPoint;
    private boolean myDisplayPlayer;
    private PlayerEditor myPlayerEditor;
    private String myBackgroundPath;

    /**
     * 
     * @param gameWindow
     *            The game window that calls this editor pane
     */
    public EditorPane(GameWindow gameWindow) {
        super(gameWindow);
        addInitialButtons();
        addMouseListener(new GameMouseListener());
        myDimension = new Dimension(-1, -1);
        myCurrentTile = new Point(-1, -1);
        myPlayerPoint = new Point(-1, -1);
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
                if (myBackground != null) {
                    g.drawImage(myBackground, x, y, BOX_SIZE, BOX_SIZE, null);
                }
                if (myCurrentTile.x == i && myCurrentTile.y == j) {
                    g.drawImage(FLASH_BOX, x, y, BOX_SIZE, BOX_SIZE, null);
                }
                myGrid[i][j] = new Point(x, y);
            }
        }
        for (Point s : myPaintedSprites) {
            g.drawImage(SPRITE_IMG, s.x, s.y, BOX_SIZE, BOX_SIZE, null);
        }
        if (myDisplayPlayer) {
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
        JPanel p = new JPanel(GRID_LAYOUT);
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
                    Point player = myGrid[myCurrentTile.x][myCurrentTile.y];
                    myPlayerPoint = new Point(player.x, player.y);
                    myDisplayPlayer = true;
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
        p.addMapObject("", MAP, "vooga.turnbased.gameobject.mapobject.MapPlayerObject",
                NO_ACTION, myCurrentTile.x, myCurrentTile.y, imagePaths);
        final int NUM_PAIRS = OBJECTS.length;
        final JPanel PANEL = setUpJPanel(OBJECTS, OBJECTS_DEFAULTS, NUM_PAIRS);
        InputDisplayUtil.makeCompactGrid(PANEL, NUM_PAIRS, 2, SIX, THIRTY_FIVE, SIX, SIX);
        final JFrame FRAME = new JFrame(
                "Player Information (Select Add Object when all fields are ready)");
        JButton nextButton = makeNextButtonAndAddXml(NUM_PAIRS, PANEL);
        setUpFrameAndPanel(PANEL, FRAME, nextButton);
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
                    else if (current.getClass().getName().contains("JComboBox")) {
                        returnedValues[index] = ((JComboBox) current).getSelectedItem().toString();
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
        Map<String, String> imagePaths = new HashMap<String, String>();
        imagePaths.put("src/vooga/turnbased/resources/image/player/Down.png", "down");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Down1.png", "down1");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Down2.png", "down2");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Up.png", "up");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Up1.png", "up1");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Up2.png", "up2");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Right.png", "right");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Right1.png", "right1");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Right2.png", "right2");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Left.png", "left");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Left1.png", "left1");
        imagePaths.put("src/vooga/turnbased/resources/image/player/Left2.png", "left2");
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
        final JPanel panel = setUpJPanel(objects, objectsDefaultValues, NUM_PAIRS);
        InputDisplayUtil.makeCompactGrid(panel, NUM_PAIRS, 2, SIX, THIRTY_FIVE, SIX, SIX);
        final JFrame FRAME = new JFrame(
                "Sprite Information (Select Add Object when all fields are ready)");
        Element sprite = l.addSprite();
        JButton nextButton = makeNextButtonAndAddObjectXml(l, sprite, NUM_PAIRS, panel);
        JButton doneButton = makeDoneButton(FRAME);
        setUpFrameAndPanel(panel, FRAME, nextButton, doneButton);
    }

    private JButton makeDoneButton (final JFrame frame) {
        JButton doneButton = new JButton(DONE);
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                frame.dispose();
            }
        });
        return doneButton;
    }

    private JButton makeNextButtonAndAddObjectXml (final LevelEditor l,
            final Element sprite, final int numPairs, final JPanel panel) {
        Point toPaint = myGrid[myCurrentTile.x][myCurrentTile.y];
        myPaintedSprites.add(toPaint);
        repaint();
        JButton nextButton = new JButton("Add Object");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                String[] returnedValues = new String[numPairs];
                Component[] allComponents = panel.getComponents();
                int index = 0;
                for (Component current : allComponents) {
                    if (current.getClass().getName().contains("JTextField")) {
                        returnedValues[index] = ((JTextComponent) current).getText();
                        index++;
                    }
                    else if (current.getClass().getName().contains("JComboBox")) {
                        returnedValues[index] = ((JComboBox) current).getSelectedItem().toString();
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
        j.setSize(FOUR_HUNDRED, TWO_HUNDRED);
        j.setVisible(true);
    }

    private void addObjectXmlInformation (String[] returnedValues,
                    LevelEditor l, Element sprite) {
        Integer x = myCurrentTile.x;
        Integer y = myCurrentTile.y;
        String mode = "";
        if (returnedValues[0].toLowerCase().contains("mapobject")) {
            mode = MAP + ((Integer) myMapCounter).toString();
        }
        else if (returnedValues[0].toLowerCase().contains("battleobject")) {
            mode = "battle";
        }
        l.addObject(sprite, "", mode, returnedValues[0],
                returnedValues[1], x.toString(), y.toString(), returnedValues[2],
                returnedValues[3], returnedValues[4]);
    }

    private void displayAndGetSetupInformation (String[] labels, String[] defaultValues,
            final LevelEditor l) {

        final int NUM_PAIRS = labels.length;
        final JPanel panel = setUpJPanel(labels, defaultValues, NUM_PAIRS);
        InputDisplayUtil.makeCompactGrid(panel, NUM_PAIRS, 2, SIX, SIX, SIX, SIX);
        final JFrame FRAME = new JFrame("Background Information (Default Values shown)");
        JButton doneButton = makeDoneButtonAndAddBackgroundXml(l, NUM_PAIRS, panel,
                FRAME);
        setUpFrameAndPanel(panel, FRAME, doneButton);
    }

    private JButton makeDoneButtonAndAddBackgroundXml (final LevelEditor l,
            final int numPairs, final JPanel panel, final JFrame frame) {
        JButton doneButton = new JButton(DONE);
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                String[] returnedValues = new String[numPairs];
                Component[] allComponents = panel.getComponents();
                int index = 0;
                for (Component current : allComponents) {
                    if (current.getClass().getName().contains("JTextField")) {
                        returnedValues[index] = ((JTextComponent) current).getText();
                        index++;
                    }
                }
                addBackgroundXmlInformation(returnedValues, l);
                frame.dispose();
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
            if (defaultValues[i].equals(MAKE_CLASS_OPTIONS)) {
                SubClassFinder cf = new SubClassFinder();
                Collection<Class<?>> subClassesOfGameObject = cf.getSubClasses(GameObject.class);
                String[] options = new String[subClassesOfGameObject.size()];
                int index = 0;
                for (Class<?> c : subClassesOfGameObject) {
                    options[index] = c.toString();
                    index++;
                }
                JComboBox box = new JComboBox(options);
                P.add(box);
            }
            else if (defaultValues[i].equals(MAKE_CONDITION_OPTIONS)) {
                String[] options = {"dobattle", "endgame", "entermap", "optionstuff", NO_ACTION};
                JComboBox box = new JComboBox(options);
                P.add(box);
            }
            else {
                JTextField textField = new JTextField(defaultValues[i], 10);
                l1.setLabelFor(textField);
                P.add(textField);
            }
        }
        return P;
    }

    private void setUpFrameAndPanel (final JPanel P, final JFrame FRAME,
            JButton doneButton) {
        P.add(doneButton);
        P.setOpaque(true);
        FRAME.setContentPane(P);
        FRAME.pack();
        FRAME.setSize(new Dimension(SIX_HUNDRED, SIX_HUNDRED));
        FRAME.setVisible(true);
    }

    private void setUpFrameAndPanel (final JPanel P, final JFrame FRAME,
            JButton nextButton, JButton doneButton) {
        P.add(nextButton);
        setUpFrameAndPanel(P, FRAME, doneButton);
    }

    private void addBackgroundXmlInformation (String[] returnedValues, LevelEditor l) {
        String imagePath = returnedValues[4];
        File imageFile = new File(imagePath);
        myBackgroundPath = imageFile.getPath();
        myBackground = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        myDimension = new Dimension(Integer.parseInt(returnedValues[0]),
                Integer.parseInt(returnedValues[1]));
        myGrid = new Point[myDimension.width][myDimension.height];
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
                l.addStaticSprites(myBackgroundPath, myMapCounter);
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
                l.addMode(MAP + mapNumber, "vooga.turnbased.gamecore.gamemodes.MapMode",
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
                    int xGrid = myGrid[i][j].x;
                    int yGrid = myGrid[i][j].y;
                    if (xGrid < xMouse && xMouse < xGrid + BOX_SIZE && yGrid < yMouse && 
                            yMouse < yGrid + BOX_SIZE) {
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
        myPlayerPoint = new Point(-1, -1);
        myPaintedSprites.clear();
        myDisplayPlayer = false;
    }
}
