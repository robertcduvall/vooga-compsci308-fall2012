package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import edu.cmu.relativelayout.Direction;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.components.UserListComponent;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.UserProfile;


/**
 * 
 * @author Robert Bruce
 * 
 */
public class UserListMainPanel extends AMainPanel {

    /**
     * 
     * @param a Arcade to draw in.
     */
    public UserListMainPanel (Arcade a) {
        super(a);
    }

    /**
     * 
     */
    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setPreferredSize(new Dimension(750, 900));
        JLabel label = new JLabel();
        label.setText("[User List]");
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        

        myPanel.add(label);
        ArrayList<UserProfile> allUsers = (ArrayList<UserProfile>) getArcade().getModelInterface().getAllUsers();
        for (UserProfile user : allUsers){
            myPanel.add(new UserListComponent(user, myPanel));
        }

        return myPanel;
    }

}
