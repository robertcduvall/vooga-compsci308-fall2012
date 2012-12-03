package arcade.gui.panel.search;

import java.awt.Color;
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

public class UserSearchPanel extends ASearchPanel implements ActionListener{

    private List<String> myUserList;
    private JList userSearchedThroughList = new JList();
    private String GAME = "Game";
    private String USER = "User";
    
    public UserSearchPanel (Arcade a) {
        super(a);      
        myUserList = new ArrayList<String>();
        
        List <UserProfile> tempUserList = a.getModelInterface().getAllUsers();
        for (UserProfile user : tempUserList) {
            myUserList.add(user.getUserName());
        }
    }
    
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.LIGHT_GRAY);
        
        MigLayout layout = new MigLayout("flowy", "[50%, grow]", "[][][][][]push[]");
        myPanel.setLayout(layout);
        
        String[] arrayOfUsers = new String[myUserList.size()];
        for (int i = 0; i < myUserList.size(); i++) {
            arrayOfUsers[i] = myUserList.get(i);
        }
        JList jListOfUsers = new JList(arrayOfUsers);
        jListOfUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListOfUsers.setLayoutOrientation(JList.VERTICAL);   
        jListOfUsers.setVisibleRowCount(5);
        JListSearcher userSearchArea = new JListSearcher("", jListOfUsers);
        userSearchArea.setColumns(10);

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
        JButton goToGameNameSearchButton = new JButton("Search for Games by Name");
        goToGameNameSearchButton.setActionCommand(GAME);
        goToGameNameSearchButton.addActionListener(this);
        
        
        
        myPanel.add(userSearchPrompt, "align center");
        myPanel.add(userSearchArea, "align center, grow");
        myPanel.add(userListSelectPrompt, "align center");
        myPanel.add(userSearchedListScroller, "align center, grow");
        myPanel.add(userGoButton, "align center, grow");
        myPanel.add(goToGameNameSearchButton, "align center, dock south, grow");
        
        
        
        
        return myPanel;
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand() == GAME) {
            getArcade().replacePanel("NormSearch");
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
