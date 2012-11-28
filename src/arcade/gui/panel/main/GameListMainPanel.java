package arcade.gui.panel.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * A subclass of the Main Panel which displays a short, scrolling list
 * of all the Games available in the Arcade. When a Game is selected,
 * the User is taken to the Game's Profile Page.
 * @author Kannan
 *
 */
public class GameListMainPanel extends AMainPanel implements ScrollPaneConstants{

    private List<String> myGameList;
    private List<Image> myGameProfilePictures;
    private String gameSelected;
    private JList gameList;
    
    public GameListMainPanel (Arcade a) {
        super(a);
         //myGameList = a.getGameManager().getListOfGames();
         /*myGameProfilePictures = new ArrayList<Image>();
         for (int i = 0; i < myGameList.size(); i++) {
             myGameProfilePictures.add(a.getGameManager().getGameProfilePicture(myGameList.get(i)));            
         }*/
         createPanel();
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        /*String[] arrayOfGames = new String[myGameList.size()];
        for (int i = 0; i < myGameList.size(); i++) {
            arrayOfGames[i] = myGameList.get(i);
        }*/
        String[] theList = new String[10];
        for (int i = 0; i < 10; i++) {
            theList[i] = "" + i;
        }
        
        MigLayout layout = new MigLayout();
        myPanel.setLayout(layout);

        JList listOfGames = new JList(theList);
        listOfGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfGames.setLayoutOrientation(JList.VERTICAL);
        listOfGames.setVisibleRowCount(3);
        gameList = listOfGames;
        gameList.setPreferredSize(new Dimension(300, 300));
        JScrollPane listScroller = new JScrollPane(gameList, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
        
        
        JLabel label = new JLabel("Select a Game to View: ");
        label.setForeground(Color.WHITE);
        label.setLabelFor(listScroller);
        myPanel.add(label, "dock center");
        myPanel.add(listScroller, "wrap");
        
        
        JButton goButton = new JButton("Go!");

        goButton.setActionCommand("Go!");
        goButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                gameSelected = (String) gameList.getSelectedValue();
                if (gameSelected != null) {
                    getArcade().saveVariable("GameName", gameSelected);
                    getArcade().replacePanel("GameProfile");
                }
            }
              
          });
        
        myPanel.add(goButton, "span 2");

        return myPanel;
    }
    
}
