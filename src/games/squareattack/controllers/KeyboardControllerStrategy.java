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
    private KeyboardController myKeyboardController;
    private int myType;
    private Square myCurTarget;
    

    public KeyboardControllerStrategy (Square target, JPanel compTarget, int type) {
       
        myKeyboardController = new KeyboardController(compTarget);
        myType = type;
        myKeyboardController.subscribe(this);
        setTarget(target);
    }

    public void setControls () {
        myCurTarget = getTarget();
       if(myType==WASD){
           setWASDControls();
       }
       else{
           setArrowControls();
       }

    }

    public void setArrowControls(){
        try {
            myKeyboardController.setControl(KeyEvent.VK_UP, KeyboardController.PRESSED, myCurTarget,
                                            "enableMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_DOWN, KeyboardController.PRESSED, myCurTarget,
                                            "enableMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED, myCurTarget,
                                            "enableMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED,
                                            myCurTarget, "enableMoveRight");
            myKeyboardController.setControl(KeyEvent.VK_UP, KeyboardController.RELEASED, myCurTarget,
                                            "disableMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_DOWN, KeyboardController.RELEASED,
                                            myCurTarget, "disableMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_LEFT, KeyboardController.RELEASED,
                                            myCurTarget, "disableMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_RIGHT, KeyboardController.RELEASED,
                                            myCurTarget, "disableMoveRight");
        }
        catch (NoSuchMethodException e) {

            e.printStackTrace();
        }
        catch (IllegalAccessException e) {

            e.printStackTrace();
        }
    }
    public void setWASDControls () {
        System.out.println("WASD created");
        try {
            myKeyboardController.setControl(KeyEvent.VK_W, KeyboardController.PRESSED, myCurTarget,
                                            "enableMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_S, KeyboardController.PRESSED, myCurTarget,
                                            "enableMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_A, KeyboardController.PRESSED, myCurTarget,
                                            "enableMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_D, KeyboardController.PRESSED, myCurTarget,
                                            "enableMoveRight");
            myKeyboardController.setControl(KeyEvent.VK_W, KeyboardController.RELEASED, myCurTarget,
                                            "disableMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_S, KeyboardController.RELEASED, myCurTarget,
                                            "disableMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_A, KeyboardController.RELEASED, myCurTarget,
                                            "disableMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_D, KeyboardController.RELEASED, myCurTarget,
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
        //System.out.println("typing");
    }

}
