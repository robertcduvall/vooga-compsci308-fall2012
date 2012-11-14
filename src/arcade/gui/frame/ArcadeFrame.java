package arcade.gui.frame;

import javax.swing.JFrame;
import arcade.gui.Arcade;

/**
 * 
 * @author Michael
 *
 */
public class ArcadeFrame extends JFrame {

    private Arcade myArcade;
    
    public ArcadeFrame (Arcade a, String frameName){
        super(frameName);
        myArcade = a;
    }
    
    

    
}

