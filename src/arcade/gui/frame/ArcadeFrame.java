package arcade.gui.frame;

import java.util.Map;
import javax.swing.JFrame;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * The arcades implementation of a JFrame
 * 
 * @author Michael Deng
 * 
 */
public class ArcadeFrame extends JFrame {

    private Arcade myArcade;
    private Map<String, ArcadePanel> myPanels;

    public ArcadeFrame (Arcade a, String frameName, Map<String, ArcadePanel> m) {
        super(frameName);
        myArcade = a;
        myPanels = m;
        System.out.println("main FRAME created");
    }

    /**
     * Method to return a 'holder' panel. This panel is one of
     * the panels in the frame. You can put things in this panel.
     * 
     * @param panelType the name of the holder panel
     * @return
     */
    public ArcadePanel getPanel (String panelType) {
        if (myPanels.containsKey(panelType)) {
            return myPanels.get(panelType);
        }
        else {
            return null;
        }
    }

}
