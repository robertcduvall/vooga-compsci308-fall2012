package vooga.shooter.graphics;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;


/**
 * An interface to be implemented by the different classes that should be drawn,
 * like the level editor and the game itself
 * 
 * @author David Spruill
 * 
 */
public interface DrawableComponent {
    
    public void update ();

    public void paint (Graphics g);
    
    public void setKeyboardListener(KeyListener keyListener);
    
    public void setMouseListener(MouseMotionListener mouseMotionListener);
}
