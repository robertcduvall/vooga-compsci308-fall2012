package arcade.gui.panel.search;

import java.awt.Color;
import javax.swing.JPanel;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * 
 * @author Michael Deng
 * 
 */
public class BlankSearchPanel extends ASearchPanel {

    public BlankSearchPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        System.out.println("BlankSearchPanel");
        
        return myPanel;
    }

}
