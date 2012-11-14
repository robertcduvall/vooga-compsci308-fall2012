package util.input.core;

import java.awt.Component;
import java.awt.event.KeyEvent;


/**
 * This class allows users to enter input through the keyboard
 * 
 * @author Amay
 * 
 */
public class KeyboardController extends Controller<KeyListener> implements
        KeyListener {

    /**
     * Create a new keyboard controller
     * 
     * @param comp - The component to which we add the KeyListener
     */
    public KeyboardController(Component comp) {
        super();
        comp.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        try {
            performReflections(e, "keyTyped",
                    UKeyCode.codify(KeyEvent.KEY_TYPED, e.getKeyCode()));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            performReflections(e, "keyPressed",
                    UKeyCode.codify(KeyEvent.KEY_PRESSED, e.getKeyCode()));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            performReflections(e, "keyReleased",
                    UKeyCode.codify(KeyEvent.KEY_RELEASED, e.getKeyCode()));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
