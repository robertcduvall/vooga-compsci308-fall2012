package arcade.gui.panel.main;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
        String[] arrayOfGames = new String[myGameList.size()];
        for (int i = 0; i < myGameList.size(); i++) {
            arrayOfGames[i] = myGameList.get(i);
        }
        String[] theList = new String[2];
        theList[0] = "sup";
        theList[1] = "bro";
        JList listOfGames = new JList(theList);
        myPanel.add(listOfGames);
        
        final JButton setButton = new JButton("Set");
        setButton.setActionCommand("Set");
        setButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().replacePanel("GameProfile");
            }
              
          });
        
        return myPanel;
    }

}
