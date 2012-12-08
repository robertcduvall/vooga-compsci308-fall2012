package games.squareattack.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import util.input.core.KeyboardController;
import games.squareattack.sprites.Square;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class KeyboardControllerStrategy extends ControllerStrategy implements KeyListener {

    public static final int WASD = 1;
    public static final int ARROWS = 2;
    private Square myTarget;
    private KeyboardController myKeyboardController;

    public KeyboardControllerStrategy (Square target, JPanel compTarget, int type) {
        myTarget = target;
        myKeyboardController = new KeyboardController(compTarget);
        if (type == WASD) {
            setWASDControls();

        }
        else {
            setControls();
        }
        myKeyboardController.subscribe(this);
    }

    @Override
    public void setControls () {
        try {
            myKeyboardController.setControl(KeyEvent.VK_UP, KeyboardController.PRESSED, myTarget,
                                            "enableMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_DOWN, KeyboardController.PRESSED, myTarget,
                                            "enableMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED, myTarget,
                                            "enableMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED,
                                            myTarget, "enableMoveRight");
            myKeyboardController.setControl(KeyEvent.VK_UP, KeyboardController.RELEASED, myTarget,
                                            "disableMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_DOWN, KeyboardController.RELEASED,
                                            myTarget, "disableMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_LEFT, KeyboardController.RELEASED,
                                            myTarget, "disableMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_RIGHT, KeyboardController.RELEASED,
                                            myTarget, "disableMoveRight");
        }
        catch (NoSuchMethodException e) {

            e.printStackTrace();
        }
        catch (IllegalAccessException e) {

            e.printStackTrace();
        }

    }

    public void setWASDControls () {
        try {
            myKeyboardController.setControl(KeyEvent.VK_W, KeyboardController.PRESSED, myTarget,
                                            "enableMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_S, KeyboardController.PRESSED, myTarget,
                                            "enableMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_A, KeyboardController.PRESSED, myTarget,
                                            "enableMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_D, KeyboardController.PRESSED, myTarget,
                                            "enableMoveRight");
            myKeyboardController.setControl(KeyEvent.VK_W, KeyboardController.RELEASED, myTarget,
                                            "disableMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_S, KeyboardController.RELEASED, myTarget,
                                            "disableMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_A, KeyboardController.RELEASED, myTarget,
                                            "disableMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_D, KeyboardController.RELEASED, myTarget,
                                            "disableMoveRight");
        }
        catch (NoSuchMethodException e) {

            e.printStackTrace();
        }
        catch (IllegalAccessException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void keyPressed (KeyEvent arg0) {

    }

    @Override
    public void keyReleased (KeyEvent arg0) {

    }

    @Override
    public void keyTyped (KeyEvent arg0) {

    }

}
