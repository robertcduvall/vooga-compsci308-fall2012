package vooga.platformer.leveleditor;

import java.util.Collection;
import java.util.HashSet;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import util.projectsearcher.SubClassFinder;
import util.reflection.Reflection;
import vooga.platformer.leveleditor.leveldrawer.IEditorObject;

/*
 * I haven't yet tested this class and I'm not entirely sure how
 * JMenus work. If this class works full time it would be a miracle.
 * It won't though.
 */

/**
 * This class offers the user a choice to switch between
 * different modes of the editor. This class needs to instantiate
 * each new mode and ensure a smooth transition between modes.
 * 
 * @author Paul Dannenberg
 * 
 */
public class ModeChooser extends JMenu {

    private static final long serialVersionUID = -680095609392880328L;
    private IEditorMode myCurrent;
    private LevelBoard myBoard;

    /**
     * Creates a new object capable of listening to user input and
     * transitioning between modes accordingly.
     * 
     * @param board The board whose modes this object should manage.
     */
    public ModeChooser (LevelBoard board) {
        myCurrent = new PlacementMode();
        myBoard = board;
        setupMenu();
    }

    /**
     * Finds the modes that are available for the level editor.
     * 
     * @return All the possible modes that the editor supports.
     * 
     */
    private Collection<IEditorMode> getDifferentModes () {
        Collection<IEditorMode> editorModes = new HashSet<IEditorMode>();
        SubClassFinder classFinder = new SubClassFinder();
        for (Class<?> modeClass : classFinder.getSubClasses(IEditorMode.class)) {
            editorModes.add((IEditorMode) Reflection.createInstance(modeClass.getCanonicalName()));
        }
        return editorModes;
    }

    /**
     * Sets up the menu that displays the possible game modes.
     */
    private void setupMenu () {
        Collection<IEditorMode> differentModes = getDifferentModes();
        for (IEditorMode editorMode : differentModes) {
            setupMenuItem(editorMode);
        }

    }

    /**
     * Switches from one mode to the next, saving objects from the first and
     * ensuring
     * these are successfully handed to the next mode.
     * 
     * @param currentMode The current mode the editor is in.
     * @param nextMode The next mode the editor is about to transition to.
     */
    private void transitionBetweenModes (IEditorMode currentMode, IEditorMode nextMode) {
        Collection<IEditorObject> editorObjectsToKeep = currentMode.getEditorObjects();
        for (IEditorObject objectFromPreviousMode : editorObjectsToKeep) {
            nextMode.add(objectFromPreviousMode);
        }
    }

    /**
     * Sets up each menu item displaying the name of each possible mode.
     * @param mode The mode which this menu item will represent.
     * @return A menu item for the specified editor mode.
     */
    private JMenuItem setupMenuItem (IEditorMode mode) {
        JMenuItem menuItem = new JMenuItem(mode.getClass().getSimpleName());
        MenuKeyListener listener = new ModeMenuListener(mode);
        menuItem.addMenuKeyListener(listener);
        return menuItem;
    }

    /**
     * A menu listener that switches the level editor between
     * modes, if a particular menu key is pressed.
     * @author Paul Dannenberg
     *
     */
    private class ModeMenuListener implements MenuKeyListener {

        private IEditorMode myMode;

        public ModeMenuListener (IEditorMode mode) {
            myMode = mode;
        }

        @Override
        public void menuKeyPressed (MenuKeyEvent e) {
        }

        @Override
        public void menuKeyReleased (MenuKeyEvent e) {
            transitionBetweenModes(myCurrent, myMode);
            myBoard.setMode(myMode);
        }

        @Override
        public void menuKeyTyped (MenuKeyEvent e) {
        }

    }
}