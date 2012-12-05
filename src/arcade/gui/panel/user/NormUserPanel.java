package arcade.gui.panel.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * This is the normal user panel post-login.
 * This will show:
 * "Welcome, Michael" and then have 2 buttons: Logout and Exit
 * 
 * @author Michael Deng
 * 
 */
public class NormUserPanel extends AUserPanel {

    private static final int BTN_WIDTH = 100;
    private static final int BTN_HEIGHT = 30;

    public NormUserPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();

        String user = getArcade().getUsername();

        JButton logoutBut = new JButton("Logout");
        logoutBut.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));

        JLabel welcomeLabel = new JLabel("You are current logged in as: " + user, JLabel.CENTER);
        welcomeLabel.setForeground(Color.WHITE);

        logoutBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                getArcade().setUsername("");

                getArcade().replacePanel("MainDefault");
                getArcade().replacePanel("UserDefault");
                getArcade().replacePanel("SearchDefault");
                getArcade().replacePanel("NavDefault");
            }
        });

        myPanel.setLayout(new MigLayout("", "[grow]", "[][b]"));

        myPanel.add(logoutBut, "align center, wrap");
        myPanel.add(welcomeLabel, "align center");

        return myPanel;
    }

}
