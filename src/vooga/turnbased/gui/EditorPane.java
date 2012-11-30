package vooga.turnbased.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
        JButton menuButton = new JButton("Back to menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                getGameWindow().changeActivePane(GameWindow.MENU);
            }
        });
        add(menuButton);
        JButton newLevelButton = new JButton("Create New Level");
        newLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                String dir = System.getProperty("user.dir");
                LevelEditor l = new LevelEditor(dir +
                        "/src/vooga/turnbased/resources/level/testLevel.xml");
                editDocument(l);
                testHardcodedLevelEditor(l);
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
        l.addLevelId(1);
        l.addPlayerEntryPoints(5, 5);
        Element sprite = l.addSprite();
        l.addMapObject(sprite, "MAP CLASS", "MAP EVENT", 5, 5, "MAP IMAGE PATH");
        Map<String, Number> stats = new HashMap<String, Number>();
        stats.put("health", 10);
        stats.put("attack", 5);
        stats.put("defense", 8);
        l.addBattleObject(sprite, "BATTLE CLASS", "BATTLE EVENT", stats, "NAME", "BATTLE IMG PATH");
        l.saveXmlDocument();
    }

    private void testHardcodedPlayerEditor (PlayerEditor p) {
        Element player = p.addPlayer();
        Map<String, String> imagePaths = new HashMap<String,String>();
        imagePaths.put("source1", "direction1");
        imagePaths.put("source2", "direction2");
        p.addPlayerMapObject(player, "MAP CLASS", "MAP EVENT", 4, 4, imagePaths);
        Map<String, Number> stats = new HashMap<String, Number>();
        stats.put("health", 3);
        stats.put("attack", 2);
        stats.put("defense", 1);
        p.addBattleObject(player, "BATTLE CLASS", "BATTLE EVENT", stats, "NAME", "BATTLE IMG");
        stats.put("health", 6);
        stats.put("attack", 5);
        stats.put("defense", 4);
        p.modifyBattleStats(player, stats);
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
	}
}
