package vooga.platformer.core;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import vooga.platformer.core.inputinitializer.InputInitializer;
import vooga.platformer.level.Level;
import vooga.platformer.level.LevelFactory;
import vooga.platformer.util.enums.PlayState;


@SuppressWarnings("serial")
public class PlatformerController extends JPanel implements Runnable {
    private final int SLEEP_DELAY = 25;

    private Level myCurrentLevel;
    private String myCurrentLevelName;

    // TODO: Make this variable hold a LevelFactory
    private Map<String, Point> myStringMap = new HashMap<String, Point>();
    private Dimension mySize;

    private Thread animator;

    public PlatformerController (String firstLevelName, InputInitializer ii) {

        this.setFocusable(true);

        setupLevel(firstLevelName);

        ii.setUpInput(myCurrentLevel.getObjectList(), this);

        animator = new Thread(this);
        animator.start();
    }

    /**
     * The main update cycle method.
     * 
     * @param elapsedTime
     */
    public void update (long elapsedTime) {
        myCurrentLevel.update(elapsedTime);
        PlayState currentState = myCurrentLevel.getLevelStatus();

        if (currentState == PlayState.NEXT_LEVEL || currentState == PlayState.GAME_OVER) {
            String nextLevelName = myCurrentLevel.getNextLevelName();
            setupLevel(nextLevelName);
        }
    }

    private void setupLevel (String lvlName) {
        myCurrentLevelName = lvlName;
        myCurrentLevel = LevelFactory.loadLevel(lvlName);
        Rectangle2D cameraBounds = myCurrentLevel.getCamera().getBounds();
        mySize = new Dimension((int) cameraBounds.getWidth(), (int) cameraBounds.getHeight());
        setPreferredSize(mySize);
    }

    @Override
    public Dimension getSize () {
        return mySize;
    }

    /**
     * Return the current level of this controller
     * 
     * @return
     */
    public Level getLevel () {
        return myCurrentLevel;
    }

    @Override
    public void paint (Graphics pen) {
        paintBlankScreen(pen);
        myCurrentLevel.paint(pen);
        pen.setColor(Color.BLACK);
        paintString(pen);
        for (Component c : getComponents()) {
            c.paint(pen);
        }
    }

    /**
     * Paint the background of game canvas
     * 
     * @param pen
     */
    public void paintBlankScreen (Graphics pen) {
        pen.setColor(Color.WHITE);
        pen.fillRect(0, 0, getWidth(), getHeight());

        /*
         * if (myBackground != null) {
         * pen.drawImage(myBackground.getScaledInstance((int) getWidth(),
         * (int) getHeight(), Image.SCALE_DEFAULT), 0, 0, null);
         * }
         */
    }

    private void paintString (Graphics pen) {
        for (String s : myStringMap.keySet()) {
            Point p = myStringMap.get(s);
            pen.drawString(s, p.x, p.y);
        }
    }

    /**
     * Paint string at (x,y)
     * 
     * @param str
     * @param x coordinate on canvas
     * @param y coordinate on canvas
     */
    public void paintString (String str, int x, int y) {
        Point location = new Point(x, y);
        myStringMap.put(str, location);
    }

    /**
     * The main game loop
     */
    @Override
    public void run () {
        long beforeTime, timeDiff, sleepTime;

        beforeTime = System.currentTimeMillis();

        while (true) {
            timeDiff = System.currentTimeMillis() - beforeTime;

            update(timeDiff);
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleepTime = SLEEP_DELAY - timeDiff;

            if (sleepTime < 0) {
                sleepTime = 2;
            }

            try {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e) {
                System.out.println("interrupted!");
            }
            beforeTime = System.currentTimeMillis();
        }
    }
    
    /**
     * Pause game;
     */
    public void pause(){
        myCurrentLevel.pause();
    }
    
    /**
     * Unpause current Level
     */
    public void upause(){
        myCurrentLevel.unpause();
    }
    
    /**
     * Replay current level
     */
    public void replayCurrentLevel(){
        setupLevel(myCurrentLevelName);
    }
}
