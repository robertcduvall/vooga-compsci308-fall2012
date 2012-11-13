package arcade.gui.panel.nav;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanel;

/**
 * 
 * @author Michael Deng
 *
 */
abstract public class ANavPanel extends AbstractPanel {

    private static final String PANEL_TYPE = "nav";
    
    public ANavPanel(Arcade a){
        super(a);
        super.setPanelType(PANEL_TYPE);
        
    }

    abstract protected void makeListeners ();

    
    abstract protected void addComponents (); 

    
    abstract public void refresh () ;
    
}
