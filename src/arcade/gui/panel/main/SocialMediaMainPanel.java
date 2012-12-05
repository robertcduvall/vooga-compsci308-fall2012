package arcade.gui.panel.main;

import java.awt.Color;
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
import javax.swing.JOptionPane;

/**
 * The twitter Main panel; here, the user can send tweets. Upon 
 * the finishing of use, the user is encouraged the disconenct from
 * his/her twitter acount via the button provided.
 * 
 *
 * @author Kannan and Howard
 *
 */
public class SocialMediaMainPanel extends AMainPanel{

    //TODO: REFACTORING HERE
    private JTextArea textToPost;
    private JTextArea instructionText;
    
    public SocialMediaMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.BLUE);
        MigLayout layout = new MigLayout("", "100[c]", "[][][]30[][]");
        myPanel.setLayout(layout);
        
        JLabel welcomeLabel = new JLabel("Welcome to the Social Media Connection Center!");
        welcomeLabel.setForeground(Color.WHITE);
        JLabel textPrompt = new JLabel("Enter your text here: ");
        textPrompt.setForeground(Color.WHITE);
        
        textToPost = new JTextArea("", 4, 25);
        textToPost.setLineWrap(true);
        textToPost.setWrapStyleWord(true);
        
        instructionText = new JTextArea("In order to use this feature, you must have a Twitter and/or a Facebook " +
        		"account already created.\n\nStart by typing in your text in the social media " +
        		"update field of your choice. Upon Tweeting or clicking 'Update Status', a tab will open in" +
        		" your browser prompting you for permission for the Arcade to integrate with your " +
        		"Twitter and/or Facebook account.\nBoth websites will provide you with an acknowledgement of authentication," +
        		" Twitter with a PIN number and Facebook with a success page with a long URL. Take the PIN and " +
        		"the URL for Twitter and Facebook, respectively, and input them into the small popup window eclipse " +
        		"has generated. Once you've done this, you should receive confirmation of a succesful post." +
        		"\n\nFinally, when you've finished letting your friends know what you're up to, we encourage you to" +
        		" disconnect from " +
        		"the appropriate social media website for personal account security.", 13, 40);
        instructionText.setLineWrap(true);
        instructionText.setWrapStyleWord(true);
        instructionText.setEditable(false);
        JScrollPane instructionTextScroller = new JScrollPane(instructionText);
        
        ImageIcon faceBookIcon = new ImageIcon("src/arcade/gui/images/facebook.jpg");
        JButton statusPostBut = new JButton("Update Status");
        JButton fbDisconnectBut = new JButton("Unlink Facebook Account");
        
        ImageIcon twitterIcon = new ImageIcon("src/arcade/gui/images/twitterbird.jpg");
        JButton tweetBut = new JButton("Tweet");
        JButton tweetDisconnectBut = new JButton("Unlink Twitter Account");
        
        statusPostBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
              String theStatusPost = textToPost.getText();
              if (theStatusPost.length() == 0) {
                  JOptionPane.showMessageDialog(null, "You need to enter Text before you can update your status.");
                  return;
              }
              String finalStatus = theStatusPost;
              boolean statusPostSuccessful = getArcade().getModelInterface().sendPost(getArcade().getUsername(), finalStatus);
              if (finalStatus.length() > 63206) {
                  JOptionPane.showMessageDialog(null, "Status post length may be too long!");
              }
              if (statusPostSuccessful) {
                  JOptionPane.showMessageDialog(null, "Status Update was successful!");
              }
              else {
                  JOptionPane.showMessageDialog(null, "Status Update failed Q_Q");
              }
            }             
          });
        
        tweetBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
              String theTweet = textToPost.getText();
              if (theTweet.length() == 0) {
                  JOptionPane.showMessageDialog(null, "You need to enter Text before you can Tweet.");
                  return;
              }
              String finalTweet = theTweet + " | via CS308 Arcade";
              boolean tweetSuccessful = getArcade().getModelInterface().sendTweet(getArcade().getUsername(), finalTweet);
              if (finalTweet.length() > 140) {
                  JOptionPane.showMessageDialog(null, "Tweet length exceeding 140 characters! \nKeep in mind the tail ' |" +
                  		" via CS308 Arcade' when making your tweet.");
                  return;
              }
              if (tweetSuccessful) {
                  JOptionPane.showMessageDialog(null, "Tweet was successful!");
              }
              else {
                  JOptionPane.showMessageDialog(null, "Tweet failed Q_Q");
              }
            }             
          });
        
        tweetDisconnectBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                boolean tweetDeleted = getArcade().getModelInterface().disconnectTwitter(getArcade().getUsername());
                if (tweetDeleted) {
                    JOptionPane.showMessageDialog(null, "Twitter Disconnect Successful.");
                }
                
            }             
          });
        
        fbDisconnectBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                boolean statusPostDeleted = getArcade().getModelInterface().disconnectFacebook(getArcade().getUsername());
                if (statusPostDeleted) {
                    JOptionPane.showMessageDialog(null, "Facebook Disconnect Successful.");
                }
                
            }             
          });
        
        
        JLabel twitterPicture = new JLabel(twitterIcon);
        JLabel facebookPicture = new JLabel(faceBookIcon);
        
        myPanel.add(twitterPicture, "align center, split 3");
        myPanel.add(welcomeLabel, "align center");
        myPanel.add(facebookPicture, "align center, wrap");
        myPanel.add(textPrompt, "align center, span, wrap");
        myPanel.add(tweetBut, "grow, split 3");
        myPanel.add(textToPost, "grow, align center, span");
        myPanel.add(statusPostBut, "grow, wrap");
        myPanel.add(instructionTextScroller, "wrap, grow, align center, span");
        myPanel.add(tweetDisconnectBut, "align left, split 2");
        myPanel.add(fbDisconnectBut, "align left");
        
        
        
        return myPanel;
    }
}
