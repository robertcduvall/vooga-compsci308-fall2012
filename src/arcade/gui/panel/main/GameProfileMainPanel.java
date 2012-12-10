package arcade.gui.panel.main;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;


/**
 * Each individual game's profile page. Contains general assignments
 * so that any game's information can be displayed using this class.
 * 
 * @author Kannan
 * 
 */
public class GameProfileMainPanel extends AMainPanel implements ActionListener {

    private static final String PLAY = "Play";
    private static final String REVIEW = "Review";
    private static final String TWEET = "Tweet";
    private static final String UPDATE_STATUS = "Update Status";
    private String myQuickSocialMediaPost;
    private String myGameName;
    private ImageIcon myGameIcon;
    private JLabel myListOfTagsToDisplay;
    private List<String> myListOfReviews;
    private List<Integer> myListOfRatings;
    private List<String[]> myUsersAndScoresList;
    private JTextArea myReviewsArea;
    private JTextArea myTopScoresArea;
    private final int myLengthOfAvgRatingTitle = 16;
    private List<String> myListOfTags;
    private final int myLengthOfAverageRatingNum = 4;
    private final int myLengthOfAverageRatingPhrase = 20;
    private final int myGameDescriptionHeight = 10;
    private final int myGameDescriptionWidth = 20;
    private final int myGameReviewsAreaHeight = 10;
    private final int myGameReviewsAreaWidth = 20;
    private final int myTopScoresAreaHeight = 10;
    private final int myTopScoresAreaWidth = 8;
    private final int myTen = 10;

    /**
     * The Constructor for this class.
     * 
     * @param a The Arcade object being passed down.
     */
    public GameProfileMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        MigLayout layout = new MigLayout("insets 10 100 10 10", "[c][c]", "[][][][][][][]");
        myPanel.setLayout(layout);

        //instantiation of relevant data
        myGameName = (String) getArcade().getVariable("GameName");
        myListOfReviews = getArcade().getModelInterface().getGame(myGameName).getReviews();
        myListOfRatings = getArcade().getModelInterface().getGame(myGameName).getRatings();
        myListOfTags = getArcade().getModelInterface().getGame(myGameName).getTags();
        Collections.sort(myListOfRatings);
        myQuickSocialMediaPost = "Playing " + myGameName + "! | via CS:308 Arcade";
 
        //create the panel's buttons
        JButton playButton = new JButton(PLAY);
        playButton.addActionListener(this);
        playButton.setActionCommand(PLAY);
        
        JButton writeReviewAndRatingBut = new JButton("Review/Rate this Game");
        writeReviewAndRatingBut.addActionListener(this);
        writeReviewAndRatingBut.setActionCommand(REVIEW);
        
        JButton statusPostBut = new JButton("Post to Facebook");
        statusPostBut.addActionListener(this);
        statusPostBut.setActionCommand(UPDATE_STATUS);

        JButton tweetBut = new JButton(TWEET);
        tweetBut.addActionListener(this);
        tweetBut.setActionCommand(TWEET);

        //generation of complex components
        JLabel profilePic = establishGameProfilePic();
        JLabel averageRatingToDisplay = createAverageRatingPhrase();
        JScrollPane scrollingDescription = createGameDescriptionArea();
        JLabel listOfRatingsToDisplay = createListOfRatings();
        JLabel tagsTitle = createListOfTags();
        JScrollPane scrollingReviews = createReviewArea();
        JScrollPane topScoresScrollPane = createTopScoresArea();

        //simple component generation
        JLabel nameOfGame = new JLabel(myGameName);
        nameOfGame.setForeground(Color.WHITE);
        JLabel ratingsTitle = new JLabel("User Ratings for " + myGameName + ":");
        ratingsTitle.setForeground(Color.WHITE);
        JLabel reviewsTitleLabel = new JLabel("User Submitted Reviews of this Game: ");
        reviewsTitleLabel.setForeground(Color.WHITE);
        JLabel asterisk = new JLabel("*");
        asterisk.setForeground(Color.WHITE);
        JLabel unlinkReminder = new JLabel("*Remember to unlink your social media account(s) " +
                "before you end your Arcade session!");
        unlinkReminder.setForeground(Color.WHITE);

        //add components to panel
        myPanel.add(nameOfGame, "wrap, span, align center");
        myPanel.add(profilePic, "grow, span, align center");
        myPanel.add(averageRatingToDisplay, "wrap, align center, span");
        myPanel.add(playButton, "wrap, grow, span, align center");
        myPanel.add(tweetBut, "grow, span, align center, split 3");
        myPanel.add(statusPostBut, "grow, span, align center");
        myPanel.add(asterisk, "align center, wrap, span");
        myPanel.add(tagsTitle, "split 3, flowy, align center");
        myPanel.add(myListOfTagsToDisplay, "align center");
        myPanel.add(topScoresScrollPane, "grow, span, align center");
        myPanel.add(scrollingDescription, "wrap, grow, align center, span");
        myPanel.add(reviewsTitleLabel, "wrap, span, align center");
        myPanel.add(scrollingReviews, "grow, span, align center, wrap");
        myPanel.add(ratingsTitle, "split 2, alignx");
        myPanel.add(listOfRatingsToDisplay, "wrap, align center, span, grow");
        myPanel.add(writeReviewAndRatingBut, "grow, span, align center, wrap");
        myPanel.add(unlinkReminder, "span, align left");

        return myPanel;

    }

    private JScrollPane createTopScoresArea () {
        myUsersAndScoresList = getArcade().getModelInterface().getGameHighScores(myGameName);
        String usersAndScoresString = "Top Scorers:\n\n";
        for (int i = 0; i < myUsersAndScoresList.size(); i++) {
            usersAndScoresString +=
                    i + 1 + ". " + myUsersAndScoresList.get(i)[0] + "- " +
                            myUsersAndScoresList.get(i)[1] + "\n";
        }
        myTopScoresArea =
                new JTextArea(usersAndScoresString, myTopScoresAreaHeight, myTopScoresAreaWidth);
        myTopScoresArea.setLineWrap(true);
        myTopScoresArea.setWrapStyleWord(true);
        myTopScoresArea.setEditable(false);
        JScrollPane topScoresScrollPane = new JScrollPane(myTopScoresArea);
        return topScoresScrollPane;
    }

    private JScrollPane createReviewArea () {
        String stringOfReviews = "";
        for (int i = 0; i < myListOfReviews.size(); i++) {
            stringOfReviews += (i + 1) + ". " + myListOfReviews.get(i) + "\n";
        }
        myReviewsArea =
                new JTextArea(stringOfReviews, myGameReviewsAreaHeight, myGameReviewsAreaWidth);
        myReviewsArea.setLineWrap(true);
        myReviewsArea.setWrapStyleWord(true);
        myReviewsArea.setEditable(false);
        JScrollPane scrollingReviews = new JScrollPane(myReviewsArea);
        return scrollingReviews;
    }

    private JLabel createListOfTags () {
        int amountOfTotalCharacters = 0;
        String stringOfTags = "";
        for (int i = 0; i < myListOfTags.size(); i++) {
            amountOfTotalCharacters += myListOfTags.get(i).length();
            stringOfTags += myListOfTags.get(i) + ", ";
            if (i == myListOfTags.size() - 1) {
                stringOfTags = stringOfTags.substring(0, amountOfTotalCharacters + 2 * i);
            }
        }
        JLabel tagsTitle = new JLabel("This game has been tagged as: ");
        tagsTitle.setForeground(Color.WHITE);
        myListOfTagsToDisplay = new JLabel(stringOfTags);
        myListOfTagsToDisplay.setForeground(Color.WHITE);
        return tagsTitle;
    }

    private JLabel createListOfRatings () {
        String stringOfRatingsCommas = "";
        int tenCount = 0;
        for (int i = 0; i < myListOfRatings.size(); i++) {
            if (myListOfRatings.get(i) == myTen) {
                tenCount += 1;
            }
            stringOfRatingsCommas += myListOfRatings.get(i) + ", ";
            if (i == myListOfRatings.size() - 1) {
                stringOfRatingsCommas =
                        stringOfRatingsCommas.substring(0, myListOfRatings.size() + 2 * i +
                                                           tenCount);
            }
        }
        JLabel listOfRatingsToDisplay = new JLabel(stringOfRatingsCommas);
        listOfRatingsToDisplay.setForeground(Color.WHITE);
        return listOfRatingsToDisplay;
    }

    private JScrollPane createGameDescriptionArea () {
        String gameDescription =
                getArcade().getModelInterface().getGame(myGameName).getDescription();
        JTextArea descriptionToDisplay =
                new JTextArea("Game Overview: " + "\n" + gameDescription, myGameDescriptionHeight,
                              myGameDescriptionWidth);
        descriptionToDisplay.setLineWrap(true);
        descriptionToDisplay.setWrapStyleWord(true);
        descriptionToDisplay.setEditable(false);
        JScrollPane scrollingDescription = new JScrollPane(descriptionToDisplay);
        return scrollingDescription;
    }

    private JLabel createAverageRatingPhrase () {
        String averageRating =
                "Average Rating: " +
                        getArcade().getModelInterface().getGame(myGameName).getAverageRating();
        if ((averageRating.length() - myLengthOfAvgRatingTitle) > myLengthOfAverageRatingNum) {
            averageRating = averageRating.substring(0, myLengthOfAverageRatingPhrase);
        }
        JLabel averageRatingToDisplay = new JLabel(averageRating);
        averageRatingToDisplay.setForeground(Color.WHITE);
        return averageRatingToDisplay;
    }

    private JLabel establishGameProfilePic () {
        myGameIcon = new ImageIcon(getArcade().
                           getModelInterface().getGame(myGameName).getImage());
        JLabel profilePic = new JLabel(myGameIcon);
        return profilePic;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand().equals(PLAY)) {
            getArcade().getModelInterface().getGame(myGameName).runGame();
        }
        if (e.getActionCommand().equals(REVIEW)) {
            getArcade().replacePanel("EnterReviewRating");
        }
        if (e.getActionCommand().equals(UPDATE_STATUS)) {
            String theStatusPost = myQuickSocialMediaPost;
            boolean statusPostSuccessful =
                    getArcade().getModelInterface().sendPost(getArcade().getUsername(),
                                                             theStatusPost);
            if (statusPostSuccessful) {
                JOptionPane.showMessageDialog(null, "Status Update was successful!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Status Update failed Q_Q");
            }
        }
        if (e.getActionCommand().equals(TWEET)) {
            String theTweet = myQuickSocialMediaPost;
            boolean tweetSuccessful =
                    getArcade().getModelInterface().sendTweet(getArcade().getUsername(),
                                                              theTweet);
            if (tweetSuccessful) {
                JOptionPane.showMessageDialog(null, "Tweet was successful!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Tweet failed Q_Q");
            }
        }
    }
}
