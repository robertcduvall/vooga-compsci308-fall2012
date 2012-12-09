package arcade.gui.panel.nav;

import java.awt.Dimension;
import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;
import arcade.gui.panel.ArcadePanel;
import edu.cmu.relativelayout.Binding;
import edu.cmu.relativelayout.BindingFactory;
import edu.cmu.relativelayout.RelativeConstraints;
import edu.cmu.relativelayout.RelativeLayout;

/**
 * 
 * @author Michael Deng
 * 
 */
abstract public class ANavPanel extends AbstractPanelCreator {

    private static final String PANEL_TYPE = "nav";
//    private static final int PANEL_WIDTH = 1100;
//    private static final int PANEL_HEIGHT = 50;

    public ANavPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);

    }
    
    protected ArcadePanel preparePanel (ArcadePanel newPanel){
//        newPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        return newPanel;
    }


}
