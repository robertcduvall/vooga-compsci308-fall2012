package vooga.shooter.graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.Timer;

/**
 * 
 * @author David Spruill
 *
 */
public class Canvas extends JApplet{
    
    /**
     * Initializes the applet --- called by the browser.
     */
    public void init()
    {
        
    }
    
    /**
     * Starts the applet's action, i.e., starts the animation.
     */
    @Override
    public void start ()
     {
        
        // create a timer to animate the canvas
        myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND, 
            new ActionListener()
            {
                @Override
                public void actionPerformed (ActionEvent e)
                {
                    myGame.update();
                    // indirectly causes paint to be called
                    repaint();
                }
            });
        myTimer.start();
    }


    /**
     * Stops the applet's action, i.e., the animation.
     */
    @Override
    public void stop ()
    {
        myTimer.stop();
    }
    
    /**
     * Called by Java to paint the frame
     * 
     * @param g The graphics object passed by Java, will be cast as SpecialGraphics
     */
    public void paint(Graphics g)
    {
        
    }
    
    /**
     * Called at regular intervals set up by the action listener instantiated in the start method.
     */
    public void update()
    {
        
    }
}
