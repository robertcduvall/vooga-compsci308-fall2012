package games.squareattack.gui;

import games.squareattack.engine.GameManager;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Score extends GameMenuComponent {

    public int myTime = GameManager.GAME_FPS;
    public int curFrame;
    private int mySeconds = 0;
    private Rectangle myBounds;

    public Score (Rectangle bounds) {
        myBounds = bounds;
    }

    @Override
    public void paint (Graphics2D pen) {
        curFrame++;
        if (curFrame > myTime) {
            mySeconds++;
            curFrame = 0;
        }
        pen.drawString("" + mySeconds, myBounds.x, myBounds.y);

    }

    public int getScore () {
        return mySeconds;
    }

}
