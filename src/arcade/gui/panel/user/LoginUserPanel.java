package arcade.gui.panel.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * This is the user panel during login.
 * It has 2 buttons: Login and New User
 * 
 * @author Michael Deng
 * 
 */
public class LoginUserPanel extends AUserPanel {

    public LoginUserPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        System.out.println("LoginUserPanel");

        JButton loginBut = new JButton("Login");
        JButton newuserBut = new JButton("New User");
        JLabel welcomeLabel = new JLabel("Welcome to the Arcade!", JLabel.CENTER);
        welcomeLabel.setForeground(Color.WHITE);

        loginBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().replacePanel("Login");
            }

        });

        newuserBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                getArcade().replacePanel("NewUser");
            }

        });

        myPanel.setLayout(new MigLayout("", "[grow][grow]", "[c, grow][c, grow]"));

        myPanel.add(loginBut, "align right");
        myPanel.add(newuserBut, "align left, wrap");
        myPanel.add(welcomeLabel, "align center, span");

        myPanel.setPreferredSize(new Dimension(300, 100));

        return myPanel;
    }

}
