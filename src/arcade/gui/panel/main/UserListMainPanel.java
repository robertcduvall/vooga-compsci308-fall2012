package arcade.gui.panel.main;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import edu.cmu.relativelayout.Direction;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.components.UserListComponent;
import arcade.gui.panel.ArcadePanel;


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

        JLabel label = new JLabel();
        label.setText("[User List]");
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);

        myPanel.add(label);
        for (int i=0; i<10; i++){
            myPanel.add(new UserListComponent("Helloooooo321"+i, myPanel));
        }

        return myPanel;
    }

}
