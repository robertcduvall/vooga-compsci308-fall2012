package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
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
    @SuppressWarnings("unchecked")
    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        int numUsers = getArcade().getModelInterface().getNumUsers();
        myPanel.setPreferredSize(new Dimension(750, 110*numUsers));
        JLabel label = new JLabel();
        label.setText("[User List]");
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        

        myPanel.add(label);
        ArrayList<UserProfile> allUsers = (ArrayList<UserProfile>) getArcade().getModelInterface().getAllUsers();
        Collections.sort(allUsers);
        // This ^ sort takes a while... But I don't really wanna mess with alphabetizing everything...
        for (UserProfile user : allUsers){
            myPanel.add(new UserListComponent(user, myPanel));
        }

        return myPanel;
    }

}
