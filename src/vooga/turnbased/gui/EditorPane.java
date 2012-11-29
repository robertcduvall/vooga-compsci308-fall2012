package vooga.turnbased.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        addButtons();
        addMouseListener(new GameMouseListener());
    }

    private void addButtons () {
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
                createNewLevel();
            }
        });
        add(newLevelButton);
        JButton modifyLevelButton = new JButton("Modify Existing Level");
        modifyLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                modifyExistingLevel(selectFile());
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

    private void createNewLevel () {
        System.out.println("Action performed to create a new Level");
        LevelEditor l = new LevelEditor();
    }

    private void modifyExistingLevel (File f) {
        Document xmlDocument = createXmlFromFile(f);
        System.out.println("Action performed to modify existing level");
        System.out.println(xmlDocument.getDocumentElement().getNodeName());
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

    private class GameMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked (MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mouseEntered (MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mouseExited (MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mousePressed (MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mouseReleased (MouseEvent e) {
            System.out.println(e);
        }
    }
}
