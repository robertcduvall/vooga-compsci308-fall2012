package arcade.gui.panel.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import edu.cmu.relativelayout.Direction;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.components.MessageListComponent;
import arcade.gui.components.UserListComponent;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.UserProfile;


/**
 * 
 * @author Michael Deng
 * 
 */
public class MessageCenterMainPanel extends AMainPanel {

    public MessageCenterMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setPreferredSize(new Dimension(750, 900));
        JButton composeMessageButton = new JButton("Compose A Message.");

        myPanel.add(composeMessageButton);

        ArrayList<String> messages = new ArrayList<String>();
        ArrayList<String> senders = new ArrayList<String>();
        for (int i=0; i<10; i++){
            myPanel.add(new MessageListComponent("Howard", "Message" + i +
                    " blah blah bh bh eehhbh bh b h beehh h beehheehh h blah" +
                    "blahhhhhh meeeeeehhhhhhhhhhhhhh meeeeeehhhhhhhhhhhhhh " +
                    "meeeeeehhhhhhhhhhhhhh meeeeeehhhhhhhhhhhhhh " + i +
                    "meeeeeehhhhhhhhhhhhhh meeeeeehhhhhhhhhhhhhh " + i +
                    "eeeeeehhhhhhhhhhhhhhhhh" + i, myPanel), BorderLayout.SOUTH);
        }

        return myPanel;
    }
}

