package arcade.gui.panel.search;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

public class GameTagCompleteSearchPanel extends ASearchPanel implements ActionListener{

    //TODO:REFACTORING
    private String[] retrievedListOfTaggedGames;
    private String gameSelected;
    private String GO = "Go";
    private String USER = "User";
    private String NAME = "Name";
    private String TAG = "Tag";
    private String SEARCH = "Search";
    private JList jListOfGames;
    
    public GameTagCompleteSearchPanel (Arcade a) {
        super(a);
    }
    
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.LIGHT_GRAY);
        MigLayout layout = new MigLayout("flowy", "[50%, grow]", "[][][]push[][][]");
        myPanel.setLayout(layout);
        retrievedListOfTaggedGames = (String[]) getArcade().getVariable("taggedGames");
        JLabel gamesFoundLabel = new JLabel("The following games matched your tag '"
                + getArcade().getVariable("tag") + "" +"' :");
        
        JButton goButton = new JButton("Go to Profile");
        goButton.setActionCommand(GO);
        goButton.addActionListener(this);
        
        JButton goToGameNameSearchButton = new JButton("Search for Games by Name");
        goToGameNameSearchButton.setActionCommand(NAME);
        goToGameNameSearchButton.addActionListener(this);
        
        JButton goToUserSearchButton = new JButton("Search for Arcade Users!");
        goToUserSearchButton.setActionCommand(USER);
        goToUserSearchButton.addActionListener(this);
        
        JButton goToTagSearchButton = new JButton("Search by Tag Again ");
        goToTagSearchButton.setActionCommand(TAG);
        goToTagSearchButton.addActionListener(this);
        
        JTextArea consolationTextArea = new JTextArea("Think a genre of Game is missing from the Arcade?" +
        		" You can create your own game to add! :D", 10, 10);
        consolationTextArea.setLineWrap(true);
        consolationTextArea.setWrapStyleWord(true);
        consolationTextArea.setEditable(false);
        
        jListOfGames = new JList(retrievedListOfTaggedGames);
        jListOfGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListOfGames.setLayoutOrientation(JList.VERTICAL);   
        jListOfGames.setVisibleRowCount(6);
        JScrollPane searchedListScroller = new JScrollPane(jListOfGames);
        
        myPanel.add(gamesFoundLabel, "align center");
        myPanel.add(searchedListScroller, "align center, grow");
        myPanel.add(goButton, "align center, grow");   
        myPanel.add(consolationTextArea, "align center, grow");
        myPanel.add(goToUserSearchButton, "dock south, grow, align center");
        myPanel.add(goToGameNameSearchButton, "dock south, grow, align center");
        myPanel.add(goToTagSearchButton, "dock south, grow, align center");
        
        
        return myPanel;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand() == GO) {
            gameSelected = (String) jListOfGames.getSelectedValue();
            if (gameSelected != null) {
                getArcade().saveVariable("GameName", gameSelected);
                getArcade().replacePanel("GameProfile");
            }
        }
        if (e.getActionCommand() == NAME) {
            getArcade().replacePanel("NormSearch");
        }
        if (e.getActionCommand() == TAG) {
            getArcade().replacePanel("GameTagSearch");
        }
        if (e.getActionCommand() == USER) {
            getArcade().replacePanel("UserSearch");
        }       
        
    }
}
