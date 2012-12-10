package arcade.gui.panel;

import javax.swing.JPanel;
import arcade.gui.Arcade;


/**
 * The arcades implementation of JPanel
 * 
 * @author Michael Deng
 * 
 */
public class ArcadePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private String myPanelType;
    private Arcade myArcade;

    /**
     * Constructor for the ArcadePanel
     * 
     * @param a reference to the arcade
     * @param panelType the type of panel this is
     */
    public ArcadePanel (Arcade a, String panelType) {
        myArcade = a;
        myPanelType = panelType;
    }

    /**
     * Getter method the the panel's type
     * 
     * @return the panel's type
     */
    public String getPanelType () {
        return myPanelType;
    }

    /**
     * Getter method for reference to the arcade
     * 
     * @return reference to the arcade
     */
    public Arcade getArcade () {
        return myArcade;
    }

}
