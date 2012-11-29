package arcade.gui.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    private class WindowExitAdapter extends WindowAdapter {

        @Override
        public void windowClosing (WindowEvent e) {
            myArcade.setUsername("");
            System.out.println("username set to null");
            System.exit(0);
        }
    }
}
