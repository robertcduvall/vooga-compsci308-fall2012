package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Image;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

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

        Image profilePicture = getArcade().getCurrentUser().getPicture();
        myPanel = addUserInfo(myPanel);
        myPanel = addUserGames(myPanel);

        JLabel label = new JLabel();
        label.setText("[User Profile]");
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);

        myPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        myPanel.add(label, "align center");

        return myPanel;
    }


    private ArcadePanel addUserInfo (ArcadePanel myPanel) {
        // TODO Auto-generated method stub
        return null;
    }

    private ArcadePanel addUserGames (ArcadePanel myPanel) {
        // TODO Implement this.
        return myPanel;
    }

}
