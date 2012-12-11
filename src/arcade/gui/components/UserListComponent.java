package arcade.gui.components;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.UserProfile;


/**
 * A useful example of an ArcadeListComponent.
 * Displays a user profile picture, user name,
 * the player's full name, a button to visit
 * the user's profile page, and a button to
 * send the user a message.
 * 
 * @author Robert Bruce
 */
@SuppressWarnings("serial")
public class UserListComponent extends ArcadeListComponent {

    private UserProfile myUser;
    private JButton viewProfileButton;
    private JButton sendMessageButton;
    private String myUserName;

    public UserListComponent (UserProfile user, ArcadePanel theContainer) {
        super(user.getUserPicture(), user.getUserName(), user
                .getUserFirstName() + " " + user.getUserLastName(),
                theContainer);
        myUser = user;
        myUserName = myUser.getUserName();
        setPreferredSize(new Dimension(theContainer.getWidth(), 110));
    }

    public String getUserName () {
        return myUserName;
    }

    @Override
    protected void initComponents () {
        setLayout(new MigLayout("", "[450][]5[]", "[30][][30]"));
        viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("View profile of " + myUser);
                myContainer.getArcade().saveVariable("UserName",
                        myUser.getUserName());
                myContainer.getArcade().replacePanel("UserProfile");
            }

        });
        sendMessageButton = new JButton("Send Message");
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                myContainer.getArcade().saveVariable("UserName",
                        myUser.getUserName());
                myContainer.getArcade().replacePanel("SendMessage");
            }

        });
        this.add(viewProfileButton, "cell 1 1, align right, split 2");
        this.add(sendMessageButton);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension(myContainer.getWidth(), 100);
    }

    /**
     * Used for sorting. If the other component is also a
     * UserListComponent, it returns a comparison b/t their
     * user names. Otherwise, it returns 0.
     */
    @Override
    public int compareTo (ArcadeListComponent o) {
        if (o.getClass().equals(this.getClass())) { return myUser.getUserName()
                .compareTo(((UserListComponent) o).getUserName()); }
        return 0;
    }
}
