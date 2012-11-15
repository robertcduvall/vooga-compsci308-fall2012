package arcade.gui.panel.logo;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
public class SteamLogoPanel extends ALogoPanel {

    public SteamLogoPanel (Arcade a) {
        super(a);

    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        super.addLogo("Arcade_logo2.png", myPanel);
        return myPanel;
    }

}
