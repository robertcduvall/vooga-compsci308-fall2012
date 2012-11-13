package arcade.gui.panel.info;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanel;

/**
 * 
 * @author Michael Deng
 *
 */
abstract public class AInfoPanel extends AbstractPanel {
    
    private static final String PANEL_TYPE = "info";
    
    public AInfoPanel(Arcade a){
        super(a);
        super.setPanelType(PANEL_TYPE);
        
    }

    abstract protected void makeListeners ();

    
    abstract protected void addComponents (); 

    
    abstract public void refresh () ;
}
