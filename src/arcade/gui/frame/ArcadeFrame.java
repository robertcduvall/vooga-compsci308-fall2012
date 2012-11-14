package arcade.gui.frame;

import javax.swing.JFrame;
import arcade.gui.Arcade;

/**
 * The arcades implementation of a JFrame
 * 
 * @author Michael Deng
 *
 */
public class ArcadeFrame extends JFrame {

    private Arcade myArcade;
    
    public ArcadeFrame (Arcade a, String frameName){
        super(frameName);
        myArcade = a;
    }
    
    

    
}

