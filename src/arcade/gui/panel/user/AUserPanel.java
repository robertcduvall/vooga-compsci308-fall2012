package arcade.gui.panel.user;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class AUserPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "user";

    public AUserPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);

    }

//    @Override
//    public void creatorSetup (Arcade a) {
//        super.creatorSetup(a);
//        super.setPanelType(PANEL_TYPE);
//    }
}
