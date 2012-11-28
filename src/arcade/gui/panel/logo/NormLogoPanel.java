package arcade.gui.panel.logo;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
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
