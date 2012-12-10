package arcade.gui.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.JFrame;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * The arcade's implementation of a JFrame. It has the
 * added ability to store (in a map) reference to the
 * panel holders in the frame.
 * 
 * @author Michael Deng
 * 
 */
public class ArcadeFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private Arcade myArcade;
    private Map<String, ArcadePanel> myPanels;

    /**
     * Constructor for an ArcadeFrame
     * 
     * @param a reference to the arcade
     * @param frameName name of this ArcadeFrame
     * @param m map of references to the frame's holder panels
     */
    public ArcadeFrame (Arcade a, String frameName, Map<String, ArcadePanel> m) {
        super(frameName);
        myArcade = a;
        myPanels = m;

        addWindowListener(new WindowExitAdapter() {
        });
    }

    /**
     * Method to return a 'holder' panel. This panel is one of
     * the holder panels in the frame. You can place place things in this
     * panel. To do so, first clear the panel, and then add the new
     * component.
     * 
     * @param panelType the name of the holder panel
     * @return a reference to the specified holder panel
     */
    public ArcadePanel getPanel (String panelType) {
        if (myPanels.containsKey(panelType)) {
            return myPanels.get(panelType);
        }
        else {
            return null;
        }
    }

    /**
     * Class responsible for managing the closing
     * of the ArcadeFrame window. When the window closes,
     * we want to clear the saved username and exit
     * the process.
     * 
     * @author Michael Deng
     * 
     */
    private class WindowExitAdapter extends WindowAdapter {

        @Override
        public void windowClosing (WindowEvent e) {
            myArcade.setUsername("");
            System.out.println("Preparing to Exit...");
            System.exit(0);
        }
    }
}
