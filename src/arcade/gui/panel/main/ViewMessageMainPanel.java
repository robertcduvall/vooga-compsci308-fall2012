package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.UserProfile;
import arcade.utility.ImageReader;

public class ViewMessageMainPanel extends AMainPanel {

    private UserProfile myUser;
    private String gameStats;
    private String userToLoad;
    private String messageToDisplay;
    private JTextArea statsArea;
    private JLabel senderLabel;
    private Boolean loggedInUsersPage;
    public ViewMessageMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();

        userToLoad = (String) getArcade().getVariable("UserName");
        messageToDisplay = (String) getArcade().getVariable("Message");
        myUser = getArcade().getModelInterface().getUser(userToLoad);
        loggedInUsersPage = userToLoad.equals(getArcade().getUsername());

        getArcade().saveVariable("Message", "");

        // Add the profile picture:
        Image profilePic = getUserPicture();
        JLabel profilePictureLabel = new JLabel(new ImageIcon(profilePic.getScaledInstance(175, 175, 0)));

        // Add the username:
        String info = "<html><font size='6'><b>From:</b></font><br/><b>Username: </b>" + myUser.getUserName()
                + "<br/><b>Full Name: </b>"
                + myUser.getUserFirstName() + " " + myUser.getUserLastName()+"<html/>";
        senderLabel = new JLabel(info);
        senderLabel.setForeground(Color.WHITE);
        senderLabel.setVerticalTextPosition(JLabel.CENTER);
        senderLabel.setHorizontalTextPosition(JLabel.CENTER);
        
        // Add send message button:
        JButton replyButton = new JButton("Reply"); 
        replyButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("Send message to " + userToLoad);
                getArcade().saveVariable("UserName", userToLoad);
                getArcade().replacePanel("SendMessage");
            }

        });
        JButton viewProfileButton = new JButton("View Profile"); 
        viewProfileButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("View profile of " + userToLoad);
                getArcade().saveVariable("UserName", userToLoad);
                getArcade().replacePanel("UserProfile");
            }

        });
        
        if (messageToDisplay != null && !messageToDisplay.equals("")) {
            statsArea = new JTextArea(messageToDisplay, 10, 20);
        }
        else {
            statsArea = new JTextArea("*There is no message content.*", 10, 20);
        }
        statsArea.setLineWrap(true);
        statsArea.setWrapStyleWord(true);
        statsArea.setEditable(false);
        JScrollPane messageScrollPane = new JScrollPane(statsArea);


        myPanel.setLayout(new MigLayout("", "[200] 50 [450] 20", "[]20[]10[]"));
        myPanel.add(profilePictureLabel);
        myPanel.add(senderLabel, "span, grow, align center, wrap");
        myPanel.add(messageScrollPane, "grow, span");
        myPanel.add(viewProfileButton, "dock south, grow, span, wrap");
        myPanel.add(replyButton, "dock south, grow, span, wrap");

        return myPanel;
    }


    private Image getUserPicture() {
        String fullLocation = myUser.getUserPicture();
        String fileName = fullLocation.substring(fullLocation.lastIndexOf("/"));
        String directoryLocation = fullLocation.substring(0,fullLocation.lastIndexOf("/"));
        return ImageReader.loadImage(directoryLocation, fileName);
    }

}
