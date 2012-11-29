package arcade.gui.panel.foot;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * 
 * @author Michael Deng
 *
 */
public class BlankFootPanel extends AFootPanel {

    public BlankFootPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        
        return myPanel;
    }

}
