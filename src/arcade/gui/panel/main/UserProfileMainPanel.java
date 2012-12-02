package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import edu.cmu.relativelayout.Direction;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.User;
import arcade.usermanager.UserProfile;
import arcade.utility.ImageReader;

/**
 * 
 * @author Robert Bruce
 *
 */

public class UserProfileMainPanel extends AMainPanel {

    private UserProfile myUser;
    private String gameStats;
    private String userToLoad;
    private JTextArea statsArea;
    private JLabel profileNameLabel;
    private Boolean loggedInUsersPage;
    public UserProfileMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        System.out.println("This is currently a work in progress. " +
                "It will break stuff until ModelInterface gets implemented.");
        ArcadePanel myPanel = initializeNewPanel();

        userToLoad = (String) getArcade().getVariable("UserName");
        myUser = getArcade().getModelInterface().getUser(userToLoad);
        loggedInUsersPage = userToLoad.equals(getArcade().getUsername());

        // Add the profile picture:
        Image profilePic = getUserPicture();
        JLabel profilePictureLabel = new JLabel(new ImageIcon(profilePic));

        // Add the username:
        String info = myUser.getUserFirstName() + " " + myUser.getUserLastName();
        profileNameLabel = new JLabel(info);
        profileNameLabel.setForeground(Color.WHITE);
        profileNameLabel.setVerticalTextPosition(JLabel.CENTER);
        profileNameLabel.setHorizontalTextPosition(JLabel.CENTER);
        
        // Add the First & Last name:
        JLabel blankLabel = new JLabel("Username : " + myUser.getUserName());
        blankLabel.setForeground(Color.WHITE);
        blankLabel.setVerticalTextPosition(JLabel.CENTER);
        blankLabel.setHorizontalTextPosition(JLabel.CENTER);
        
        // Add send message button:
        JButton sendMessageButton = new JButton("Send this player a message."); 
        sendMessageButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("SendMessage...");
                    //getArcade().replacePanel("SendMessage");
            }
              
          });
        JButton editButton = new JButton("Edit Profile"); 
        editButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("Edit Profile...");
                    //getArcade().replacePanel("SendMessage");
            }
              
          });
        gameStats = "Testing!";
        statsArea = new JTextArea(gameStats, 10, 20);
        statsArea.setLineWrap(true);
        statsArea.setWrapStyleWord(true);
        statsArea.setEditable(false);
        JScrollPane statsScrollPane = new JScrollPane(statsArea);


        myPanel.setLayout(new MigLayout("", "[200] 50 [475]", "[]20[]10[]"));
        myPanel.add(profilePictureLabel);
        myPanel.add(profileNameLabel, "span, grow, align center, wrap");
        myPanel.add(blankLabel, "span 1 2, wrap");
        myPanel.add(sendMessageButton, "grow, span, wrap");
        if (loggedInUsersPage) {
            myPanel.add(editButton, "grow, span, wrap");
        }
        myPanel.add(blankLabel, "wrap");
        myPanel.add(statsScrollPane, "grow, span");

        return myPanel;
    }
    
    
    private Image getUserPicture() {
        String fullLocation = myUser.getUserPicture();
        String fileName = fullLocation.substring(fullLocation.lastIndexOf("/"));
        String directoryLocation = fullLocation.substring(0,fullLocation.lastIndexOf("/"));
        return ImageReader.loadImage(directoryLocation, fileName);
    }
    
    /*
     * 
       MigLayout layout = new MigLayout();
        myPanel.setLayout(layout);

        myPanel.add(nameOfGame, "span, grow, align center, wrap");
        myPanel.add(profilePic);
        myPanel.add(averageRatingToDisplay, "wrap");
        myPanel.add(playButton, "grow, span, wrap");
        myPanel.add(scrollingDescription);
        myPanel.add(ratingsTitle, "split 2, flowy");
        myPanel.add(listOfRatingsToDisplay);
        myPanel.add(writeReviewAndRatingBut, "growx, spanx");
        myPanel.add(reviewsTitleLabel, "grow, span, wrap");
        myPanel.add(scrollingReviews, "grow, span");
        
        
        return myPanel;

    }
     */

}
