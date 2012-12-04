package vooga.platformer.leveleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import util.projectsearcher.SubClassFinder;
import util.reflection.Reflection;



/**
 * This class offers the user a choice to switch between
 * different modes of the editor. It acts as a factory
 * and instantiates new modes.
 * 
 * @author Paul Dannenberg
 * 
 */
public class ModeChooser extends JMenu {

    private static final long serialVersionUID = -680095609392880328L;
    private LevelBoard myBoard;

    /**
     * Creates a new object capable of listening to user input and
     * causing a transition between level editor modes.
     * 
     * @param board The board whose modes this object should manage.
     */
    public ModeChooser (LevelBoard board, String menuTitle) {
        super(menuTitle);
        myBoard = board;
        setInitialMode();
        setupMenu();
    }

    /**
     * Starts the level editor in a particular default mode.
     */
    private void setInitialMode () {
        myBoard.setMode(new PlacementMode());
    }

    /**
     * Finds the modes that are available for the level editor.
     * 
     * @return All the possible modes that the editor supports.
     * 
     */
    private Collection<Class<?>> getDifferentModes () {
        SubClassFinder classFinder = new SubClassFinder();
        return classFinder.getSubClasses(IEditorMode.class);
    }

    /**
     * Sets up the menu that displays the possible game modes.
     */
    private void setupMenu () {
        Collection<Class<?>> differentModes = getDifferentModes();
        for (Class<?> editorMode : differentModes) {
            add(setupMenuItem(editorMode));
        }
    }

    /**
     * Finds the mode the user has selected and instantiates it.
     * 
     * @param nextMode The mode that will be instantiated.
     * @return An IEditorMode object representing an editor mode.
     */
    private IEditorMode instantiateMode (Class<?> nextMode) {
        return (IEditorMode) Reflection.createInstance(nextMode.getCanonicalName());
    }

    /**
     * Sets up each menu item displaying the name of each possible mode.
     * 
     * @param mode The mode which this menu item will represent.
     * @return A menu item for the specified editor mode.
     */
    private JMenuItem setupMenuItem (Class<?> mode) {
        JMenuItem menuItem = new JMenuItem(mode.getSimpleName());
        ActionListener listener = new ModeMenuListener(mode);
        menuItem.addActionListener(listener);
        return menuItem;
    }

    /**
     * When a menu item is selected, this class will
     * automatically create the new mode, and transition
     * the level editor to the new user chosen mode.
     * 
     * @author Paul Dannenberg
     * 
     */
    private class ModeMenuListener implements ActionListener {

        private Class<?> myMode;

        /**
         * 
         * @param mode The mode that will be transitioned to
         *        if the user selects the item on the JMenu for which
         *        this class is a listener.
         */
        public ModeMenuListener (Class<?> mode) {
            myMode = mode;
        }

        /**
         * Creates the next mode and sets the level editor's
         * mode to this next mode.
         */
        @Override
        public void actionPerformed (ActionEvent e) {
            IEditorMode nextMode = instantiateMode(myMode);
            myBoard.setMode(nextMode);
        }

    }
}