package vooga.turnbased.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static final String[] GAME_SETUP_DEFAULTS = {"20", "30", "15", "11",
        "src/vooga/turnbased/resources/image/background.png"};
    private static final String[] MODES = {"Name: ", "Class: ", "Condition: "};
    private static final String[] MODES_DEFAULTS = {"", "", ""};
    private static final String[] OBJECTS = {"Create On: ", "Modes: ", "Class: ", "Condition: ",
        "X-Coordinate: ", "Y-Coordinate: ", "Images: ", "Stats: ", "Name: "};
    private static final String[] OBJECTS_DEFAULTS = {"", "", "", "", "", "", "", "", ""};
    private static final Point DISPLAY_MAP_DIMENSION = new Point (700, 500);
    private static final Point DISPLAY_MAP_ORIGIN = new Point (50, 85);
    private static final int BOX_SIZE = 20;

    /**
     * 
     * @param gameWindow
     *            The game window that calls this editor pane
     */
    public EditorPane(GameWindow gameWindow) {
        super(gameWindow);
        addInitialButtons();
    }

    private void addInitialButtons () {
        addMenuButton();
        JButton newLevelButton = new JButton("Create Level");
        newLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                String dir = System.getProperty(USER_DIR);
                LevelEditor l = makeFile();
                editLevelDocument(l);
                PlayerEditor p = new PlayerEditor(dir +
                        "/src/vooga/turnbased/resources/level/testPlayer.xml");
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
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                g.drawRect(DISPLAY_MAP_ORIGIN.x + i * BOX_SIZE,
                        DISPLAY_MAP_ORIGIN.y + j * BOX_SIZE, BOX_SIZE, BOX_SIZE);
            }
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
        addMenuButton();
        JButton modeButton = setUpModeButton(l, MODES, MODES_DEFAULTS);
        add(modeButton);
        JButton spriteButton = setUpSpriteButton(l, OBJECTS, OBJECTS_DEFAULTS);
        add(spriteButton);
        // TODO: Add create player button somewhere in this mess
        JButton finishedButton = addDoneButton(l);
        add(finishedButton);
        validate();
    }

    private JButton setUpSpriteButton (final LevelEditor l, final String[] objects,
            final String[] objectsDefaultValues) {
        JButton spriteButton = new JButton("Add Sprites");
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
        l.addObject(sprite, returnedValues[0], returnedValues[1], returnedValues[2],
                returnedValues[3], returnedValues[4], returnedValues[5], returnedValues[6],
                returnedValues[7], returnedValues[8]);
    }

    private JButton setUpModeButton (final LevelEditor l, final String[] modes,
            final String[] modesDefaultValues) {
        JButton modeButton = new JButton("Set up Modes");
        modeButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                displayAndGetModeInformation(modes, modesDefaultValues, l);
            }
        });
        return modeButton;
    }

    private void displayAndGetModeInformation (String[] labels,
            String[] defaultValues, final LevelEditor l) {
        final int NUM_PAIRS = labels.length;
        final JPanel P = setUpJPanel(labels, defaultValues, NUM_PAIRS);
        InputDisplayUtil.makeCompactGrid(P, NUM_PAIRS, 2, 6, 6, 6, 6);
        final JFrame FRAME = new JFrame("Modes Information");
        JButton addButton = makeAddButtonAndAddModeXml(l, NUM_PAIRS, P, FRAME);
        setUpFrameAndPanel(P, FRAME, addButton);
    }

    private JButton makeAddButtonAndAddModeXml (final LevelEditor l,
            final int NUM_PAIRS, final JPanel P, final JFrame FRAME) {
        JButton addButton = new JButton("Add Mode");
        addButton.addActionListener(new ActionListener() {
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
                addModesXmlInformation(returnedValues, l);
            }
        });
        return addButton;
    }

    private void addModesXmlInformation (
            String[] returnedValues, LevelEditor l) {
        l.addMode(returnedValues[0], returnedValues[1], returnedValues[2]);
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
        l.addDimensionTag(returnedValues[0], returnedValues[1]);
        l.addCameraDimension(returnedValues[2], returnedValues[3]);
        l.addBackgroundImage(returnedValues[4]);
        l.addStartMode();
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
                getGameWindow().changeActivePane(GameWindow.MENU);
            }
        });
        return doneButton;
    }
}
