package arcade.gui.panel.logo;

import java.awt.Color;
import javax.swing.JPanel;
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
        // TODO Auto-generated constructor stub
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.BLUE);
        System.out.println("NormLogoPanel");
        
        return myPanel;
    }

}