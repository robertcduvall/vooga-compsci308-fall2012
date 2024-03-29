package util.input.core;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import util.input.inputhelpers.UKeyCode;


/**
 * This class allows users to enter input through the keyboard.
 * 
 * @author Amay
 * 
 */
public class KeyboardController extends Controller<KeyListener> implements KeyListener {

    /**
     * Specify action occur on key pressed.
     */
    public static final int PRESSED = KeyEvent.KEY_PRESSED;

    /**
     * Specify action occur on key released.
     */
    public static final int RELEASED = KeyEvent.KEY_RELEASED;

    /**
     * Create a new keyboard controller.
     * 
     * @param comp - The component to which we add the KeyListener
     */
    public KeyboardController (Component comp) {
        super();
        comp.addKeyListener(this);
    }

    @Override
    public void keyTyped (KeyEvent e) {
        try {
            performReflections(e, "keyTyped", UKeyCode.codify(KeyEvent.KEY_TYPED, e.getKeyCode()));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }

    }

    @Override
    public void keyPressed (KeyEvent e) {
        try {
            performReflections(e, "keyPressed",
                               UKeyCode.codify(KeyEvent.KEY_PRESSED, e.getKeyCode()));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void keyReleased (KeyEvent e) {
        try {
            performReflections(e, "keyReleased",
                               UKeyCode.codify(KeyEvent.KEY_RELEASED, e.getKeyCode()));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }
}
