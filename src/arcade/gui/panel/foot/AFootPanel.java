package arcade.gui.panel.foot;

import java.awt.Dimension;
import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class AFootPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "foot";
    
    public AFootPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);
    }

    protected ArcadePanel preparePanel (ArcadePanel newPanel) {
        // nothing
        return newPanel;
    }

}
