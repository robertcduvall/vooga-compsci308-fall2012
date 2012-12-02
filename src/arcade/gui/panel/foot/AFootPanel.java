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
    private static final int PANEL_WIDTH = 1100;
    private static final int PANEL_HEIGHT = 50;

    public AFootPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);
    }

    
    protected ArcadePanel preparePanel (ArcadePanel newPanel){
        newPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        return newPanel;
    }
    
}
