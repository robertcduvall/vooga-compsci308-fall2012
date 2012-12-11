package arcade.gui.panel.main;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;


/**
 * A subclass of the Main Panel which displays a large scrolling list
 * of all the Games available in the Arcade. When a Game is selected,
 * the User is taken to the Game's Profile Page.
 * 
 * @author Kannan
 * 
 */
public class GameListMainPanel extends AMainPanel implements ScrollPaneConstants {

    private List<String> myGameList;
    private String myGameSelected;
    private JList myGameJList;
    private final int myGameListRowCount = 3;

    /**
     * The constructor for this class. The list of games is
     * instantiated here.
     * @param a The Arcade object passed down
     */
    public GameListMainPanel (Arcade a) {
        super(a);
        myGameList = a.getModelInterface().getGameList();

    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        MigLayout layout = new MigLayout("", "50[center]", "[][][][250, grow]");
        myPanel.setLayout(layout);

        JScrollPane listScroller = createListOfGamesDisplayed();

        JLabel gameSelectLabel = new JLabel("Select a Game to View: ");
        gameSelectLabel.setForeground(Color.WHITE);
        gameSelectLabel.setLabelFor(listScroller);

        JButton goButton = new JButton("Go!");
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                myGameSelected = (String) myGameJList.getSelectedValue();
                if (myGameSelected != null) {
                    getArcade().saveVariable("GameName", myGameSelected);
                    getArcade().replacePanel("GameProfile");
                }
            }
        });

        ImageIcon icon = new ImageIcon("src/arcade/gui/images/ArcadeClassics2.jpg");
        JLabel displayPic = new JLabel(icon);

        //add components to panel
        myPanel.add(displayPic, "align center, wrap");
        myPanel.add(goButton, "grow, align center, wrap");
        myPanel.add(gameSelectLabel, "align center, wrap");
        myPanel.add(listScroller, "grow");

        return myPanel;

    }

    private JScrollPane createListOfGamesDisplayed () {
        String[] arrayOfGames = new String[myGameList.size()];
        for (int i = 0; i < myGameList.size(); i++) {
            System.out.println(myGameList.get(i));
            arrayOfGames[i] = myGameList.get(i);
        }
        JList listOfGames = new JList(arrayOfGames);
        listOfGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfGames.setLayoutOrientation(JList.VERTICAL);
        listOfGames.setVisibleRowCount(myGameListRowCount);
        myGameJList = listOfGames;
        JScrollPane listScroller =
                new JScrollPane(myGameJList, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
        return listScroller;
    }

}
