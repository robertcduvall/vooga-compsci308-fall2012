package vooga.platformer.leveleditor;

/*
 * I tried to allow LevelBoard to paint everything. Unfortunately this
 * led to messiness in an initial implementation of DrawingMode since
 * every time I needed to display something I would add it to a local
 * collection for that class's personal use and then ALSO have to add
 * it to the LevelBoard class so it could be displayed on the screen.
 * Delegation of painting to each mode also means that we can remove
 * any reference of LevelBoard from each of the different IEditorModes.
 */

/**
 * This interface should be implemented by any class which determines
 * the reaction of the level editor to user input. The implementing
 * classes are free to determine how on-screen objects are manipulated
 * by the user. Each mode must be able to paint the objects it manipulates,
 * if need be.
 * 
 * @author Paul Dannenberg
 * 
 */
public interface IEditorMode extends IPaintable {

    /**
     * Sends the IEditorMode the current cursor position.
     * 
     * @param x The x coordinates of the current cursor position.
     * @param y The y coordinates of the current cursor position.
     */
    void sendCursorPosition(int x, int y);

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
    void primaryButtonPress(int x, int y);

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
    void secondaryButtonPress(int x, int y);

}
