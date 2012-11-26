package arcade.gui.panel.main;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
public class BlankMainPanel extends AMainPanel {

    public BlankMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        System.out.println("BlankMainPanel");

        return myPanel;
    }

}
