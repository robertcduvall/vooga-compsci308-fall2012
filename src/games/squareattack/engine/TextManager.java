package games.squareattack.engine;

import games.squareattack.gui.Score;
import java.awt.Graphics2D;
import java.awt.Point;


public class TextManager {

    private OnScreenText myOnScreenText = new OnScreenText();
    private GameManager myManager;
    private int counter = GameManager.GAME_FPS * 10;
    private boolean myGameOver;

    public TextManager (GameManager manager, boolean gameOver, Score score) {
        myGameOver = gameOver;
        if (!gameOver) {
            myOnScreenText.addText("The game will begin in ten seconds! connect your controllers!",
                                   10 * GameManager.GAME_FPS, new Point(100, 100), 24);
        }
        else {
            myOnScreenText
                    .addText("The defender has been killed... I hope you feel good about yourselves",
                             10 * GameManager.GAME_FPS, new Point(100, 100), 24);
            myOnScreenText.addText("He lasted " + score.getScore() + " seconds.",
                                   10 * GameManager.GAME_FPS, new Point(100, 200), 24);
            myOnScreenText
                    .addText("A new game will start in 20 seconds, trade controllers to mix things up.",
                             10 * GameManager.GAME_FPS, new Point(100, 300), 24);
        }
        myManager = manager;
    }

    public void paint (Graphics2D pen) {
        if (!myGameOver) {
            addCountDown();
        }
        myOnScreenText.drawText(pen);
        if (myOnScreenText.getSize() == 0) {
            if (myGameOver) {
                myManager.startCountdown();
            }
            else {
                myManager.setState(GameManager.State.Running);
            }

        }

    }

    private void addCountDown () {

        if (counter % GameManager.GAME_FPS == 0) {
            int time = counter / GameManager.GAME_FPS;
            if (time == 0) {
                myOnScreenText.addText("GO!!!", 3 * GameManager.GAME_FPS, new Point((int) (400),
                                                                                    (int) (300)),
                                       100);
            }
            else if (time > 0) {
                myOnScreenText.addText(time + "", GameManager.GAME_FPS, new Point((int) (500),
                                                                                  (int) (400)), 50);
            }
        }
        counter--;

    }

}
