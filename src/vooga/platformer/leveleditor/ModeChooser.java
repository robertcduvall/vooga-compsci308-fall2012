package vooga.platformer.leveleditor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import util.projectsearcher.SubClassFinder;
import vooga.platformer.leveleditor.leveldrawer.IEditorObject;


public class ModeChooser {

    private static final Class<?> INSTANTIATION_PARAMETER_TYPE = Collection.class;

    //REMEMBER TO SUPPORT PROPER TRANSITIONS!!
    //IF EXCEPTION OCCURS JUST CALL DEFAULT CONSTRUCTOR.
    private IEditorMode myCurrent;
    private LevelBoard myBoard;
    private Collection<IEditorObject> mySaved;
    private JMenu myMenu;

    public ModeChooser () throws IllegalArgumentException, InstantiationException,
                         IllegalAccessException, InvocationTargetException {
        myCurrent = new PlacementMode();
        myMenu = new JMenu();
        Collection<Class<?>> modes = getDifferentModes();
        setupMenu(modes, myMenu);
    }

    private Collection<Class<?>> getDifferentModes () {
        SubClassFinder classFinder = new SubClassFinder();
        return classFinder.getSubClasses(IEditorMode.class);
    }

    private void setupMenu (Collection<Class<?>> toInstantiate, JMenu menu)
                                                                           throws IllegalArgumentException,
                                                                           InstantiationException,
                                                                           IllegalAccessException,
                                                                           InvocationTargetException {
        List<IEditorMode> editorModes = getEditorModes(toInstantiate);
        for (IEditorMode mode : editorModes) {
            menu.add(setupMenuItem(mode));
        }

    }

    private JMenuItem setupMenuItem (IEditorMode mode) {
        JMenuItem menuItem = new JMenuItem(mode.getClass().getSimpleName());
        MenuKeyListener listener = new ModeMenuListener(mode);
        menuItem.addMenuKeyListener(listener);
        return menuItem;
    }

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
            myBoard.setMode(myMode);
        }

        @Override
        public void menuKeyTyped (MenuKeyEvent e) {
        }

    }

    private List<IEditorMode> getEditorModes (Collection<Class<?>> modeClasses)
                                                                               throws IllegalArgumentException,
                                                                               InstantiationException,
                                                                               IllegalAccessException,
                                                                               InvocationTargetException {
        List<IEditorMode> editorModes = new ArrayList<IEditorMode>();
        for (Class<?> mode : modeClasses) {
            editorModes.add(createEditorMode(mode));
        }
        return editorModes;
    }

    private IEditorMode createEditorMode (Class<?> editorOption) throws IllegalArgumentException,
                                                                InstantiationException,
                                                                IllegalAccessException,
                                                                InvocationTargetException {
        IEditorMode mode = null;
        Constructor<?>[] possibleConstructors = editorOption.getDeclaredConstructors();
        for (Constructor<?> constructor : possibleConstructors) {
            if (isCorrectConstructor(constructor)) {
                mode = (IEditorMode) constructor.newInstance(mySaved);

            }
        }
        return mode;

    }

    private boolean isCorrectConstructor (Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        for (Class<?> classType : parameterTypes) {
            if (classType.equals(INSTANTIATION_PARAMETER_TYPE)) { return true; }
        }
        return false;
    }
}