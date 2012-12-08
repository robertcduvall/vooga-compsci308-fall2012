package vooga.platformer.leveleditor;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import vooga.platformer.levelfileio.LevelFileIOException;


/**
 * A standard menu bar like that which appears at the top of most windowed
 * programs. Allows the user to save, load, make new levels, and modify aspects
 * of the level.
 * 
 * @author Sam Rang
 * 
 */
public class EditorMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new EditorMenuBar for the specified LevelEditor.
     * 
     * @param LevelEditor in which the menu bar appears
     */
    @SuppressWarnings("serial")
    public EditorMenuBar (final LevelEditor myEditor) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new AbstractAction("Load") {
            @Override
            public void actionPerformed (ActionEvent e) {
                myEditor.load();
            }
        });
        fileMenu.add(new AbstractAction("New") {
            @Override
            public void actionPerformed (ActionEvent e) {
                myEditor.newLevel();
            }
        });
        fileMenu.add(new AbstractAction("Save") {
            @Override
            public void actionPerformed (ActionEvent e) {
                myEditor.save();
            }
        });

        // Level Menu
        JMenu levelMenu = new JMenu("Level");
        JMenu attributeMenu = new JMenu("Add Level Plugin");
        List<String> plugins = getLevelItems("AvailableLevelPlugins.txt");
        for (String plugin : plugins) {
            attributeMenu.add(new AbstractAction(plugin) {
                @Override
                public void actionPerformed (ActionEvent e) {
                    myEditor.addLevelPlugin(e.getActionCommand());
                }
            });
        }
        levelMenu.add(attributeMenu);
        JMenu conditionMenu = new JMenu("Add Level Condition");
        List<String> conditions = getLevelItems("AvailableConditions.txt");
        conditions.add("Clear Conditions");
        for (String cond : conditions) {
            conditionMenu.add(new AbstractAction(cond) {
                @Override
                public void actionPerformed (ActionEvent e) {
                    myEditor.addLevelConditions(e.getActionCommand());
                }
            });
        }
        levelMenu.add(conditionMenu);
        levelMenu.add(new AbstractAction("Clear") {
            @Override
            public void actionPerformed (ActionEvent e) {
                myEditor.clear();
            }
        });

        add(fileMenu);
        add(levelMenu);
    }

    private List<String> getLevelItems (String str) {
        List<String> list = new ArrayList<String>();
        Scanner s = null;
        try {
            s =
                    new Scanner(new File(System.getProperty("user.dir") +
                                         "/src/vooga/platformer/data/" + str));
        }
        catch (FileNotFoundException e) {
            throw new LevelFileIOException("Could not find the level in the file system", e);
        }
        while (s.hasNext()) {
            String item = s.nextLine();
            item = item.substring(item.lastIndexOf(".") + 1, item.length());
            list.add(item);
        }
        list.add("Other");
        return list;
    }

}