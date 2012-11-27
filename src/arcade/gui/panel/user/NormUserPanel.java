package arcade.gui.panel.user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    public NormUserPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        System.out.println("NormUserPanel");

        // use the username to pull the user's first name
        // TODO
        String user = getArcade().getUsername();

        JButton logoutBut = new JButton("Logout");

        JLabel welcomeLabel = new JLabel("You are current logged in as: " + user, JLabel.CENTER);
        welcomeLabel.setForeground(Color.WHITE);

        logoutBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                // execute arcade logout
                // TODO

                getArcade().setUsername("");

                getArcade().replacePanel("MainDefault");
                getArcade().replacePanel("UserDefault");
                getArcade().replacePanel("SearchDefault");
                getArcade().replacePanel("NavDefault3");

            }

        });

        myPanel.setLayout(new MigLayout("", "[grow]", "[][b]"));

        myPanel.add(logoutBut, "align center, wrap");
        myPanel.add(welcomeLabel, "align center");

        return myPanel;
    }

}
