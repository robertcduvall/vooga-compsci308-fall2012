package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * Each individual game's profile page. Contains general assignments
 * so that any game's information can be displayed using this class.
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
        c.gridwidth = 3;
        c.gridheight = 2;
        
        System.out.println(myPanel.getSize());
        
        JButton playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(300, 300));
        myPanel.add(playButton);
        playButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().getGameCenter().getGame(gameName).runGame();
            }
              
          });
        
        
        return myPanel;
    }

}
