package arcade.gui.panel.search;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.UserProfile;
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
import net.miginfocom.swing.MigLayout;
import util.searchbar.JListSearcher;

/**
 * This class establishes the user search feature of the arcade,
 * implementing util.searchbar.
 * @author Rob Bruce, refactoring by Kannan
 *
 */
public class UserSearchPanel extends ASearchPanel implements ActionListener {

    private static final String GAME = "Game";
    private static final String USER = "User";
    private List<String> myUserList;
    private JList myUserSearchedThroughList = new JList();
    private final int myListVisibleRowCount = 5;
    private final int myListSearcherColumnCount = 10;

    /**
     * The constructor for this class, instantiates the list of Users.
     * @param a The Arcade Object being passed down
     */
    public UserSearchPanel (Arcade a) {
        super(a);
        myUserList = new ArrayList<String>();
        List <UserProfile> tempUserList = a.getModelInterface().getAllUsers();
        for (UserProfile user : tempUserList) {
            myUserList.add(user.getUserName());
        }
    }
    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.LIGHT_GRAY);
        MigLayout layout = new MigLayout("flowy", "[50%, grow]", "[][][][][]push[]");
        myPanel.setLayout(layout);

        JLabel userSearchPrompt = new JLabel("Search for a user by Name:");
        JLabel userListSelectPrompt = new JLabel("Select a user: ");

        //Create the search field
        String[] arrayOfUsers = new String[myUserList.size()];
        for (int i = 0; i < myUserList.size(); i++) {
            arrayOfUsers[i] = myUserList.get(i);
        }
        JList jListOfUsers = new JList(arrayOfUsers);
        jListOfUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListOfUsers.setLayoutOrientation(JList.VERTICAL);
        jListOfUsers.setVisibleRowCount(myListVisibleRowCount);
        JListSearcher userSearchArea = new JListSearcher("", jListOfUsers);
        userSearchArea.setColumns(myListSearcherColumnCount);

        //make the dynamically updated list based on search field
        myUserSearchedThroughList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myUserSearchedThroughList.setLayoutOrientation(JList.VERTICAL);
        myUserSearchedThroughList.setVisibleRowCount(myListVisibleRowCount);
        myUserSearchedThroughList = jListOfUsers;
        JScrollPane userSearchedListScroller = new JScrollPane(myUserSearchedThroughList);

        //establish buttons to navigate between search panels
        JButton userGoButton = new JButton("Go to Profile");
        userGoButton.setActionCommand(USER);
        userGoButton.addActionListener(this);
        JButton goToGameNameSearchButton = new JButton("Search for Games by Name");
        goToGameNameSearchButton.setActionCommand(GAME);
        goToGameNameSearchButton.addActionListener(this);

        //add components to panel
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
            String userSelected = (String) myUserSearchedThroughList.getSelectedValue();
            if (userSelected != null) {
                getArcade().saveVariable("UserName", userSelected);
                getArcade().replacePanel("UserProfile");
            }
        }
    }
}
