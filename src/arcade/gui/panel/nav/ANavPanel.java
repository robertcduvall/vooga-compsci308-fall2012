package arcade.gui.panel.nav;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class ANavPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "nav";

//    public ANavPanel (Arcade a) {
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
