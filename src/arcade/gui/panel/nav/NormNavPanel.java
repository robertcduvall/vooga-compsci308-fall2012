package arcade.gui.panel.nav;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * This will be the top navigation panel. This needs to be implemented.
 * 
 * @author Michael Deng
 * 
 */
public class NormNavPanel extends ANavPanel {

    private static final int BTN_WIDTH = 150;
    private static final int BTN_HEIGHT = 30;
    
    public NormNavPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();

        JButton allgamesBut = new JButton("All Games");
        allgamesBut.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        JButton allusersBut = new JButton("All Users");
        allusersBut.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        JButton myprofileBut = new JButton("My Profile");
        myprofileBut.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        JButton messagecenterBut = new JButton("Message Center");
        messagecenterBut.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        JButton twitterBut = new JButton("Social Media Connect");
        twitterBut.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));

        allgamesBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().replacePanel("GameList");
            }
        });

        allusersBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().replacePanel("UserList");
            }
        });

        myprofileBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().saveVariable("UserName", getArcade().getUsername());
                getArcade().replacePanel("UserProfile");
            }
        });

        messagecenterBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {

                getArcade().replacePanel("MessageCenter");
            }
        });
        
        twitterBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {

                getArcade().replacePanel("SocialMedia");
            }
        });

        myPanel.setLayout(new MigLayout("", "[c, grow][c, grow][c, grow][c, grow][c, grow]", "[c]"));

        myPanel.add(allgamesBut, "align center");
        myPanel.add(allusersBut, "align center");
        myPanel.add(myprofileBut, "align center");
        myPanel.add(messagecenterBut, "align center");
        myPanel.add(twitterBut, "align center");

        return myPanel;
    }

}
