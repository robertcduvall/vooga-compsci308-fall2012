package arcade.gui.panel.logo;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * Class used to create a special arcade logo
 * 
 * @author Michael Deng
 * 
 */
public class FlynnsLogoPanel extends ALogoPanel {

    public FlynnsLogoPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        super.addLogo("arcadelogo_flynn.jpg", myPanel);
        return myPanel;
    }

}
