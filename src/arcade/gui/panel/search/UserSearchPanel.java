package arcade.gui.panel.search;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

public class UserSearchPanel extends ASearchPanel {

    public UserSearchPanel (Arcade a) {
        super(a);      
    }
    
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
    
        return myPanel;
    }
}
