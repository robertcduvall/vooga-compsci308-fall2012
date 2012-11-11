package vooga.platformer.core;

import java.awt.Graphics;
import javax.swing.JPanel;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.PlayState;

public class Controller extends JPanel implements Runnable {
    private final int SLEEP_DELAY = 25;
    
    private Level myCurrentLevel;
    
    private LevelFactory myLevelFactory;
    private GameInitializer myGameInitializer;
    
    public Controller(LevelFactory lf, GameInitializer gi) {
        myLevelFactory = lf;
        myGameInitializer = gi;
        
        myCurrentLevel = myLevelFactory.loadLevel(myGameInitializer.getFirstLevel());
    }
    
    /**
     * The main update cycle method.
     * @param elapsedTime
     */
    public void update(long elapsedTime) {
        myCurrentLevel.update(elapsedTime);
        PlayState currentState = myCurrentLevel.getLevelStatus();
        
        if (currentState == PlayState.NEXT_LEVEL) {
            String nextLevelName = myCurrentLevel.getNextLevelName();
            Level nextLevel = myLevelFactory.loadLevel(nextLevelName);
        }
    }
    
    public void paint(Graphics pen) {
        myCurrentLevel.paint(pen);
    }
    
    /**
     * The main game loop
     */
    public void run() {
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
                catch(InterruptedException e) {
                        System.out.println("interrupted!");
                }
                beforeTime = System.currentTimeMillis();
        }
}
}
