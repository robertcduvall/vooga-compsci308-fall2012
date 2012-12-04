package vooga.platformer.leveleditor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ScrollingKeyListener extends KeyAdapter{
    private static final int SCROLL_SPEED = 2;
    private int accel = 0;
    private LevelBoard myLevel;
    private int myOffset;

    public ScrollingKeyListener (LevelBoard level) {
        myLevel = level;
    }

    public int getOffset() {
        return myOffset;
    }
    @Override
    public void keyPressed (KeyEvent ke) {
        if ((ke.getKeyCode() == KeyEvent.VK_LEFT) || (ke.getKeyCode() == KeyEvent.VK_RIGHT)) {
            accel++;
        }
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (myOffset == 0) {
                    bumpLeft();
                }
                else {
                    myOffset -= SCROLL_SPEED + accel;
                }
                break;
            case KeyEvent.VK_RIGHT:
                myOffset += SCROLL_SPEED + accel;
        }
        myLevel.setOffset(myOffset);
    }

    @Override
    public void keyReleased (KeyEvent ke) {
        if ((ke.getKeyCode() == KeyEvent.VK_LEFT) || (ke.getKeyCode() == KeyEvent.VK_RIGHT)) {
            accel = 0;
        }
    }

    private void bumpLeft () {
        System.out.println("Already at beginning!");
        // TODO: add code to animate bumping motion (if there's time, there never is)
    }
}
