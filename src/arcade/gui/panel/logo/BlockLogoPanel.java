package arcade.gui.panel.logo;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * Class used to create a special arcade logo
 * 
 * @author Michael Deng
 * 
 */
public class BlockLogoPanel extends ALogoPanel {

    public BlockLogoPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        super.addLogo("arcadelogo_block.jpg", myPanel);
        return myPanel;
    }

}
