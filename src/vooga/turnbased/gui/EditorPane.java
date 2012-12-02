package vooga.turnbased.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.JTextComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;
import vooga.turnbased.gamecreation.LevelEditor;
import vooga.turnbased.gamecreation.PlayerEditor;

/**
 * @author Mark Hoffman
 * 
 */
@SuppressWarnings("serial")
public class EditorPane extends DisplayPane {

    private static final String USER_DIR = "user.dir";

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
        JButton newLevelButton = new JButton("Create New Level");
        newLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                String dir = System.getProperty(USER_DIR);
                LevelEditor l = new LevelEditor(dir +
                        "/src/vooga/turnbased/resources/level/testLevel.xml");
                editLevelDocument(l);
                PlayerEditor p = new PlayerEditor(dir +
                        "/src/vooga/turnbased/resources/level/testPlayer.xml");
            }
        });
        add(newLevelButton);
        JButton modifyLevelButton = new JButton("Modify Existing Level");
        modifyLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                LevelEditor l = modifyExistingLevel(selectFile());
                editLevelDocument(l);
            }
        });
        //add(modifyLevelButton);
    }

    /**
     * paint components of the Canvas
     * 
     * @param g
     *            Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image background = GameWindow.importImage("EditorBackgroundImage");
        g.drawImage(background, 0, 0, background.getWidth(null),
                background.getHeight(null), this);
    }

    private LevelEditor modifyExistingLevel(File f) {
        Document xmlDocument = createXmlFromFile(f);
        return new LevelEditor(xmlDocument, f.toString());
    }

    private Document createXmlFromFile(File f) {
        int extensionStart = f.toString().lastIndexOf(".");
        String extension = f.toString().substring(extensionStart);
        if (".xml".equals(extension)) {
            return XmlUtilities.makeDocument(f);
        }
        return null;
    }

    private File selectFile () {
        JFileChooser fc = new JFileChooser(System.getProperty(USER_DIR));
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }
        return null;
    }

    private void editLevelDocument(final LevelEditor l) {
        removeAll();
        repaint();
        String[] background = {"Dimension Width: ", "Dimension Height: ",
                "Viewable Width: ", "Viewable Height: ", "Background Image: "};
        String[] backgroundDefaultValues = {"20", "30", "15", "11",
        "src/vooga/turnbased/resources/image/background.png"};
        displayAndGetSetupInformation(background, backgroundDefaultValues, l);
        addMenuButton();
        final String[] modes = {"Name: ", "Class: ", "Condition: "};
        final String[] modesDefaultValues = {"", "", ""};
        JButton modeButton = setUpModeButton(l, modes, modesDefaultValues);
        add(modeButton);
        final String[] objects = {"Create On: ", "Modes: ", "Class: ", "Condition: ",
                "X-Coordinate: ", "Y-Coordinate: ", "Images: ", "Stats: ", "Name: "};
        final String[] objectsDefaultValues = {"","","","","","","","",""};
        JButton spriteButton = setUpSpriteButton(l, objects, objectsDefaultValues);
        add(spriteButton);
        // TODO: Add create player button somewhere in this mess
        // TODO: Done button that saves all levelEditor changes (remove saves throughout)
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
        final JFrame FRAME = new JFrame("Sprite Information (Select Add Object when all fields are ready)");
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
                l.saveXmlDocument();
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
        JButton doneButton = makeDoneButtonAndAddModeXml(l, NUM_PAIRS, P, FRAME);
        setUpFrameAndPanel(P, FRAME, doneButton);
    }

    private JButton makeDoneButtonAndAddModeXml (final LevelEditor l,
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
                addModesXmlInformation(returnedValues, l);
                FRAME.dispose();
            }
        });
        return doneButton;
    }

    private void addModesXmlInformation (
                    String[] returnedValues, LevelEditor l) {
                l.addMode(returnedValues[0], returnedValues[1], returnedValues[2]);
                l.saveXmlDocument();
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
        l.saveXmlDocument();
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
}
