package arcade.gui.panel.search;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import arcade.usermanager.UserProfile;


/**
 * 
 * @author Michael Deng & Kannan Raju
 * 
 */
public class NormSearchPanel extends ASearchPanel implements ActionListener{

//    private static final int PANEL_WIDTH = 250; //300
//    private static final int PANEL_HEIGHT = 800; // 450
    
    private List<String> myGameList;
    private List<String> myUserList;
    private JList searchedThroughList = new JList();
    private JList userSearchedThroughList = new JList();
    private String gameSelected;
    private String GO = "Go";
    private String USER = "User";
    private String TAG = "Tag";
    
    
    public NormSearchPanel (Arcade a) {
        super(a);
        myGameList = a.getModelInterface().getGameList();
        myUserList = new ArrayList<String>();
        
        List <UserProfile> tempUserList = a.getModelInterface().getAllUsers();
        for (UserProfile user : tempUserList) {
            myUserList.add(user.getUserName());
        }
        
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        
//        myPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        
        MigLayout layout = new MigLayout("", "", "[][][][][][][][][][]push[]");
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
        goButton.setActionCommand(GO);
        goButton.addActionListener(this);
        JButton goToTagSearchButton = new JButton("Search for Games by Tag ");
        goToTagSearchButton.setActionCommand(TAG);
        goToTagSearchButton.addActionListener(this);
        JButton goToUserSearchButton = new JButton("Search for Arcade Users!");
        goToUserSearchButton.setActionCommand(USER);
        goToUserSearchButton.addActionListener(this);
        
        // User Search Section:
        
        
        String[] arrayOfUsers = new String[myUserList.size()];
        for (int i = 0; i < myUserList.size(); i++) {
            arrayOfUsers[i] = myUserList.get(i);
        }
        JList jListOfUsers = new JList(arrayOfUsers);
        jListOfUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListOfUsers.setLayoutOrientation(JList.VERTICAL);   
        jListOfUsers.setVisibleRowCount(3);
        JListSearcher userSearchArea = new JListSearcher("", jListOfUsers);
        searchArea.setColumns(10);

        userSearchedThroughList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userSearchedThroughList.setLayoutOrientation(JList.VERTICAL);
        userSearchedThroughList.setVisibleRowCount(5);
        userSearchedThroughList = jListOfUsers;
        JScrollPane userSearchedListScroller = new JScrollPane(userSearchedThroughList);
        
        JLabel userSearchPrompt = new JLabel("Search for a user by Name:");
        JLabel userListSelectPrompt = new JLabel("Select a user: ");
        JButton userGoButton = new JButton("Go to Profile");
        userGoButton.setActionCommand(USER);
        userGoButton.addActionListener(this);
        
        /*JLabel label = new JLabel();
        label.setText("This is the Norm Search page.");
        label.setForeground(Color.WHITE);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);*/

        myPanel.add(searchPrompt, "wrap");
        myPanel.add(searchArea, "align center, grow, span, wrap");
        myPanel.add(listSelectPrompt, "wrap, grow, span");
        myPanel.add(searchedListScroller, "wrap, grow, span");
        myPanel.add(goButton, "wrap");
        myPanel.add(userSearchPrompt, "wrap");
        myPanel.add(userSearchArea, "align center, grow, span, wrap");
        myPanel.add(userListSelectPrompt, "wrap, grow, span");
        myPanel.add(userSearchedListScroller, "wrap, grow, span");
        myPanel.add(userGoButton);
        //myPanel.add(goToUserSearchButton, "dock south, grow, span");
        myPanel.add(goToTagSearchButton, "dock south, grow, span");

        return myPanel;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand() == GO) {
            gameSelected = (String) searchedThroughList.getSelectedValue();
            if (gameSelected != null) {
                getArcade().saveVariable("GameName", gameSelected);
                getArcade().replacePanel("GameProfile");
            }
        }
        if (e.getActionCommand() == TAG) {
            getArcade().replacePanel("GameTagSearch");
        }
        if (e.getActionCommand() == USER) {
            String userSelected = (String) userSearchedThroughList.getSelectedValue();
            if (userSelected != null){
                getArcade().saveVariable("UserName", userSelected);
                getArcade().replacePanel("UserProfile");
            }
        }       
    }

}
