package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * This class allows the user to set a rating for a game as well
 * as write up a comment or review for others to see. 
 * @author Kannan
 *
 */
public class EnterReviewRatingMainPanel extends AMainPanel implements ActionListener {

    
    //TODO:REFACTORING
    private String gameName;
    private String ratingToBeSubmitted;
    private JTextArea reviewArea;
    private Dimension maxReviewBoxSize = new Dimension(600, 300);
    
    public EnterReviewRatingMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        gameName = (String) getArcade().getVariable("GameName");

        MigLayout layout = new MigLayout("flowy", "[]push[]", "[]10[][][]push[]");
        myPanel.setLayout(layout);

        JLabel titleLabel = new JLabel("Leave your feedback for " + gameName + " below!");
        titleLabel.setForeground(Color.WHITE);
        JLabel ratingLabel = new JLabel("Select a Rating:");
        ratingLabel.setForeground(Color.WHITE);

        JRadioButton oneButton = new JRadioButton("1");
        oneButton.setMnemonic(KeyEvent.VK_B);
        oneButton.setActionCommand("1");

        JRadioButton twoButton = new JRadioButton("2");
        twoButton.setMnemonic(KeyEvent.VK_B);
        twoButton.setActionCommand("2");

        JRadioButton threeButton = new JRadioButton("3");
        threeButton.setMnemonic(KeyEvent.VK_B);
        threeButton.setActionCommand("3");

        JRadioButton fourButton = new JRadioButton("4");
        fourButton.setMnemonic(KeyEvent.VK_B);
        fourButton.setActionCommand("4");

        JRadioButton fiveButton = new JRadioButton("5");
        fiveButton.setMnemonic(KeyEvent.VK_B);
        fiveButton.setActionCommand("5");

        JRadioButton sixButton = new JRadioButton("6");
        sixButton.setMnemonic(KeyEvent.VK_B);
        sixButton.setActionCommand("6");

        JRadioButton sevenButton = new JRadioButton("7");
        sevenButton.setMnemonic(KeyEvent.VK_B);
        sevenButton.setActionCommand("7");

        JRadioButton eightButton = new JRadioButton("8");
        eightButton.setMnemonic(KeyEvent.VK_B);
        eightButton.setActionCommand("8");

        JRadioButton nineButton = new JRadioButton("9");
        nineButton.setMnemonic(KeyEvent.VK_B);
        nineButton.setActionCommand("9");

        JRadioButton tenButton = new JRadioButton("10");
        tenButton.setMnemonic(KeyEvent.VK_B);
        tenButton.setActionCommand("10");

        // Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(oneButton);
        group.add(twoButton);
        group.add(threeButton);
        group.add(fourButton);
        group.add(fiveButton);
        group.add(sixButton);
        group.add(sevenButton);
        group.add(eightButton);
        group.add(nineButton);
        group.add(tenButton);

        // Register a listener for the radio buttons.
        oneButton.addActionListener(this);
        twoButton.addActionListener(this);
        threeButton.addActionListener(this);
        fourButton.addActionListener(this);
        fiveButton.addActionListener(this);
        sixButton.addActionListener(this);
        sevenButton.addActionListener(this);
        eightButton.addActionListener(this);
        nineButton.addActionListener(this);
        tenButton.addActionListener(this);
        
        JLabel reviewPrompt = new JLabel("Write a review/comment:");
        reviewPrompt.setForeground(Color.WHITE);
        reviewArea = new JTextArea("", 20, 40);
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        //reviewArea.setMaximumSize(maxReviewBoxSize);
        JScrollPane scrollingReview = new JScrollPane(reviewArea);
        
        JButton returnToProfileBut = new JButton("Return to Profile Page");
        returnToProfileBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().replacePanel("GameProfile");
            }             
          });
        
        JButton submitBut = new JButton("Submit");
        submitBut.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("this is registering");
                if (ratingToBeSubmitted != null) {
                    getArcade().getModelInterface().getGame(gameName)
                            .setRating(Integer.parseInt(ratingToBeSubmitted));
                }
                if (reviewArea.getText() != null && reviewArea.getText() != "") {
                    getArcade().getModelInterface().getGame(gameName).setReview(reviewArea.getText() 
                                                                                + "- " + getArcade().getUsername());
                }
                getArcade().replacePanel("GameProfile");
            }
              
          });

        myPanel.add(titleLabel, "span, align center");
        myPanel.add(ratingLabel, "align center");
        myPanel.add(oneButton, "split 10, align center");
        myPanel.add(twoButton, "align center");
        myPanel.add(threeButton, "align center");
        myPanel.add(fourButton, "align center");
        myPanel.add(fiveButton, "align center");
        myPanel.add(sixButton, "align center");
        myPanel.add(sevenButton, "align center");
        myPanel.add(eightButton, "align center");
        myPanel.add(nineButton, "align center");
        myPanel.add(tenButton, "wrap, align center");
        myPanel.add(reviewPrompt, "align center");
        myPanel.add(scrollingReview, "align center, grow");
        myPanel.add(returnToProfileBut, "wrap");
        myPanel.add(submitBut, "dock south, growy, span, align center");

        return myPanel;
    }

    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand() == "1") {
            ratingToBeSubmitted = "1";
        }
        if (e.getActionCommand() == "2") {
            ratingToBeSubmitted = "2";
        }
        if (e.getActionCommand() == "3") {
            ratingToBeSubmitted = "3";
        }
        if (e.getActionCommand() == "4") {
            ratingToBeSubmitted = "4";
        }
        if (e.getActionCommand() == "5") {
            ratingToBeSubmitted = "5";
        }
        if (e.getActionCommand() == "6") {
            ratingToBeSubmitted = "6";
        }
        if (e.getActionCommand() == "7") {
            ratingToBeSubmitted = "7";
        }
        if (e.getActionCommand() == "8") {
            ratingToBeSubmitted = "8";
        }
        if (e.getActionCommand() == "9") {
            ratingToBeSubmitted = "9";
        }
        if (e.getActionCommand() == "10") {
            ratingToBeSubmitted = "10";
        }
    }
}
