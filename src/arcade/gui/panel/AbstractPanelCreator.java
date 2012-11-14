package arcade.gui.panel;

import javax.swing.JPanel;
import arcade.gui.Arcade;


/**
 * The is the top-level abstract class for the panel hierarchy.
 * 
 * @author Michael Deng
 * 
 */
public abstract class AbstractPanelCreator {

    private String myPanelType;
    private Arcade myArcade;

    public AbstractPanelCreator (Arcade a) {
        myArcade = a;
    }

    abstract public JPanel createPanel ();

    public String getPanelType () {
        return myPanelType;
    }

    protected void setPanelType (String panelType) {
        myPanelType = panelType;
    }

    protected Arcade getArcade () {
        return myArcade;
    }

}
