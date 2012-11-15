package arcade.gui.panel.search;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class ASearchPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "search";

    public ASearchPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);

    }

    
//    @Override
//    public void creatorSetup (Arcade a) {
//        super.creatorSetup(a);
//        super.setPanelType(PANEL_TYPE);
//    }
}
