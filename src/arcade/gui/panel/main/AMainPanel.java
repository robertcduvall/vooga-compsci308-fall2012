package arcade.gui.panel.main;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class AMainPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "main";

//    public AMainPanel (Arcade a) {
//        super(a);
//        super.setPanelType(PANEL_TYPE);
//
//    }

    
    @Override
    public void creatorSetup (Arcade a) {
        super.creatorSetup(a);
        super.setPanelType(PANEL_TYPE);
    }
}
