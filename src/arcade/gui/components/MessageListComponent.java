package arcade.gui.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.Message;


/**
 * A useful example of an ArcadeListComponent.
 * Displays the user profile picture of whoever
 * sent the message, user name of who sent the
 * message, the first 30 characters of the message,
 * and the time / date at which the message was
 * sent. Also has two buttons: one to visit the
 * profile of the message sender and a button
 * to view the full message.
 * 
 * @author Robert Bruce
 */
@SuppressWarnings("serial")
public class MessageListComponent extends ArcadeListComponent {

    private Message myMessage;
    private JButton viewProfileButton;
    private JButton sendMessageButton;

    public MessageListComponent (Message aMessage, ArcadePanel theContainer) {
        super(theContainer.getArcade().getModelInterface()
                .getUser(aMessage.getSender()).getUserPicture(), aMessage
                .getSender(), aMessage.getMessage(), theContainer);
        myMessage = aMessage;
        String displayMessage = aMessage.getMessage();
        if (displayMessage.length() > 30) {
            displayMessage = displayMessage.substring(0, 30) + "..." + " "
                    + aMessage.getDateString();
        }
        setSubText(displayMessage);
    }

    @Override
    protected void initComponents () {
        setLayout(new MigLayout("", "[450][]5[]", "[30][][30]"));
        System.out.println("Initing.");

        viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                myContainer.getArcade().saveVariable("UserName",
                        myMessage.getSender());
                myContainer.getArcade().replacePanel("UserProfile");
            }

        });
        sendMessageButton = new JButton("View Message");
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("Opening Message.");
                myContainer.getArcade().saveVariable("UserName",
                        myMessage.getSender());
                myContainer.getArcade().saveVariable("Message",
                        myMessage.getMessage());
                myContainer.getArcade().replacePanel("ViewMessage");
            }

        });
        this.add(viewProfileButton, "cell 1 1, align right, split 2");
        this.add(sendMessageButton);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension(myContainer.getWidth(), 100);
    }
}
