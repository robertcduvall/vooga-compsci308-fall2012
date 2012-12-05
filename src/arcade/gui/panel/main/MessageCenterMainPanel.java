package arcade.gui.panel.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import util.encrypt.Encrypter;
import edu.cmu.relativelayout.Direction;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.components.MessageListComponent;
import arcade.gui.components.UserListComponent;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.Message;
import arcade.usermanager.UserProfile;
import arcade.usermanager.exception.ValidationException;


/**
 * 
 * @author Robert Bruce
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
        composeMessageButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().saveVariable("UserName", "");
                getArcade().replacePanel("SendMessage");
            }

        });

        myPanel.add(composeMessageButton);

        List<Message> myMessages = getArcade().getModelInterface().getMessage();
        int numLoaded = 0;
        for (Message aMessage : myMessages){
            try {
                myPanel.add(new MessageListComponent(aMessage.getSender(), 
                        aMessage.getMessage(), myPanel), BorderLayout.SOUTH);
                numLoaded++;
            }
            catch (NullPointerException e) {
                System.out.println("Trouble loading a message...");
            }
        }
        myPanel.setPreferredSize(new Dimension(750, (110*numLoaded)+50));

        return myPanel;
    }
}

