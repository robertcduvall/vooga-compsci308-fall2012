package arcade.gui.panel.foot;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class AFootPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "foot";

//    public AFootPanel (Arcade a) {
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
