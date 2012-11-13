package arcade.gui.panel.user;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanel;

/**
 * 
 * @author Michael Deng
 *
 */
abstract public class AUserPanel extends AbstractPanel {
    
    private static final String PANEL_TYPE = "user";
    
    public AUserPanel(Arcade a){
        super(a);
        super.setPanelType(PANEL_TYPE);
        
    }

    abstract protected void makeListeners ();

    
    abstract protected void addComponents (); 

    
    abstract public void refresh () ;

    

}
