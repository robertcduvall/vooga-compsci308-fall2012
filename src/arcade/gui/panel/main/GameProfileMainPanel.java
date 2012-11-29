package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public GameProfileMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        gameName = (String) getArcade().getVariable("GameName");
        myPanel.setBackground(Color.WHITE);

        
        
        MigLayout layout = new MigLayout();
        myPanel.setLayout(layout);

        System.out.println(myPanel.getSize());

        myPanel = addPlayButton(myPanel);
        myPanel = addGameName(myPanel);
        myPanel = addGameProfilePicture(myPanel);
        
        return myPanel;

    }

    private ArcadePanel addGameName (ArcadePanel myPanel) {
        JLabel nameOfGame = new JLabel(gameName);
        myPanel.add(nameOfGame);
        return myPanel;
    }
    
    private ArcadePanel addGameProfilePicture (ArcadePanel myPanel) {
        ImageIcon icon = new ImageIcon("src/arcade/gui/images/Arcade_logo2.png");
        JLabel profilePic = new JLabel(icon);
        myPanel.add(profilePic, "wrap");

        return myPanel;
    }
    
    private ArcadePanel addPlayButton (ArcadePanel myPanel) {
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().getModelInterface().getGame(gameName).runGame();
            }

        });

        myPanel.add(playButton);
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
        myPanel.add(label);

        return myPanel;
    }

    /*private ArcadePanel addListOfRatingsAndReviews (ArcadePanel myPanel) {
        
    }
    */
}
