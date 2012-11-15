package arcade.gui.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * This class creates the frame for
 * 
 * @author Michael Deng
 * 
 */
public class MainFrameCreator {

    private static final String ARCADE_NAME = "Duke CS 308 Arcade";
    private static final int FRAME_HEIGHT = 650;
    private static final int FRAME_WIDTH = 1100;
    private static final boolean FRAME_RESIZABLE = false;

    private Arcade myArcade;
    private ArcadePanel myContentPanel;
    private Map<String, ArcadePanel> myPanels;
    private GridBagConstraints c;

    public MainFrameCreator (Arcade a) {
        myArcade = a;
        myContentPanel = new ArcadePanel(myArcade, "super");
        myPanels = new HashMap<String, ArcadePanel>();

    }

    /**
     * this method is the main method that will
     * set up the jframe.. and get it read to add
     * 
     * @return
     */
    public ArcadeFrame createFrame () {

        prepareContentPanel();

        ArcadeFrame myFrame = new ArcadeFrame(myArcade, ARCADE_NAME, myPanels);

        // set size
        myFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        myFrame.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        myFrame.setResizable(FRAME_RESIZABLE);

        // add myContentPanel
        myFrame.getContentPane().removeAll();
        myFrame.getContentPane().add(myContentPanel);

        // set other things
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLocationRelativeTo(null);

        // myFrame.pack();
        myFrame.setVisible(true);
        return myFrame;
    }

    private void prepareContentPanel () {
        myContentPanel.removeAll();
        myContentPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        myContentPanel.setBackground(Color.BLACK);

        // TEST CODE BELOW
        // myContentPanel.setLayout(new BorderLayout());
        // JButton j = new JButton("the message");
        // j.setPreferredSize(new Dimension(100, 100));
        // myContentPanel.add(j, BorderLayout.CENTER);
        // TEST CODE ENDS

        myContentPanel.setLayout(new GridBagLayout());

        // position all sub panels in content panel (use layout)
        ArcadePanel logoHolder = new ArcadePanel(myArcade, "logoholder");
        logoHolder.setBackground(Color.BLUE);
        logoHolder.setPreferredSize(new Dimension(300, 100));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 0.5;
        c.weighty = .15;
        // c.ipadx = ;
        // c.ipady = ;
        myContentPanel.add(logoHolder, c);

        ArcadePanel blankHolder = new ArcadePanel(myArcade, "blankholder");
        blankHolder.setBackground(Color.BLACK);
        blankHolder.setPreferredSize(new Dimension(500, 100));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 0.5;
        c.weighty = .15;
        // c.ipadx = ;
        // c.ipady = ;
        myContentPanel.add(blankHolder, c);

        ArcadePanel userHolder = new ArcadePanel(myArcade, "userholder");
        userHolder.setBackground(Color.GREEN);
        userHolder.setPreferredSize(new Dimension(300, 100));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 0.3;
        c.weighty = .15;
        // c.ipadx = ;
        // c.ipady = ;
        myContentPanel.add(userHolder, c);

        ArcadePanel navHolder = new ArcadePanel(myArcade, "navholder");
        navHolder.setBackground(Color.ORANGE);
        navHolder.setPreferredSize(new Dimension(1100, 100));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.gridwidth = 6;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = .1;
        // c.ipadx = ;
        // c.ipady = ;
        myContentPanel.add(navHolder, c);

        ArcadePanel mainHolder = new ArcadePanel(myArcade, "mainholder");
        mainHolder.setBackground(Color.RED);
        mainHolder.setPreferredSize(new Dimension(800, 350));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.weightx = .7;
        c.weighty = .1;
        // c.ipadx = ;
        // c.ipady = ;
        myContentPanel.add(mainHolder, c);

        ArcadePanel searchHolder = new ArcadePanel(myArcade, "searchholder");
        searchHolder.setBackground(Color.PINK);
        searchHolder.setPreferredSize(new Dimension(300, 350));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = .3;
        c.weighty = .65;
        // c.ipadx = ;
        // c.ipady = ;
        myContentPanel.add(searchHolder, c);

        ArcadePanel footHolder = new ArcadePanel(myArcade, "footholder");
        footHolder.setBackground(Color.GRAY);
        footHolder.setPreferredSize(new Dimension(1100, 100));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.gridwidth = 6;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = .1;
        // c.ipadx = ;
        // c.ipady = ;
        myContentPanel.add(footHolder, c);

        // add all sub panels to the map
        myPanels.put("main", mainHolder);
        myPanels.put("foot", footHolder);
        myPanels.put("logo", logoHolder);
        myPanels.put("search", searchHolder);
        myPanels.put("user", userHolder);
        myPanels.put("nav", navHolder);
        myPanels.put("blank", blankHolder);

    }

}
