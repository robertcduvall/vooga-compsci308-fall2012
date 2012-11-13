package arcade.gui.panel.main;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanel;

/**
 * 
 * @author Michael Deng
 *
 */
abstract public class AMainPanel extends AbstractPanel {

    private static final String PANEL_TYPE = "main";
    
    public AMainPanel(Arcade a){
        super(a);
        super.setPanelType(PANEL_TYPE);
        
    }

    abstract protected void makeListeners ();

    
    abstract protected void addComponents (); 

    
    abstract public void refresh () ;
}
