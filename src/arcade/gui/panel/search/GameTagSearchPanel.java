package arcade.gui.panel.search;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 * Search Panel to find Games by Tag. The user types 
 * in a tag, and is returned a list of those games which
 * possess that tag.
 * @author Kannan Raju
 *
 */
public class GameTagSearchPanel extends ASearchPanel implements ActionListener {

    private static final String USER = "User";
    private static final String NAME = "Name";
    private static final String SEARCH = "Search";
    private List<String> myGameList;
    private final int myTagEntryFieldLength = 10;
    private JTextField myTagEntryField = new JTextField(myTagEntryFieldLength);

    /**
     * The Constructor for this class.
     * @param a The Arcade Object being passed down
     */
    public GameTagSearchPanel (Arcade a) {
        super(a);
    }
    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        MigLayout layout = new MigLayout("flowy", "[50%, grow]", "[][][]40[][]push[][]");
        myPanel.setLayout(layout);
        myPanel.setBackground(Color.LIGHT_GRAY);

        JLabel searchPrompt = new JLabel("Enter a tag to filter by: ");
        JLabel recomendationPrompt = new JLabel("e.g. fun, shooter, etc.");

        //Creating buttons to navigate to other search panels
        JButton searchByTagBut = new JButton(SEARCH);
        searchByTagBut.addActionListener(this);
        searchByTagBut.setActionCommand(SEARCH);
        JButton goToGameNameSearchButton = new JButton("Search for Games by Name");
        goToGameNameSearchButton.setActionCommand(NAME);
        goToGameNameSearchButton.addActionListener(this);
        JButton goToUserSearchButton = new JButton("Search for Arcade Users!");
        goToUserSearchButton.setActionCommand(USER);
        goToUserSearchButton.addActionListener(this);

        //add components to panel
        myPanel.add(searchPrompt, "align center");
        myPanel.add(myTagEntryField, "align center, grow");
        myPanel.add(searchByTagBut, "align center, grow");
        myPanel.add(recomendationPrompt, "align center");
        myPanel.add(goToUserSearchButton, "dock south, grow");
        myPanel.add(goToGameNameSearchButton, "dock south, grow");

        return myPanel;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand() == SEARCH) {
            myGameList = getArcade().getModelInterface().
                    getGameListByTagName(myTagEntryField.getText());
            String[] arrayOfGames = new String[myGameList.size()];
            for (int i = 0; i < myGameList.size(); i++) {
                arrayOfGames[i] = myGameList.get(i);
            }
            getArcade().saveVariable("taggedGames", arrayOfGames);
            getArcade().saveVariable("tag", myTagEntryField.getText());
            getArcade().replacePanel("GameTagCompleteSearch");
        }
        if (e.getActionCommand() == NAME) {
            getArcade().replacePanel("NormSearch");
        }
        if (e.getActionCommand() == USER) {
            getArcade().replacePanel("UserSearch");
        }
    }
}
