package arcade.gui.panel.logo;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class ALogoPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "logo";

    public ALogoPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);

    }

//    @Override
//    public void creatorSetup (Arcade a) {
//        super.creatorSetup(a);
//        super.setPanelType(PANEL_TYPE);
//    }
    
}
