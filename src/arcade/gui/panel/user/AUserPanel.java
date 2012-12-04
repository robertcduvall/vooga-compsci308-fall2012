package arcade.gui.panel.user;

import java.awt.Dimension;
import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class AUserPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "user";
//    private static final int PANEL_WIDTH = 250;
//    private static final int PANEL_HEIGHT = 80;

    public AUserPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);

    }

    protected ArcadePanel preparePanel (ArcadePanel newPanel){
//        newPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        
//        System.out.println(newPanel.getPanelType());
//        System.out.println(getArcade().getPanelSize(newPanel));
        
        
//        newPanel.setPreferredSize(getArcade().getPanelSize(newPanel)); // uncomment
//        System.out.println(newPanel.getPreferredSize());
        return newPanel;
    }
}
