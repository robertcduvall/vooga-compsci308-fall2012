package arcade.gui.panel;

import javax.swing.JPanel;
import arcade.gui.Arcade;


/**
 * The is the top-level abstract class for the panel hierarchy.
 * 
 * @author Michael Deng
 * 
 */
public abstract class AbstractPanel extends JPanel {
    
    private String myPanelType;
    private Arcade myArcade;

    public AbstractPanel (Arcade a) {

        makeListeners();
        addComponents();
    }

    abstract protected void addComponents ();

    abstract protected void makeListeners ();
    
    abstract public void refresh();

    public String getPanelType(){
        return myPanelType;
    }
    
    protected void setPanelType(String panelType){
        myPanelType = panelType;
    }
    
    protected Arcade getArcade(){
        return myArcade;
    }
    
    
}
