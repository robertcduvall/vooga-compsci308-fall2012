package arcade.gui.panel.search;

import java.awt.Dimension;
import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class ASearchPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "search";
//    private static final int PANEL_WIDTH = 300; //300
//    private static final int PANEL_HEIGHT = 450; // 450

    public ASearchPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);

    }

    protected ArcadePanel preparePanel (ArcadePanel newPanel){
//        newPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        return newPanel;
    }
}
