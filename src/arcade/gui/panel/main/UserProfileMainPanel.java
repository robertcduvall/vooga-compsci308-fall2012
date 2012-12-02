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
import arcade.utility.ImageReader;

/**
 * 
 * @author Robert Bruce
 *
 */

public class UserProfileMainPanel extends AMainPanel {

    private User myUser;
    private String gameStats;
    private String userToLoad;
    private JTextArea statsArea;
    private JLabel profileInfoLabel;
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
        loggedInUsersPage = userToLoad.equals(getArcade().getUsername());
        //User myUser = getArcade().getModelInterface().getUser(userToLoad);

        // Add the profile picture:
        //String profilePictureLocation = myUser.getPicture();
        String profilePictureLocation = "src/arcade/database/images/garfield.jpg";
        JLabel profilePictureLabel = new JLabel(new ImageIcon(profilePictureLocation));

        // Add the username:
        profileInfoLabel = new JLabel(getArcade().getUsername());
        profileInfoLabel.setForeground(Color.WHITE);
        profileInfoLabel.setVerticalTextPosition(JLabel.CENTER);
        profileInfoLabel.setHorizontalTextPosition(JLabel.CENTER);
        
        // Add the First & Last name:
        //JLabel userFirstAndLastName = new JLabel(getArcade().getCurrentUser().getFullName());
        JLabel blankLabel = new JLabel(" ");
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
        myPanel.add(profileInfoLabel, "span, grow, align center, wrap");
        myPanel.add(blankLabel, "span 1 2, wrap");
        myPanel.add(sendMessageButton, "grow, span, wrap");
        if (loggedInUsersPage) {
            myPanel.add(editButton, "grow, span, wrap");
        }
        myPanel.add(blankLabel, "wrap");
        myPanel.add(statsScrollPane, "grow, span");

        return myPanel;
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
