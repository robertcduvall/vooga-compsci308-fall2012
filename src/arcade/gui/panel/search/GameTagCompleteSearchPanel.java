package arcade.gui.panel.search;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
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

/**
 * The sister panel to the GameTagSearch- after the search is
 * initiated, the user is sent to this panel. Displays a list of games
 * matching the requested tag (stored through the arcade gui-wide map),
 * as well as a consolation message.
 * @author Kannan Raju
 *
 */
public class GameTagCompleteSearchPanel extends ASearchPanel implements ActionListener {

    private static final String GO = "Go";
    private static final String USER = "User";
    private static final String NAME = "Name";
    private static final String TAG = "Tag";
    private String[] myRetrievedListOfTaggedGames;
    private String myGameSelected;
    private JList myJListOfGames;
    private final int myConsolationTextAreaHeight = 6;
    private final int myConsolationTextAreaWidth = 10;
    private final int myGameListVisibleRowCount = 4;

    /**
     * The constructor for this class.
     * @param a The Arcade Object being passed down
     */
    public GameTagCompleteSearchPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setBackground(Color.LIGHT_GRAY);
        MigLayout layout = new MigLayout("flowy", "[50%, grow]", "[][][]push[][][]");
        myPanel.setLayout(layout);

        myRetrievedListOfTaggedGames = (String[]) getArcade().getVariable("taggedGames");
        JLabel gamesFoundLabel = new JLabel("<html>" + "The following games matched your tag '" +
                ((String)getArcade().getVariable("tag")) + "': " + "</html>");

        //establish internal search panel navigation buttons
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

        //Consolation text area, yea buddy
        JTextArea consolationTextArea = new JTextArea("Think a genre of Game is missing" +
                " from the Arcade? You can create your own game to add! :D",
                myConsolationTextAreaHeight, myConsolationTextAreaWidth);
        consolationTextArea.setLineWrap(true);
        consolationTextArea.setWrapStyleWord(true);
        consolationTextArea.setEditable(false);

        myJListOfGames = new JList(myRetrievedListOfTaggedGames);
        myJListOfGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myJListOfGames.setLayoutOrientation(JList.VERTICAL);
        myJListOfGames.setVisibleRowCount(myGameListVisibleRowCount);
        JScrollPane searchedListScroller = new JScrollPane(myJListOfGames);

        //add components to Panel
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
            myGameSelected = (String) myJListOfGames.getSelectedValue();
            if (myGameSelected != null) {
                getArcade().saveVariable("GameName", myGameSelected);
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
