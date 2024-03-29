package vooga.platformer.leveleditor;

import java.util.Collection;
import vooga.platformer.leveleditor.leveldrawer.IEditorObject;


/*
 * I initially tried to allow LevelBoard to paint everything. Unfortunately this
 * led to messiness in an initial implementation of DrawingMode since
 * every time I needed to display something I would add it to a local
 * collection for that class's personal use and then ALSO have to add
 * it to the LevelBoard class so it could be displayed on the screen.
 * Delegation of painting to each mode also means that we can remove
 * any reference of LevelBoard from each of the different IEditorModes.
 * Therefore, I've made this interface extend IPaintble so each manager
 * now takes care of what needs to be painted.
 */

/**
 * This interface should be implemented by any class which determines
 * the reaction of the level editor to user input. The implementing
 * classes are free to determine how on-screen objects are manipulated
 * by the user. Each mode must be able to paint the objects it manipulates,
 * if need be.
 * 
 * There are two scenarios that you may encounter if you want to get a
 * class that implements this interface to work with the rest of the
 * level editor.
 * 
 * 1) If the implementing class uses a constructor that takes
 * particular parameters, you must add some instantiation logic to the
 * editor's factory (the ModeChooser class).
 * 
 * 2) If the class that implements
 * this interface has a parameterless constructor then nothing whatsoever
 * needs to be done to get the level editor to work with your new class.
 * The factory will automatically find it, offer the user an option to
 * switch the editor to the new mode and will switch to it if the user
 * selects it.
 * 
 * @author Paul Dannenberg
 * 
 */
public interface IEditorMode extends IPaintable {

    /**
     * Sends the IEditorMode the current cursor position. This
     * method will be called by the level editor once per frame.
     * This means that every frame, the implementing class will
     * receive the location of the user's cursor. It is up to
     * this implementing class to decide what to do with this (if
     * anything).
     * 
     * @param x The x coordinates of the current cursor position.
     * @param y The y coordinates of the current cursor position.
     */
    void sendCursorPosition (int x, int y);

    /**
     * Alerts the IEditorMode that a button has been pressed
     * by the user. It is the implementing class's responsibility
     * to determine how it should react.
     * 
     * @param x The coordinates of the cursor on the screen at the
     *        time of the button press.
     * @param y The coordinates of the cursor on the screen at the
     *        time of the button press.
     */
    void primaryButtonPress (int x, int y);

    /**
     * Alerts the IEditorMode that a button has been pressed
     * by the user. It is the implementing class's responsibility
     * to determine how it should react.
     * 
     * @param x The coordinates of the cursor on the screen at the
     *        time of the button press.
     * @param y The coordinates of the cursor on the screen at the
     *        time of the button press.
     */
    void secondaryButtonPress (int x, int y);

    /**
     * Adds an object to the current mode.
     * 
     * @param editorObject The editor object that will be added to
     *        the current mode.
     */
    void add (IEditorObject editorObject);

    /**
     * Gets the editor objects from a current editor mode. This method
     * is needed to ensure transitions between modes, since objects
     * should not be lost between transitions.
     * 
     * @return
     */
    Collection<IEditorObject> getEditorObjects ();

}
