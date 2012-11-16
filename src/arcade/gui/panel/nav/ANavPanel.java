package arcade.gui.panel.nav;

import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanelCreator;
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

    public ANavPanel (Arcade a) {
        super(a);
        super.setPanelType(PANEL_TYPE);

    }

//    @Override
//    public void creatorSetup (Arcade a) {
//        super.creatorSetup(a);
//        super.setPanelType(PANEL_TYPE);
//    }
}
