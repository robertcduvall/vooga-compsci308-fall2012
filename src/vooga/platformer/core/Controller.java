package vooga.platformer.core;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Controller extends JPanel implements Runnable {
    private final int SLEEP_DELAY = 25;
    
    private Level myCurrentLevel;
    
    /**
     * The main update cycle method.
     * @param elapsedTime
     */
    public void update(long elapsedTime) {
        myCurrentLevel.update(elapsedTime);
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