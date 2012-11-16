package arcade.gui.panel.nav;

import java.awt.Color;
import javax.swing.JPanel;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * 
 * @author Michael Deng
 * 
 */
public class BlankNavPanel extends ANavPanel {

    public BlankNavPanel (Arcade a) {
        super(a);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.ORANGE);
        System.out.println("BlankNavPanel");
        
        return myPanel;
    }

}
