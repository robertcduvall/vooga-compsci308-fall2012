package vooga.platformer.leveleditor;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class EditorMenuBar extends JMenuBar{
    private static final long serialVersionUID = 1L;

    public EditorMenuBar(final LevelEditor myEditor) {
        JMenu levelMenu = new JMenu("Level");
        levelMenu.add(new AbstractAction("Load") {
            @Override
            public void actionPerformed(ActionEvent e) {
                myEditor.load();
            }
        });
        levelMenu.add(new AbstractAction("Clear") {
            @Override
            public void actionPerformed(ActionEvent e) {
                myEditor.clear();
            }
        });
        levelMenu.add(new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                myEditor.newLevel();
            }
        });
        levelMenu.add(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                myEditor.save();
            }
        });
        JMenu attributeMenu = new JMenu("Add Level Plugin");
        List<String> plugins = getLevelPlugins();
        for(String plugin : plugins) {
            attributeMenu.add(new AbstractAction(plugin) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    myEditor.addLevelPlugin(e.getActionCommand());
                }
            });
        }
        levelMenu.add(attributeMenu);
        JMenu spriteMenu = new JMenu("Sprite");
        spriteMenu.add(new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //newLevel();
            }
        });
        add(levelMenu);
        add(spriteMenu);
    }

    private List<String> getLevelPlugins () {
        List<String> list = new ArrayList<String>();
        Scanner s = null;
        try {
            s = new Scanner(new File(System.getProperty("user.dir")+"/src/vooga/platformer/data/AvailableLevelPlugins.txt"));
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while(s.hasNext()) {
            String plug = s.nextLine();
            plug = plug.substring(plug.lastIndexOf(".")+1, plug.length());
            list.add(plug);
        }
        list.add("Background Painter");
        list.add("Message Painter");
        list.add("Other");
        return list;
    }

}