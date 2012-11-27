package arcade.gui.panel.nav;

import java.awt.Color;
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

    public NormNavPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();

        JButton allgamesBut = new JButton("All Games");
        JButton allusersBut = new JButton("All Users");
        JButton myprofileBut = new JButton("My Profile");
        JButton messagecenterBut = new JButton("Message Center");

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
                getArcade().replacePanel("UserProfile");
            }
        });

        messagecenterBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {

                getArcade().replacePanel("MessageCenter");
            }
        });

        myPanel.setLayout(new MigLayout("", "[c, grow][c, grow][c, grow][c, grow]", "[c]"));

        myPanel.add(allgamesBut, "align center");
        myPanel.add(allusersBut, "align center");
        myPanel.add(myprofileBut, "align center");
        myPanel.add(messagecenterBut, "align center");

        return myPanel;
    }

}
