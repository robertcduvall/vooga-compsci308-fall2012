package arcade.gui.panel.nav;

import java.awt.Color;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
public class BlankNavPanel extends ANavPanel {

    public BlankNavPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        
        return myPanel;
    }

}
