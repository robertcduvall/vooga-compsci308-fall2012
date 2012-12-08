package vooga.turnbased.gamecore;

import java.awt.Graphics;

/**
 * Game loop works by sequential calls of update and paint
 * down the class hierarchy.
 * 
 * @author volodymyr
 *
 */
public interface GameLoop {

    /**
     * Update game loop member every cycle
     */
    public void update ();

    /**
     * Paint game loop member every cycle after updating.
     * 
     * @param g Graphics
     */
    public void paint (Graphics g);
}
