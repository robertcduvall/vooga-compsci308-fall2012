package arcade.gui.panel.main;

import java.awt.Dimension;
import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class AMainPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "main";
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 450;

    public AMainPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);

    }
    
    protected ArcadePanel preparePanel (ArcadePanel newPanel){
        newPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        return newPanel;
    }

}
