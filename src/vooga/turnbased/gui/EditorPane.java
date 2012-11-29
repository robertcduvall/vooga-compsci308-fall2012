package vooga.turnbased.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import org.w3c.dom.Document;
import util.xml.XmlUtilities;
import vooga.turnbased.gamecreation.LevelEditor;

/**
 * @author s
 *
 */
@SuppressWarnings("serial")
public class EditorPane extends DisplayPane {

    /**
     * 
     * @param gameWindow The game window that calls this editor pane
     */
    public EditorPane (GameWindow gameWindow) {
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
                LevelEditor l = new LevelEditor();
                editDocument(l);
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

    /**
     * paint components of the Canvas
     * 
     * @param g Graphics
     */
    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Image background = GameWindow.importImage("EditorBackgroundImage");
        g.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null), this);
    }

    private LevelEditor modifyExistingLevel (File f) {
        Document xmlDocument = createXmlFromFile(f);
        return new LevelEditor(xmlDocument, f.toString());
    }

    private Document createXmlFromFile (File f) {
        int extensionStart = f.toString().lastIndexOf(".");
        String extension = f.toString().substring(extensionStart);
        if (".xml".equals(extension)) {
            return XmlUtilities.makeDocument(f);
        }
        return null;
    }

    private File selectFile () {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }
        return null;
    }

    private void editDocument (LevelEditor l) {
        removeAll();
        repaint();
    }
}
