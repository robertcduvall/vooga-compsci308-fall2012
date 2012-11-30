package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
    
    public GameProfileMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        
        gameName = (String) getArcade().getVariable("GameName");
        listOfReviews = getArcade().getModelInterface().getGame(gameName).getReviews();
        listOfRatings = getArcade().getModelInterface().getGame(gameName).getRatings();
        myPanel.setBackground(Color.YELLOW);

        
        
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

        ImageIcon icon = new ImageIcon("src/arcade/gui/images/Arcade_logo2.png");
        JLabel profilePic = new JLabel(icon);

        for (int i = 0; i < listOfReviews.size(); i++) {
            System.out.println(listOfReviews.get(i));
        }

        //String averageRating = "Average Rating: " + getArcade().getModelInterface().getGame(gameName).getAverageRatings();
        String gameDescription = "Game Overview: " + getArcade().getModelInterface().getGame(gameName).getDescription();
        JLabel descriptionToDisplay = new JLabel("<html>" + gameDescription + "</html>");
        
        JButton writeReviewAndRatingBut = new JButton("Review/Rate this Game");
        
        writeReviewAndRatingBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                
                    getArcade().replacePanel("EnterReviewRating");
            }
              
          });
        
        myPanel.add(descriptionToDisplay);
        myPanel.add(nameOfGame, "align label, wrap");
        myPanel.add(playButton, "grow, span");
        myPanel.add(profilePic, "grow");
        myPanel.add(writeReviewAndRatingBut);
        
        
        return myPanel;

    }

    /*private ArcadePanel addGameDescription (ArcadePanel myPanel) {
        JLabel label = new JLabel("<html>" + getArcade().getGameCenter().getGame(gameName). + "</html>");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        myPanel.add(label, c);

        return myPanel;
    }*/
    /*private ArcadePanel addListOfRatingsAndReviews (ArcadePanel myPanel) {
        
    }
    */
}
