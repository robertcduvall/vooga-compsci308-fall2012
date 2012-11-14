package arcade.gui.frame;

import java.util.Map;
import javax.swing.JFrame;
import arcade.gui.panel.AbstractPanel;


/**
 * This is the abstract class for the frame hierarchy.
 * All frames in our GUI will implement this abstract class.
 * 
 * @author Michael Deng
 * 
 */
public class AbstractFrame extends JFrame {

    private Map<String, AbstractPanel> myPanels;

    public AbstractFrame () {

    }

    /*
     * Creating new/different frames will allow users to implement different
     * layouts. And give each of the frames different properties.
     * 
     * All frames must have space for six canvases. The Layout can vary.
     * We have decided on sex because that is how many we need now; ideally,
     * not other implementation would need as many as six.
     */

    /**
     * This method returns the panel specified by the panel number.
     * 
     * @param panelNumber the int representing the panel
     * @return this returns the panel specified
     */
    public AbstractPanel getPanel (String panelNumber) {
        return myPanels.get(panelNumber);
    }

    /**
     * This method replaces and old panel with a new panel.
     * 
     * @param panelNumber the int representing the panel to be replaced
     * @param newPanel the replacement panel
     * @return this returns the old panel
     */
    public AbstractPanel replacePanel (String panelNumber, AbstractPanel newPanel) {
        AbstractPanel oldPanel = myPanels.get(panelNumber);
        myPanels.put(panelNumber, newPanel);
        return oldPanel;
    }

    /**
     * This method refreshes (reloads all the panels into the content pane)
     * the frame. It also causes each panel to refresh (panel will reload data
     * and components).
     */
    public void refreshAll () {
        for (String p : myPanels.keySet()){
            myPanels.get(p).refresh();
        }
    }

}
