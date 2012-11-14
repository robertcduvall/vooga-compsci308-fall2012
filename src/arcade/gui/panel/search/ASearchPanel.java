package arcade.gui.panel.search;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanel;

/**
 * 
 * @author Michael Deng
 *
 */
abstract public class ASearchPanel extends AbstractPanel {
    
    private static final String PANEL_TYPE = "search";
    
    public ASearchPanel(Arcade a){
        super(a);
        super.setPanelType(PANEL_TYPE);
        
    }

    abstract protected void makeListeners ();

    
    abstract protected void addComponents (); 

    
    abstract public void refresh () ;

}
