package arcade.gui.panel.main;

import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;


/**
 * This class is designed to function as a very brief
 * starting guide for a new CS308 Arcade User. Short and
 * sweet and to the point.
 * @author Michael Deng and Kannan Raju
 * 
 */
public class HelpMainPanel extends AMainPanel {
    private JTextArea myHelpTextArea;
    private final int myHelpTextAreaHeight = 20;
    private final int myHelpTextAreaWidth = 60;

    /**
     * The Constructor for this class.
     * @param a The Arcade object being passed down.
     */
    public HelpMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        myPanel.setLayout(new MigLayout("", "[grow]", "[]20[c]"));

        JLabel helpTitle = new JLabel();
        helpTitle.setText("The Help Page -- Your Guide to the CS 308 Arcade");
        helpTitle.setForeground(Color.WHITE);
        helpTitle.setVerticalTextPosition(JLabel.CENTER);
        helpTitle.setHorizontalTextPosition(JLabel.CENTER);

        String helpText =
                "This Arcade supports the playing of various games, the tracking of high scores," +
                " messaging other users, and more! This page will aid you" +
                " in getting started with the" +
                " Arcade. \n\nTo begin using the Arcade and playing games, you must log" +
                " in; if you do not " +
                "have an account, go ahead and create one by clicking the 'New User'" +
                " Button in the top right corner. Upon logging" +
                " in, you will notice the Navigation buttons " +
                "located just above the Main Panel - these will lead you to the major" +
                " features of the Arcade.\n\n" +
                "These Nav buttons will always be available to use at any time while " +
                "you are exploring the interface, as well " +
                "as the informational buttons at the foot of your display.\n\nTo start" +
                " playing games, go to the 'All Games' page " +
                "and select a game. Check out the All Users Tab to see what your friend" +
                "s are up to. You've learned the basics- " +
                "the rest is for you to discover.\n\nPEACE\n-The Arcade Gui Team";

        myHelpTextArea = new JTextArea(helpText, myHelpTextAreaHeight, myHelpTextAreaWidth);
        myHelpTextArea.setLineWrap(true);
        myHelpTextArea.setWrapStyleWord(true);
        myHelpTextArea.setEditable(false);
        JScrollPane scrollingHelpTextArea = new JScrollPane(myHelpTextArea);

        myPanel.add(helpTitle, "align center, wrap");
        myPanel.add(scrollingHelpTextArea, "align center");

        return myPanel;
    }

}
