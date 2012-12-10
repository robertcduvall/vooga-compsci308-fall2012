package arcade.gui.panel.main;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;


/**
 * The Social Media Main panel; here, the user can send tweets and
 * facebook status updates. Upon completion, the user is encouraged
 * to disconnect from their social media account for security
 * purposes.
 * 
 * @author Kannan and Howard
 * 
 */
public class SocialMediaMainPanel extends AMainPanel implements ActionListener {

    private static final String TWEET = "Tweet";
    private static final String UPDATE_STATUS = "Update Status";
    private static final String UNLINK_TWITTER = "Unlink Twitter Account";
    private static final String UNLINK_FACEBOOK = "Unlink Facebook Account";
    private JTextArea myTextToPost;
    private JTextArea myInstructionText;
    private final int myTextToPostHeight = 4;
    private final int myTextToPostWidth = 25;
    private final int myInstructionTextHeight = 13;
    private final int myInstructionTextWidth = 40;
    private final int myMaxTweetLength = 140;
    private final int myPotentiallyMaxStatusLength = 63206;

    /**
     * The Constructor for this class.
     * 
     * @param a The Arcade Object being passed down
     */
    public SocialMediaMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.BLUE);
        MigLayout layout = new MigLayout("", "30[c]", "[][][]30[][]");
        myPanel.setLayout(layout);

        JLabel welcomeLabel = new JLabel("Welcome to the Social Media Connection Center!");
        welcomeLabel.setForeground(Color.WHITE);
        JLabel textPrompt = new JLabel("Enter your text here: ");
        textPrompt.setForeground(Color.WHITE);

        myTextToPost = new JTextArea("", myTextToPostHeight, myTextToPostWidth);
        myTextToPost.setLineWrap(true);
        myTextToPost.setWrapStyleWord(true);

        myInstructionText =
                new JTextArea("In order to use this feature, you must" +
                " have a Twitter and/or a Facebook account" +
                " already created.\n\nStart by typing in" +
                " your message in the text field. Upon Tweeting" +
                " or clicking 'Update Status', a tab will open" +
                " in your browser prompting you for permission" +
                " for the Arcade to integrate with your Twitter" +
                " and/or Facebook account.\nBoth websites will" +
                " provide you with an acknowledgement of authentication," +
                " Twitter with a PIN number and Facebook with a success" +
                " page with a long URL. Take the PIN and the URL for Twitter" +
                " and Facebook, respectively, and input them into the small" +
                " popup window eclipse has generated. Once you've done this," +
                " you should receive confirmation of a succesful post.\n\nFinally," +
                " when you've finished letting your friends know what you're up to," +
                " we encourage you to disconnect from the appropriate social media" +
                " website(s) for personal account security.",
                myInstructionTextHeight, myInstructionTextWidth);
        myInstructionText.setLineWrap(true);
        myInstructionText.setWrapStyleWord(true);
        myInstructionText.setEditable(false);
        JScrollPane instructionTextScroller = new JScrollPane(myInstructionText);

        //Title Pictures made
        ImageIcon faceBookIcon = new ImageIcon("src/arcade/gui/images/facebook.jpg");
        JLabel facebookPicture = new JLabel(faceBookIcon);
        ImageIcon twitterIcon = new ImageIcon("src/arcade/gui/images/twitterbird.jpg");
        JLabel twitterPicture = new JLabel(twitterIcon);

        //Creation of Interaction Buttons for Panel
        JButton statusPostBut = new JButton(UPDATE_STATUS);
        statusPostBut.addActionListener(this);
        statusPostBut.setActionCommand(UPDATE_STATUS);

        JButton fbDisconnectBut = new JButton(UNLINK_FACEBOOK);
        fbDisconnectBut.addActionListener(this);
        fbDisconnectBut.setActionCommand(UNLINK_FACEBOOK);

        JButton tweetBut = new JButton(TWEET);
        tweetBut.addActionListener(this);
        tweetBut.setActionCommand(TWEET);

        JButton tweetDisconnectBut = new JButton(UNLINK_TWITTER);
        tweetDisconnectBut.addActionListener(this);
        tweetDisconnectBut.setActionCommand(UNLINK_TWITTER);

        //add components to Panel
        myPanel.add(twitterPicture, "align center, split 3");
        myPanel.add(welcomeLabel, "align center");
        myPanel.add(facebookPicture, "align center, wrap");
        myPanel.add(textPrompt, "align center, span, wrap");
        myPanel.add(tweetBut, "grow, split 3");
        myPanel.add(myTextToPost, "grow, align center, span");
        myPanel.add(statusPostBut, "grow, wrap");
        myPanel.add(instructionTextScroller, "wrap, grow, align center, span");
        myPanel.add(tweetDisconnectBut, "align left, split 2");
        myPanel.add(fbDisconnectBut, "align left");

        return myPanel;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand().equals(UPDATE_STATUS)) {
            String theStatusPost = myTextToPost.getText();
            if (theStatusPost.length() == 0) {
                JOptionPane.showMessageDialog(null, "You need to enter" +
                        " Text before you can update your status.");
                return;
            }
            String finalStatus = theStatusPost;
            boolean statusPostSuccessful =
                    getArcade().getModelInterface().sendPost(getArcade().getUsername(),
                                                             finalStatus);
            if (finalStatus.length() > myPotentiallyMaxStatusLength) {
                JOptionPane.showMessageDialog(null, "Status post length may be too long!");
            }
            if (statusPostSuccessful) {
                JOptionPane.showMessageDialog(null, "Status Update was successful!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Status Update failed Q_Q");
            }
        }
        if (e.getActionCommand().equals(TWEET)) {
            String theTweet = myTextToPost.getText();
            if (theTweet.length() == 0) {
                JOptionPane.showMessageDialog(null,
                                              "You need to enter Text before you can Tweet.");
                return;
            }
            String finalTweet = theTweet + " | via CS308 Arcade";
            boolean tweetSuccessful =
                    getArcade().getModelInterface().sendTweet(getArcade().getUsername(),
                                                              finalTweet);
            if (finalTweet.length() > myMaxTweetLength) {
                JOptionPane.showMessageDialog(null, "Tweet length exceeding 140 characters!" +
                        " \nKeep in mind the tail ' | via CS308 Arcade' when making your tweet.");
                return;
            }
            if (tweetSuccessful) {
                JOptionPane.showMessageDialog(null, "Tweet was successful!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Tweet failed Q_Q");
            }
        }
        if (e.getActionCommand().equals(UNLINK_TWITTER)) {
            boolean tweetDeleted =
                    getArcade().getModelInterface().
                    disconnectTwitter(getArcade().getUsername());
            if (tweetDeleted) {
                JOptionPane.showMessageDialog(null, "Twitter Disconnect Successful.");
            }
        }
        if (e.getActionCommand().equals(UNLINK_FACEBOOK)) {
            boolean statusPostDeleted =
                    getArcade().getModelInterface().
                    disconnectFacebook(getArcade().getUsername());
            if (statusPostDeleted) {
                JOptionPane.showMessageDialog(null, "Facebook Disconnect Successful.");
            }
        }
    }
}
