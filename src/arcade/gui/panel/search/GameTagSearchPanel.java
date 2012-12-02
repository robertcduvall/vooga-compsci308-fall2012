package arcade.gui.panel.search;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import net.miginfocom.swing.MigLayout;
import util.searchbar.JListSearcher;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

public class GameTagSearchPanel extends ASearchPanel implements ActionListener{

    private List<String> myGameList;
    private JList searchedByTagList = new JList();
    private JTextField tagEntryField = new JTextField(10);
    private JList listOfTags;
    private String gameSelected;
    private String GO = "Go";
    private String USER = "User";
    private String NAME = "Name";
    private String SEARCH = "Search";
    
    
    public GameTagSearchPanel (Arcade a) {
        super(a);
    }
    
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        MigLayout layout = new MigLayout("", "", "[][][]40[][]push[][]");
        myPanel.setLayout(layout);
        myPanel.setBackground(Color.LIGHT_GRAY);
        
        /*String[] fakeTags = new String[6];
        fakeTags[0] = "Action";
        fakeTags[1] = "Adventure";
        fakeTags[2] = "Blah";
        fakeTags[3] = "yeaboi";
        fakeTags[4] = "trololo";
        fakeTags[5] = "M";
        listOfTags = new JList(fakeTags);
        listOfTags = getArcade().getModelInterface().getListOfGameTags();
        listOfTags.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfTags.setLayoutOrientation(JList.VERTICAL);
        listOfTags.setVisibleRowCount(5);
        JScrollPane tagListScroller = new JScrollPane(listOfTags);*/
        
        JButton searchByTagBut = new JButton("Search");
        searchByTagBut.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                myGameList = getArcade().getModelInterface().getGameListByTagName(tagEntryField.getText());
                String[] arrayOfGames = new String[myGameList.size()];
                for (int i = 0; i < myGameList.size(); i++) {
                    arrayOfGames[i] = myGameList.get(i);
                }
                getArcade().saveVariable("taggedGames", arrayOfGames);
                getArcade().saveVariable("tag", tagEntryField.getText());
                getArcade().replacePanel("GameTagCompleteSearch");
            }
              
          });        
        
        JLabel searchPrompt = new JLabel("Enter a tag to filter by: ");
        
        JLabel recomendationPrompt = new JLabel("e.g. fun, shooter, etc.");
        
        JButton goToGameNameSearchButton = new JButton("Search for Games by Name");
        goToGameNameSearchButton.setActionCommand(NAME);
        goToGameNameSearchButton.addActionListener(this);
        JButton goToUserSearchButton = new JButton("Search for Arcade Users!");
        goToUserSearchButton.setActionCommand(USER);
        goToUserSearchButton.addActionListener(this);

        myPanel.add(searchPrompt, "wrap");
        //myPanel.add(tagListScroller, "wrap");
        myPanel.add(tagEntryField, "wrap");
        myPanel.add(searchByTagBut, "wrap");
        myPanel.add(recomendationPrompt);

        myPanel.add(goToUserSearchButton, "dock south, grow, span");
        myPanel.add(goToGameNameSearchButton, "dock south, grow, span");

        return myPanel;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand() == SEARCH) {
        
        }
        if (e.getActionCommand() == NAME) {
            getArcade().replacePanel("NormSearch");
        }
        if (e.getActionCommand() == USER) {
            getArcade().replacePanel("UserSearch");
        }       
    }
}