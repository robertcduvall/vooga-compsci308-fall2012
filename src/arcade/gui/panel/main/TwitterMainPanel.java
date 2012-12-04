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
public class TwitterMainPanel extends AMainPanel{

    //TODO: REFACTORING HERE
    private JTextArea textToTweet;
    private JTextArea instructionText;
    
    public TwitterMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.BLUE);
        MigLayout layout = new MigLayout("", "[c][c]", "[][][][]push[]");
        myPanel.setLayout(layout);
        
        JLabel welcomeLabel = new JLabel("Welcome to the twitter connect! \nTweet from the comfort of the arcade.");
        welcomeLabel.setForeground(Color.WHITE);
        JLabel tweetPrompt = new JLabel("Tweet here: ");
        tweetPrompt.setForeground(Color.WHITE);
        
        textToTweet = new JTextArea("", 4, 25);
        textToTweet.setLineWrap(true);
        textToTweet.setWrapStyleWord(true);
        
        instructionText = new JTextArea("In order to use this feature, you must have a Twitter" +
        		" account.\n\nStart by typing in your text and then hit Tweet. A tab will" +
        		" open in your browser which will ask you for permission for the Arcade to" +
        		" integrate with your Twitter.\nOnce you accept, you will be given a PIN number to enter " +
        		"into a popup window from eclipse. After that, you're good to go!\n\nLastly, when you've " +
        		"ended your tweeting, please disconnect from twitter for personal security.", 8, 40);
        instructionText.setLineWrap(true);
        instructionText.setWrapStyleWord(true);
        instructionText.setEditable(false);
        JScrollPane instructionTextScroller = new JScrollPane(instructionText);
        
        ImageIcon icon = new ImageIcon("src/arcade/gui/images/twitterbird.jpg");
        JButton tweetBut = new JButton("Tweet");
        JButton tweetDisconnectBut = new JButton("Unlink Twitter Account");
        
        tweetBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
              String theTweet = textToTweet.getText();
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
                  textToTweet.setText("");
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
                    JOptionPane.showMessageDialog(null, "Disconnect Successful.");
                }
                
            }             
          });
        
        
        JLabel twitterPicture = new JLabel(icon);
        
        myPanel.add(twitterPicture, "align right");
        myPanel.add(welcomeLabel, "align left, wrap");
        //myPanel.add(tweetPrompt, "align center, span, wrap");
        myPanel.add(textToTweet, "grow, split 2, align center, span");
        myPanel.add(tweetBut, "wrap, grow,");
        myPanel.add(instructionTextScroller, "wrap, grow, align center, span");
        myPanel.add(tweetDisconnectBut, "align left");
        
        
        
        
        
        return myPanel;
    }
}
