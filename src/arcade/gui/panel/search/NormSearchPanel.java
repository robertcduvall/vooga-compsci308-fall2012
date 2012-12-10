package arcade.gui.panel.search;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import net.miginfocom.swing.MigLayout;
import util.searchbar.JListSearcher;



/**
 * Searches for a game by name. This class uses the util.searchbar
 * class. Searching is case sensitive!
 * @author Michael Deng & Kannan Raju
 * 
 */
public class NormSearchPanel extends ASearchPanel implements ActionListener {

    private static final String GO = "Go";
    private static final String USER = "User";
    private static final String TAG = "Tag";
    private List<String> myGameList;
    private JList mySearchedThroughList = new JList();
    private String myGameSelected;
    private final int myListVisibleRowCount = 5;
    private final int myListSearcherColumnCount = 10;

    /**
     * The Constructor for this class.
     * @param a The Arcade Object being passed down
     */
    public NormSearchPanel (Arcade a) {
        super(a);
        myGameList = a.getModelInterface().getGameList();
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        MigLayout layout = new MigLayout("flowy", "[50%, grow]", "[][][][][]push[][]");
        myPanel.setLayout(layout);
        myPanel.setBackground(Color.LIGHT_GRAY);

        JLabel searchPrompt = new JLabel("Search for a game by Name:");
        JLabel listSelectPrompt = new JLabel("Select a game: ");

        //create the search field
        String[] arrayOfGames = new String[myGameList.size()];
        for (int i = 0; i < myGameList.size(); i++) {
            arrayOfGames[i] = myGameList.get(i);
        }
        JList jListOfGames = new JList(arrayOfGames);
        jListOfGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListOfGames.setLayoutOrientation(JList.VERTICAL);
        jListOfGames.setVisibleRowCount(myListVisibleRowCount);
        JListSearcher searchArea = new JListSearcher("", jListOfGames);
        searchArea.setColumns(myListSearcherColumnCount);

        //make the dynamically updated list based on search field
        mySearchedThroughList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mySearchedThroughList.setLayoutOrientation(JList.VERTICAL);
        mySearchedThroughList.setVisibleRowCount(myListVisibleRowCount);
        mySearchedThroughList = jListOfGames;
        JScrollPane searchedListScroller = new JScrollPane(mySearchedThroughList);

        //establish buttons to navigate between search panels
        JButton goButton = new JButton("Go to Profile");
        goButton.setActionCommand(GO);
        goButton.addActionListener(this);
        JButton goToTagSearchButton = new JButton("Search for Games by Tag ");
        goToTagSearchButton.setActionCommand(TAG);
        goToTagSearchButton.addActionListener(this);
        JButton goToUserSearchButton = new JButton("Search for Arcade Users!");
        goToUserSearchButton.setActionCommand(USER);
        goToUserSearchButton.addActionListener(this);

        //add components to panel
        myPanel.add(searchPrompt, "align center");
        myPanel.add(searchArea, "align center, grow");
        myPanel.add(listSelectPrompt, "align center");
        myPanel.add(searchedListScroller, "align center, grow");
        myPanel.add(goButton, "align center, grow");
        myPanel.add(goToUserSearchButton, "align center, dock south, grow");
        myPanel.add(goToTagSearchButton, "align center, dock south, grow");

        return myPanel;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand() == GO) {
            myGameSelected = (String) mySearchedThroughList.getSelectedValue();
            if (myGameSelected != null) {
                getArcade().saveVariable("GameName", myGameSelected);
                getArcade().replacePanel("GameProfile");
            }
        }
        if (e.getActionCommand() == TAG) {
            getArcade().replacePanel("GameTagSearch");
        }
        if (e.getActionCommand() == USER) {
            getArcade().replacePanel("UserSearch");
        }
    }

}
