package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * Each individual game's profile page. Contains general assignments
 * so that any game's information can be displayed using this class.
 * 
 * @author Kannan
 * 
 */
public class GameProfileMainPanel extends AMainPanel {

    private String gameName;
    private List<String> listOfReviews;
    private List<Integer> listOfRatings;
    private JTextArea reviewsArea;
    private int lengthOfAvgRatingTitle = 16;
    
    public GameProfileMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        
        gameName = (String) getArcade().getVariable("GameName");
        listOfReviews = getArcade().getModelInterface().getGame(gameName).getReviews();
        listOfRatings = getArcade().getModelInterface().getGame(gameName).getRatings();
        Collections.sort(listOfRatings);
        myPanel.setBackground(Color.CYAN);

        
        
        MigLayout layout = new MigLayout();
        myPanel.setLayout(layout);

        System.out.println(myPanel.getSize());
 
        JLabel nameOfGame = new JLabel(gameName);
        
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().getModelInterface().getGame(gameName).runGame();
            }

        });

        //ImageIcon icon = new ImageIcon(getArcade().getModelInterface().getGame(gameName).getImage());
        ImageIcon icon = new ImageIcon("src/arcade/gui/images/Arcade_logo2.png");
        JLabel profilePic = new JLabel(icon);

        String averageRating = "Average Rating: " + getArcade().getModelInterface().getGame(gameName).getAverageRating();
        if ((averageRating.length() - lengthOfAvgRatingTitle) > 4) {
            averageRating = averageRating.substring(0, 20);
        }
        JLabel averageRatingToDisplay = new JLabel(averageRating);
        
        
        String gameDescription = getArcade().getModelInterface().getGame(gameName).getDescription();
        JTextArea descriptionToDisplay = new JTextArea("Game Overview: " + "\n" + gameDescription, 10, 20);
        descriptionToDisplay.setLineWrap(true);
        descriptionToDisplay.setWrapStyleWord(true);
        descriptionToDisplay.setEditable(false);
        JScrollPane scrollingDescription = new JScrollPane(descriptionToDisplay);

        JButton writeReviewAndRatingBut = new JButton("Review/Rate this Game"); 
        writeReviewAndRatingBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                
                    getArcade().replacePanel("EnterReviewRating");
            }
              
          });
        
        String stringOfRatingsCommas = "";
        int tenCount = 0;
        for (int i = 0; i < listOfRatings.size(); i++) {
            if (listOfRatings.get(i) == 10) {
                tenCount += 1;
            }
            stringOfRatingsCommas += listOfRatings.get(i) + ", ";
            if (i == listOfRatings.size() -1) {
                stringOfRatingsCommas = stringOfRatingsCommas.substring(0, listOfRatings.size() + 2*i + tenCount);
            }
        }
        
        JLabel ratingsTitle = new JLabel("User Ratings for " + gameName + ":");
        JLabel listOfRatingsToDisplay = new JLabel(stringOfRatingsCommas);
        
        
        String stringOfReviews = "";
        for (int i = 0; i < listOfReviews.size(); i++) {
            stringOfReviews += (i + 1) + ". " + listOfReviews.get(i) + "\n";
        }
        reviewsArea = new JTextArea(stringOfReviews, 10, 20);
        reviewsArea.setLineWrap(true);
        reviewsArea.setWrapStyleWord(true);
        reviewsArea.setEditable(false);
        JScrollPane scrollingReviews = new JScrollPane(reviewsArea);
        JLabel reviewsTitleLabel = new JLabel("User Submitted Reviews of this Game: ");
        
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
}
