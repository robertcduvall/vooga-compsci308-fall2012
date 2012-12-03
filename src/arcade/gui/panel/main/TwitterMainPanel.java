package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import javax.swing.JOptionPane;

public class TwitterMainPanel extends AMainPanel{

    private JTextArea textToTweet;
    
    public TwitterMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.CYAN);
        MigLayout layout = new MigLayout("", "[][]", "[][][]push[]");
        myPanel.setLayout(layout);
        
        JLabel welcomeLabel = new JLabel("Welcome to the Twitter interface! \nTweet from the comfort of the arcade.");
        JLabel tweetPrompt = new JLabel("Tweet here: ");
        
        textToTweet = new JTextArea("", 4, 25);
        textToTweet.setLineWrap(true);
        textToTweet.setWrapStyleWord(true);
        
        ImageIcon icon = new ImageIcon("src/arcade/gui/images/twitterbird.jpg");
        JButton tweetBut = new JButton("Tweet");
        JButton tweetDisconnectBut = new JButton("Disconnect Twitter Account");
        
        tweetBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
              String finalTweet = textToTweet.getText() + " | via Duke CS308 Arcade";
              boolean tweetSuccessful = getArcade().getModelInterface().sendTweet(getArcade().getUsername(), finalTweet);
              if (finalTweet.length() > 140) {
                  JOptionPane.showMessageDialog(null, "Tweet length exceeding 140 characters! \nKeep in mind the appended suffix when making your tweet.");
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
        
        myPanel.add(twitterPicture);
        myPanel.add(welcomeLabel, "wrap");
        myPanel.add(tweetPrompt, "wrap");
        myPanel.add(textToTweet);
        myPanel.add(tweetBut, "wrap, growy, span");
        myPanel.add(tweetDisconnectBut);
        
        
        
        
        
        return myPanel;
    }
}
