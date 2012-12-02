package arcade.gui.panel.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import edu.cmu.relativelayout.Direction;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.components.MessageListComponent;
import arcade.gui.components.UserListComponent;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.Message;
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

        List<Message> myMessages = getArcade().getModelInterface().getEditableCurrentUser().getMyMessage();
        for (Message aMessage : myMessages){
            myPanel.add(new MessageListComponent(aMessage.getSender(), 
                    aMessage.getMessage(), myPanel), BorderLayout.SOUTH);
        }

        return myPanel;
    }
}

