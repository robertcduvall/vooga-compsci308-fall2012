package vooga.platformer.leveleditor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import util.input.core.KeyboardController;
import vooga.platformer.core.inputinitializer.InputTeamPlayerAdapter;


/**
 * A class that allows keyboard input to be used to control how far the camera
 * view of the level during level editing is offset. This view accelerates the
 * longer the depressed key is held for visual effect.
 * 
 * @author Sam Rang
 * 
 */
public class ScrollingKeyInputInitializer {
    private static final int SCROLL_SPEED = 2;
    private int myAcceleration = 0;
    private LevelBoard myLevel;
    private int myOffset;

    /**
     * Creates a new ScrollingKeyListener for the specified LevelBoard.
     * 
     * @param level LevelBoard in which to use this listener
     */
    public ScrollingKeyInputInitializer (LevelBoard level) {
        myLevel = level;        
    }

    /**
     * Gets how far the view is offset from the left side of the level.
     * 
     * @return offset of the camera view as an int
     */
    public int getOffset () {
        return myOffset;
    }

    public KeyboardController getInputListener() {
        KeyboardController kc = new KeyboardController(myLevel);
        try {
            kc.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED, myLevel, "goLeft");
            kc.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED, myLevel, "goRight");
            kc.setControl(KeyEvent.VK_LEFT, KeyboardController.RELEASED, myLevel, "stop");
            kc.setControl(KeyEvent.VK_RIGHT, KeyboardController.RELEASED, myLevel, "stop");
        }
        catch (NoSuchMethodException e) {
            System.out.println("Error initializing KeyboardController: no such method");
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            System.out.println("Error initializing KeyboardController: illegal access");
            e.printStackTrace();
        }
        return kc;
    }
    
    public void goRight () {
        myOffset += SCROLL_SPEED + myAcceleration++;
        myLevel.setOffset(myOffset);
    }

    public void goLeft () {
        
        if (myOffset <= 0) {
            myOffset = 1;
            bumpLeft();
        }
        else {
            myOffset -= SCROLL_SPEED + myAcceleration++;
        }
        myLevel.setOffset(myOffset);
    }
    
    public void stop () {
            myAcceleration = 0;
    }

    public void bumpLeft () {
        System.out.println("Already at beginning!");
        // TODO: add code to animate bumping motion (if there's time, there
        // never is)
    }
}
