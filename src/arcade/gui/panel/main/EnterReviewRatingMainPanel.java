package arcade.gui.panel.main;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;


/**
 * This class allows the user to set a rating for a game as well
 * as write up a comment or review for others to see. The user can also
 * navigate back from the panel if he/she does not want to enter anything.
 * 
 * @author Kannan
 * 
 */
public class EnterReviewRatingMainPanel extends AMainPanel implements ActionListener {

    private static final String RETURN_TO_PROFILE = "Return to Profile";
    private static final String SUBMIT = "Submit";
    private String myGameName;
    private String myRatingToBeSubmitted;
    private JTextArea myReviewArea;
    private final int myReviewTextAreaHeight = 20;
    private final int myReviewTextAreaWidth = 40;
    private String myOne = "1";
    private String myTwo = "2";
    private String myThree = "3";
    private String myFour = "4";
    private String myFive = "5";
    private String mySix = "6";
    private String mySeven = "7";
    private String myEight = "8";
    private String myNine = "9";
    private String myTen = "10";

    /**
     * The Constructor for this class.
     * 
     * @param a The Arcade Object being passed down.
     */
    public EnterReviewRatingMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myGameName = (String) getArcade().getVariable("GameName");

        MigLayout layout = new MigLayout("flowy", "[]push[]", "[]10[][][]push[]");
        myPanel.setLayout(layout);
        
        //create basic label components
        JLabel titleLabel = new JLabel("Leave your feedback for " + myGameName + " below!");
        titleLabel.setForeground(Color.WHITE);
        JLabel ratingLabel = new JLabel("Select a Rating:");
        ratingLabel.setForeground(Color.WHITE);
        JLabel reviewPrompt = new JLabel("Write a review/comment:");
        reviewPrompt.setForeground(Color.WHITE);

        // establish the radio buttons
        JRadioButton oneButton = new JRadioButton(myOne);
        oneButton.setActionCommand(myOne);
        oneButton.setForeground(Color.RED);

        JRadioButton twoButton = new JRadioButton(myTwo);
        twoButton.setActionCommand(myTwo);
        twoButton.setForeground(Color.RED);

        JRadioButton threeButton = new JRadioButton(myThree);
        threeButton.setActionCommand(myThree);
        threeButton.setForeground(Color.RED);

        JRadioButton fourButton = new JRadioButton(myFour);
        fourButton.setActionCommand(myFour);
        fourButton.setForeground(Color.RED);

        JRadioButton fiveButton = new JRadioButton(myFive);
        fiveButton.setActionCommand(myFive);
        fiveButton.setForeground(Color.RED);

        JRadioButton sixButton = new JRadioButton(mySix);
        sixButton.setActionCommand(mySix);
        sixButton.setForeground(Color.RED);

        JRadioButton sevenButton = new JRadioButton(mySeven);
        sevenButton.setActionCommand(mySeven);
        sevenButton.setForeground(Color.RED);

        JRadioButton eightButton = new JRadioButton(myEight);
        eightButton.setActionCommand(myEight);
        eightButton.setForeground(Color.RED);

        JRadioButton nineButton = new JRadioButton(myNine);
        nineButton.setActionCommand(myNine);
        nineButton.setForeground(Color.RED);

        JRadioButton tenButton = new JRadioButton(myTen);
        tenButton.setActionCommand(myTen);
        tenButton.setForeground(Color.RED);

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

        myReviewArea = new JTextArea("", myReviewTextAreaHeight, myReviewTextAreaWidth);
        myReviewArea.setLineWrap(true);
        myReviewArea.setWrapStyleWord(true);
        JScrollPane scrollingReview = new JScrollPane(myReviewArea);

        //create button components
        JButton returnToProfileBut = new JButton("Return to Profile Page");
        returnToProfileBut.addActionListener(this);
        returnToProfileBut.setActionCommand(RETURN_TO_PROFILE);
        JButton submitBut = new JButton(SUBMIT);
        submitBut.addActionListener(this);
        submitBut.setActionCommand(SUBMIT);

        //add components to Panel
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
    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand().equals(RETURN_TO_PROFILE)) {
            getArcade().replacePanel("GameProfile");
        }
        if (e.getActionCommand().equals(SUBMIT)) {
            if (myRatingToBeSubmitted != null) {
                getArcade().getModelInterface().getGame(myGameName).
                setRating(Integer.parseInt(myRatingToBeSubmitted));
            }
            if (myReviewArea.getText() != null && !myReviewArea.getText().equals("")) {
                getArcade().getModelInterface().getGame(myGameName).
                setReview(myReviewArea.getText() + " - " + getArcade().getUsername());
            }
            getArcade().replacePanel("GameProfile");
        }
        if (e.getActionCommand() == myOne) {
            myRatingToBeSubmitted = myOne;
        }
        if (e.getActionCommand() == myTwo) {
            myRatingToBeSubmitted = myTwo;
        }
        if (e.getActionCommand() == myThree) {
            myRatingToBeSubmitted = myThree;
        }
        if (e.getActionCommand() == myFour) {
            myRatingToBeSubmitted = myFour;
        }
        if (e.getActionCommand() == myFive) {
            myRatingToBeSubmitted = myFive;
        }
        if (e.getActionCommand() == mySix) {
            myRatingToBeSubmitted = mySix;
        }
        if (e.getActionCommand() == mySeven) {
            myRatingToBeSubmitted = mySeven;
        }
        if (e.getActionCommand() == myEight) {
            myRatingToBeSubmitted = myEight;
        }
        if (e.getActionCommand() == myNine) {
            myRatingToBeSubmitted = myNine;
        }
        if (e.getActionCommand() == myTen) {
            myRatingToBeSubmitted = myTen;
        }
    }
}
