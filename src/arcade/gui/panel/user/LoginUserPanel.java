package arcade.gui.panel.user;

import java.awt.Color;
import javax.swing.JPanel;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * This is the user panel during login.
 * It has 2 buttons: Login and New User
 * 
 * @author Michael Deng
 *
 */
public class LoginUserPanel extends AUserPanel {

    public LoginUserPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.BLUE);
        System.out.println("LoginUserPanel");
        
        return myPanel;
    }

}
