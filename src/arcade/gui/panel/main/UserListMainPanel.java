package arcade.gui.panel.main;

import java.awt.Color;
import javax.swing.JLabel;
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

//        myPanel.setLayout(new MigLayout("", "[center]", "[center]60[center]"));
//        myPanel.add(label, "wrap");
//        myPanel.add(new UserListComponent("hello", myPanel), "wrap");
        
        myPanel.add(label);
        myPanel.add(new UserListComponent("hello", myPanel), Direction.BELOW);

        return myPanel;
    }

}
