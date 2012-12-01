package arcade.gui.panel.search;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import util.searchbar.JListSearcher;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng & Kannan Raju
 * 
 */
public class NormSearchPanel extends ASearchPanel {

    private List<String> myGameList;
    private JList searchedThroughList = new JList();
    private String gameSelected;
    
    
    public NormSearchPanel (Arcade a) {
        super(a);
        myGameList = a.getModelInterface().getGameList();
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        MigLayout layout = new MigLayout();
        myPanel.setLayout(layout);
        
        myPanel.setBackground(Color.LIGHT_GRAY);
        String[] arrayOfGames = new String[myGameList.size()];
        for (int i = 0; i < myGameList.size(); i++) {
            arrayOfGames[i] = myGameList.get(i);
        }
        JList jListOfGames = new JList(arrayOfGames);
        jListOfGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListOfGames.setLayoutOrientation(JList.VERTICAL);   
        jListOfGames.setVisibleRowCount(3);
        JListSearcher searchArea = new JListSearcher("", jListOfGames);
        searchArea.setColumns(10);

        searchedThroughList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchedThroughList.setLayoutOrientation(JList.VERTICAL);
        searchedThroughList.setVisibleRowCount(5);
        searchedThroughList = jListOfGames;
        JScrollPane searchedListScroller = new JScrollPane(searchedThroughList);
        
        JLabel searchPrompt = new JLabel("Search for a game by Name:");
        JLabel listSelectPrompt = new JLabel("Select a game: ");
        JButton goButton = new JButton("Go to Profile");
        JButton goToTagSearchButton = new JButton("Search for Games by tag: ");
        JButton goToUserSearchButton = new JButton("Search for Arcade Users!");
        goButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                gameSelected = (String) searchedThroughList.getSelectedValue();
                if (gameSelected != null) {
                    getArcade().saveVariable("GameName", gameSelected);
                    getArcade().replacePanel("GameProfile");
                }
            }
              
          });
        /*JLabel label = new JLabel();
        label.setText("This is the Norm Search page.");
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);*/

        myPanel.add(searchPrompt, "wrap");
        myPanel.add(searchArea, "align center, grow, span, wrap");
        myPanel.add(listSelectPrompt, "wrap, grow, span");
        myPanel.add(searchedListScroller, "wrap, grow, span");
        myPanel.add(goButton);

        return myPanel;
    }

}
