package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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

    private String gameName = (String) getArcade().getVariable("GameName");
    private GridBagConstraints c;

    public GameProfileMainPanel (Arcade a) {
        super(a);
        createPanel();
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.CYAN);

        myPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        System.out.println(myPanel.getSize());

        myPanel = addPlayButton(myPanel);
        myPanel = addGameName(myPanel);
        myPanel = addGameProfilePicture(myPanel);
        
        return myPanel;

    }

    private ArcadePanel addGameName (ArcadePanel myPanel) {
        JLabel nameOfGame = new JLabel(gameName);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        myPanel.add(nameOfGame, c);
        return myPanel;
    }
    
    private ArcadePanel addGameProfilePicture (ArcadePanel myPanel) {
        ImageIcon icon = new ImageIcon("src/arcade/gui/images/Arcade_logo2.png");
        JLabel profilePic = new JLabel(icon);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;

        myPanel.add(profilePic, c);

        return myPanel;
    }
    
    private ArcadePanel addPlayButton (ArcadePanel myPanel) {
        JButton playButton = new JButton("Play");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().getModelInterface().getGame(gameName).runGame();
            }

        });

        myPanel.add(playButton, c);
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

    private ArcadePanel addAverageRating (ArcadePanel myPanel) {

        JLabel label = new JLabel("Average Rating: " + getArcade().getModelInterface().getGame(gameName).getAverageRatings());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        myPanel.add(label, c);

        return myPanel;
    }

    /*private ArcadePanel addListOfRatingsAndReviews (ArcadePanel myPanel) {
        
    }
    */
}
