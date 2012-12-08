package arcade.gui.panel.logo;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * Class used to create a normal ARCADE logo
 * 
 * @author Michael Deng
 * 
 */
public class NormLogoPanel extends ALogoPanel {

    public NormLogoPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        super.addLogo("Arcade_logo.png", myPanel);
        return myPanel;
    }

}
