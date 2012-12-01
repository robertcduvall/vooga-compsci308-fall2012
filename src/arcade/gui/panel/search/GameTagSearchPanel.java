package arcade.gui.panel.search;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

public class GameTagSearchPanel extends ASearchPanel {

    public GameTagSearchPanel (Arcade a) {
        super(a);      
    }
    
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
    
        return myPanel;
    }
}