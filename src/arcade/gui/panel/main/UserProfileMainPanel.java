package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import arcade.utility.ImageReader;

/**
 * 
 * @author Robert Bruce
 *
 */
public class UserProfileMainPanel extends AMainPanel {

    public UserProfileMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        System.out.println("This is currently a work in progress. " +
                "It will break stuff until ModelInterface gets implemented.");
        ArcadePanel myPanel = initializeNewPanel();

        myPanel.setLayout(new MigLayout("", "[]50[100]", "[]5[]10[]"));

        // Add the profile picture:
        String profilePictureLocation = getArcade().getCurrentUser().getPicture();
        JLabel profilePictureLabel = new JLabel(new ImageIcon(profilePictureLocation));
        myPanel.add(profilePictureLabel, "align center");

        // Add the username:
        JLabel username = new JLabel(getArcade().getUsername());
        // Add the First & Last name:
        JLabel userFirstAndLastName = new JLabel(getArcade().getCurrentUser().getFullName());
        JLabel label = new JLabel();
        label.setText("[User Profile]");
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);

        myPanel.add(label, "align center");

        return myPanel;
    }

}
