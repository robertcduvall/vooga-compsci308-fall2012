package arcade.gui.panel.logo;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * Class used to create a special arcade logo
 * 
 * @author Michael Deng
 * 
 */
public class RedLogoPanel extends ALogoPanel {

    public RedLogoPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        super.addLogo("arcadelogo_red.jpg", myPanel);
        return myPanel;
    }

}
