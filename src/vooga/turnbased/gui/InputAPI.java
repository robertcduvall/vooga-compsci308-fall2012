package vooga.turnbased.gui;

/**
 * classes that need to use input API should implement this interface
 * 
 * run configureInputHandling() every time event handlers need to be reset
 * (e.g. after switching into new game mode)
 * 
 * example:
 * public void configureInputHandling() {
 * GamePane.keyboardController.setControl(KeyEvent.VK_LEFT,
 * KeyboardController.PRESSED, myPlayer, "moveLeft");
 * }
 * 
 * @author Volodymyr
 */
public interface InputAPI {
    public void configureInputHandling ();
}
