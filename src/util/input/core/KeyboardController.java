package util.input.core;

import util.input.input_utils.BoolTuple;
import util.input.input_utils.UKeyCode;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Method;
import util.input.exceptions.InvalidControllerActionException;

public class KeyboardController extends Controller<KeyListener> implements KeyListener{

    public KeyboardController (Component comp) {
        super();
        comp.addKeyListener(this);
    }

    @Override
    public void keyTyped (KeyEvent e) {
        try {
            performReflections(e, "keyTyped", UKeyCode.codify(KeyEvent.KEY_TYPED, e.getKeyCode()));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyPressed (KeyEvent e) {
        try {
            performReflections(e, "keyPressed", UKeyCode.codify(KeyEvent.KEY_PRESSED, e.getKeyCode()));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyReleased (KeyEvent e) {
        try {
            performReflections(e, "keyReleased", UKeyCode.codify(KeyEvent.KEY_RELEASED, e.getKeyCode()));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
