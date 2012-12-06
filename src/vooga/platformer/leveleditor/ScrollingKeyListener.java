package vooga.platformer.leveleditor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * A class that allows keyboard input to be used to control how far the camera
 * view of the level during level editing is offset. This view accelerates the
 * longer the depressed key is held for visual effect.
 * 
 * @author Sam Rang
 * 
 */
public class ScrollingKeyListener extends KeyAdapter {
    private static final int SCROLL_SPEED = 2;
    private int myAcceleration = 0;
    private LevelBoard myLevel;
    private int myOffset;

    /**
     * Creates a new ScrollingKeyListener for the specified LevelBoard.
     * 
     * @param level LevelBoard in which to use this listener
     */
    public ScrollingKeyListener (LevelBoard level) {
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

    @Override
    public void keyPressed (KeyEvent ke) {
        if ((ke.getKeyCode() == KeyEvent.VK_LEFT) || (ke.getKeyCode() == KeyEvent.VK_RIGHT)) {
            myAcceleration++;
        }
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (myOffset <= 0) {
                    myOffset = 1;
                    bumpLeft();
                }
                else {
                    myOffset -= SCROLL_SPEED + myAcceleration;
                }
                break;
            case KeyEvent.VK_RIGHT:
                myOffset += SCROLL_SPEED + myAcceleration;
                break;
            default:
                /*
                 * Do nothing
                 */
        }
        myLevel.setOffset(myOffset);
    }

    @Override
    public void keyReleased (KeyEvent ke) {
        if ((ke.getKeyCode() == KeyEvent.VK_LEFT) || (ke.getKeyCode() == KeyEvent.VK_RIGHT)) {
            myAcceleration = 0;
        }
    }

    private void bumpLeft () {
        System.out.println("Already at beginning!");
        // TODO: add code to animate bumping motion (if there's time, there
        // never is)
    }
}
