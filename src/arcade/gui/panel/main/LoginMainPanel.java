package arcade.gui.panel.main;

import java.awt.Color;
import javax.swing.JPanel;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * 
 * @author Michael Deng
 * 
 */
public class LoginMainPanel extends AMainPanel {

    public LoginMainPanel (Arcade a) {
        super(a);

    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.GREEN);
        System.out.println("LoginMainPanel");
        
        
        
        
        
        
        return myPanel;
    }

}
