package arcade.gui.panel;

import java.awt.Color;
import arcade.gui.Arcade;


/**
 * The is the top-level abstract class for the panelcreator hierarchy.
 * 
 * @author Michael Deng
 * 
 */
public abstract class AbstractPanelCreator {

    private String myPanelType;
    private Arcade myArcade;

    /**
     * Constructor fo the AbstractPanelCreator
     * 
     * @param a reference to the arcade
     */
    public AbstractPanelCreator (Arcade a) {
        myArcade = a;
    }

    /**
     * Used by subclasses as the first step in creating a new ArcadePanel.
     * This method create the new arcadepanel and initializes it
     * for the arcade.
     * 
     * @return a new ArcadePanel
     */
    protected ArcadePanel initializeNewPanel () {
        ArcadePanel newPanel = new ArcadePanel(myArcade, myPanelType);
        newPanel.setBackground(Color.BLACK);
        newPanel.setPreferredSize(null);
        return preparePanel(newPanel);
    }

    /**
     * Implement this with all methods that are common to all the
     * panels of this type. This method further prepares the panel
     * for the arcade.
     * 
     * @param newPanel the arcadepanel
     * @return an arcadepanel
     */
    abstract protected ArcadePanel preparePanel (ArcadePanel newPanel);

    /**
     * Call this method to have this panel creator create you a new panel
     * 
     * @return ArcadePanel (special form of JPanel)
     */
    abstract public ArcadePanel createPanel ();

    /**
     * Allows subclasses to set the panel type
     * 
     * @param panelType the panel type as a string
     */
    protected void setPanelType (String panelType) {
        myPanelType = panelType;
    }

    /**
     * Allows subclasses to get reference to the Arcade
     * 
     * @return reference to the Arcade
     */
    protected Arcade getArcade () {
        return myArcade;
    }

}
