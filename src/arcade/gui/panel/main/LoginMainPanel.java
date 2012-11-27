package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Robert Bruce
 *         This is pretty much 100% implemented.
 *         The pane it loads could be a bit prettier though.
 *         Saving that for when everything works well.
 */
public class LoginMainPanel extends AMainPanel implements ActionListener {

    private static String SUBMIT = "Submit";
    private static String BYPASS = "Bypass";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel wrongPassword;
    private GridBagConstraints c;

    public LoginMainPanel (Arcade a) {
        super(a);
        createPanel();
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        System.out.println("LoginMainPanel");


        myPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        myPanel = addSubmitButton(myPanel);
        myPanel = addUserNameField(myPanel);
        myPanel = addPasswordField(myPanel);
        myPanel = addWrongPasswordLabel(myPanel);
        // myPanel = addBypassButton(myPanel);


        return myPanel;
    }

    private ArcadePanel addWrongPasswordLabel (ArcadePanel myPanel) {
        wrongPassword = new JLabel("Wrong Username or Password.");
        wrongPassword.setForeground(Color.WHITE);
        wrongPassword.setVisible(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        myPanel.add(wrongPassword, c);
        return myPanel;
    }

    private ArcadePanel addSubmitButton (ArcadePanel myPanel) {
        JButton loginButton = new JButton(SUBMIT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 3;

        loginButton.setActionCommand(SUBMIT);
        loginButton.addActionListener(this);

        myPanel.add(loginButton, c);

        return myPanel;
    }

    /**
     * @deprecated
     * @param myPanel
     * @return
     */
    private ArcadePanel addBypassButton (ArcadePanel myPanel) {
        JButton loginButton = new JButton(BYPASS);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 3;

        loginButton.setActionCommand(BYPASS);
        loginButton.addActionListener(this);

        myPanel.add(loginButton, c);

        return myPanel;
    }

    private ArcadePanel addUserNameField (ArcadePanel myPanel) {
        usernameField = new JTextField(17);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        myPanel.add(usernameField, c);

        JLabel label = new JLabel("Username: ");
      label.setForeground(Color.WHITE);
        label.setLabelFor(usernameField);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        myPanel.add(label, c);

        return myPanel;
    }

    private ArcadePanel addPasswordField (ArcadePanel myPanel) {
        passwordField = new JPasswordField(17);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        myPanel.add(passwordField, c);

        JLabel label = new JLabel("Password: ");
        label.setForeground(Color.WHITE);
        label.setLabelFor(passwordField);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        myPanel.add(label, c);

        return myPanel;
    }

    public void actionPerformed (ActionEvent e) {
        String cmd = e.getActionCommand();

        if (SUBMIT.equals(cmd)) {
            login();
        }
        else if (BYPASS.equals(cmd)) {
            bypass();
        }
    }

    private void login () {
        System.out.println("Attempt Login");
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String passwordStr = new String(password);
        if (getArcade().getModelInterface().executeLogin(username, passwordStr)) {
            // this.getArcade().replacePanel("MainHome");
            // this.getArcade().replacePanel("NormNav");

            getArcade().setUsername(username);

            getArcade().replacePanel("NormUser");
            getArcade().replacePanel("NormMain");
            getArcade().replacePanel("NormNav");
            getArcade().replacePanel("NormSearch");

        }
        else {
            wrongPassword.setVisible(true);
        }
    }

    /**
     * @deprecated
     */
    private void bypass () {
        this.getArcade().replacePanel("NormNav");
        this.getArcade().replacePanel("MainHome");
    }
}
