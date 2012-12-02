package vooga.turnbased.gui;


import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.text.JTextComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;
import vooga.turnbased.gamecreation.LevelEditor;
import vooga.turnbased.gamecreation.PlayerEditor;

/**
 * @author s
 * 
 */
@SuppressWarnings("serial")
public class EditorPane extends DisplayPane {

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
                String dir = System.getProperty("user.dir");
                LevelEditor l = new LevelEditor(dir +
                        "/src/vooga/turnbased/resources/level/testLevel.xml");
                editDocument(l);
                //testHardcodedLevelEditor(l);
                PlayerEditor p = new PlayerEditor(dir +
                        "/src/vooga/turnbased/resources/level/testPlayer.xml");
                testHardcodedPlayerEditor(p);
            }
        });
        add(newLevelButton);
        JButton modifyLevelButton = new JButton("Modify Existing Level");
        modifyLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                LevelEditor l = modifyExistingLevel(selectFile());
                editDocument(l);
            }
        });
        add(modifyLevelButton);
    }

    private void testHardcodedLevelEditor (LevelEditor l) {
        l.addDimensionTag(20, 30);
        l.addBackgroundImage("THIS IS THE NEW IMAGE PATH");
        l.addCameraDimension(10, 10);
        Element sprite = l.addSprite();
        String[] mapImagePaths = {"img1", "img2"};
        l.addMapObject(sprite, "CREATE","map","MAP CLASS", "MAP EVENT", 5, 5, mapImagePaths);
        Map<String, Number> stats = new HashMap<String, Number>();
        stats.put("health", 10);
        stats.put("attack", 5);
        stats.put("defense", 8);
        String[] battleImagePaths = {"img1", "img2"};
        l.addBattleObject(sprite, "CREATE", "battle","BATTLE CLASS", "BATTLE EVENT", stats,
                "NAME", battleImagePaths);
        l.saveXmlDocument();
    }

    private void testHardcodedPlayerEditor (PlayerEditor p) {
        Element player = p.addPlayer();
        Map<String, String> imagePaths = new HashMap<String,String>();
        imagePaths.put("source1", "direction1");
        imagePaths.put("source2", "direction2");
        p.addMapObject(player, "start", "map1, map2", "MAP CLASS", "MAP EVENT", 4, 4, imagePaths);
        Map<String, Number> stats = new HashMap<String, Number>();
        stats.put("health", 3);
        stats.put("attack", 2);
        stats.put("defense", 1);
        String[] imagePath = {"PATH1", "PATH2"};
        p.addBattleObject(player, "start", "battle", "CLASS", "CONDITION", stats, "NAME", imagePath);
        stats.put("health", 6);
        stats.put("attack", 5);
        stats.put("defense", 4);
        p.modifyBattleStats(player, stats, "NAME");
        p.saveXmlDocument();
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
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }
        return null;
    }

    private void editDocument(LevelEditor l) {
        removeAll();
        repaint();
        String[] background = {"Dimension Width: ", "Dimension Height: ",
                "Viewable Width: ", "Viewable Height: ", "Background Image: "};
        String[] defaultValues = {"20", "30", "15", "11",
                "src/vooga/turnbased/resources/image/background.png"};
        displayAndGetSetupInformation(background, defaultValues, l);
        addMenuButton();
        validate();
    }

    private void displayAndGetSetupInformation (String[] labels, String[] defaultValues,
            final LevelEditor l) {

        final int numPairs = labels.length;
        final JPanel p = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            JLabel l1 = new JLabel(labels[i], JLabel.TRAILING);
            p.add(l1);
            JTextField textField = new JTextField(defaultValues[i], 10);
            l1.setLabelFor(textField);
            p.add(textField);
        }
        InputDisplayUtil.makeCompactGrid(p, numPairs, 2, 6, 6, 6, 6);
        final JFrame frame = new JFrame("Background Information (Default Values shown)");
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                String[] returnedValues = new String[numPairs];
                Component[] allComponents = p.getComponents();
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
        p.add(doneButton);  
        p.setOpaque(true);
        frame.setContentPane(p);
        frame.pack();
        frame.setSize(new Dimension(600, 400));
        frame.setVisible(true);
    }

    private void addBackgroundXmlInformation (String[] returnedValues, LevelEditor l) {
        for (String now : returnedValues) {
            System.out.println(now);
        }
        l.addDimensionTag(Integer.parseInt(returnedValues[0]),
                Integer.parseInt(returnedValues[1]));
        l.addCameraDimension(Integer.parseInt(returnedValues[2]),
                Integer.parseInt(returnedValues[3]));
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
