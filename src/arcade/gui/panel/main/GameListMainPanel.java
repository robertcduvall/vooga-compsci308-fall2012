package arcade.gui.panel.main;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

public class GameListMainPanel extends AMainPanel {

    private List<String> myGameList;
    private List<Image> myGameProfilePictures;
    
    public GameListMainPanel (Arcade a) {
        super(a);
         myGameList = a.getGameManager().getListOfGames();
         myGameProfilePictures = new ArrayList<Image>();
         for (int i = 0; i < myGameList.size(); i++) {
             myGameProfilePictures.add(a.getGameManager().getGameProfilePicture(myGameList.get(i)));            
         }
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        for (int i = 0; i < myGameList.size(); i++) {
            JButton button = new JButton(myGameList.get(i));
            myPanel.add(button);
            
        }
        JButton button = new JButton("Does this work I wonder");
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.LEADING);
        myPanel.add(button);
        return myPanel;
    }

}
