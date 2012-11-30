package arcade.gui.panel.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * A subclass of the Main Panel which displays a short, scrolling list
 * of all the Games available in the Arcade. When a Game is selected,
 * the User is taken to the Game's Profile Page.
 * @author Kannan
 *
 */
public class GameListMainPanel extends AMainPanel implements ScrollPaneConstants{

    private List<String> myGameList;
    private List<Image> myGameProfilePictures;
    private String gameSelected;
    private JList gameList;
    private ListSelectionModel listSelectionModel;
    private int listIndexCurrentlySelected;
    
    public GameListMainPanel (Arcade a) {
        super(a);
         myGameList = a.getModelInterface().getGameList();

    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        String[] arrayOfGames = new String[myGameList.size()];
        for (int i = 0; i < myGameList.size(); i++) {
            System.out.println(myGameList.get(i));
            arrayOfGames[i] = myGameList.get(i);
        }
        
        MigLayout layout = new MigLayout("align center, fill");
        myPanel.setLayout(layout);

        JList listOfGames = new JList(arrayOfGames);
        listOfGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfGames.setLayoutOrientation(JList.VERTICAL);
        listOfGames.setVisibleRowCount(3);
        gameList = listOfGames;
        /*listSelectionModel = gameList.getSelectionModel();
        listSelectionModel.addListSelectionListener(
                                new SharedListSelectionHandler());*/
        //gameList.setPreferredSize(new Dimension(150, 150));
        JScrollPane listScroller = new JScrollPane(gameList, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
        
        
        JLabel label = new JLabel("Select a Game to View: ");
        label.setForeground(Color.WHITE);
        label.setLabelFor(listScroller);
        
        
        JButton goButton = new JButton("Go!");

        goButton.setActionCommand("Go!");
        goButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                gameSelected = (String) gameList.getSelectedValue();
                if (gameSelected != null) {
                    getArcade().saveVariable("GameName", gameSelected);
                    getArcade().replacePanel("GameProfile");
                }
            }
              
          });
        ImageIcon icon = new ImageIcon("src/arcade/gui/images/ArcadeClassics.jpg");
        JLabel displayPic = new JLabel(icon);
        
        myPanel.add(label, "dock north, span, grow, align center");
        myPanel.add(listScroller, "span, grow");
        myPanel.add(displayPic, "span, grow");
        myPanel.add(goButton, "dock south, span, grow, align center");

        return myPanel;
        /*class SharedListSelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();

                int firstIndex = e.getFirstIndex();
                int lastIndex = e.getLastIndex();
                boolean isAdjusting = e.getValueIsAdjusting();

                if (lsm.isSelectionEmpty()) {
                    output.append(" <none>");
                } else {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            output.append(" " + i);
                        }
                    }
                }
                output.append(newline);
            }
        }*/
    }
    
}


