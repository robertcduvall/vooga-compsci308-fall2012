package arcade.gui.panel.logo;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * Used to create a STEAM logo
 * 
 * @author Michael Deng
 * 
 */
public class SteamLogoPanel extends ALogoPanel {

    /**
     * 
     * @param a arcade
     */
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
