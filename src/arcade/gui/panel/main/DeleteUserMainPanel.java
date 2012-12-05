package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

public class DeleteUserMainPanel extends AMainPanel implements ActionListener {

    private static String CONFIRM = "Confirm Deletion :(";
    private static String CANCEL = "Cancel Deletion! :O";

    private JPasswordField passwordField;
    private JLabel wrongPassword;
    private GridBagConstraints c;
    
    public DeleteUserMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();


        myPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        myPanel = addConfirmButton(myPanel);
        myPanel = addPasswordField(myPanel);
        myPanel = addWrongPasswordLabel(myPanel);
        myPanel = addCancelButton(myPanel);


        return myPanel;
    }

    private ArcadePanel addWrongPasswordLabel (ArcadePanel myPanel) {
        wrongPassword = new JLabel("<html>Wrong Password. Do you still<br/>want to <" +
        		"b><i>PERMANENTLY</i></b> delete<br/> your profile?</html>");
        wrongPassword.setForeground(Color.WHITE);
        wrongPassword.setVisible(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        myPanel.add(wrongPassword, c);
        return myPanel;
    }

    private ArcadePanel addConfirmButton (ArcadePanel myPanel) {
        JButton loginButton = new JButton(CONFIRM);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 3;

        loginButton.setActionCommand(CONFIRM);
        loginButton.addActionListener(this);

        myPanel.add(loginButton, c);

        return myPanel;
    }

    /**
     * @param myPanel
     * @return
     */
    private ArcadePanel addCancelButton (ArcadePanel myPanel) {
        JButton loginButton = new JButton(CANCEL);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 3;

        loginButton.setActionCommand(CANCEL);
        loginButton.addActionListener(this);

        myPanel.add(loginButton, c);

        return myPanel;
    }

    private ArcadePanel addPasswordField (ArcadePanel myPanel) {
        passwordField = new JPasswordField(17);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        myPanel.add(passwordField, c);

        JLabel label = new JLabel("<html>Enter Password: <br/>(Profile Deletion is PERMANANT)</html>");
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

        if (CONFIRM.equals(cmd)) {
            deleteUser();
        }
        else if (CANCEL.equals(cmd)) {
            getArcade().replacePanel("UserProfile");
        }
    }

    private void deleteUser () {
        System.out.println("Attempt deletion...");
        String username = (String) getArcade().getVariable("UserName");
        char[] password = passwordField.getPassword();
        String passwordStr = new String(password);
        if (getArcade().getModelInterface().deleteUser(username, passwordStr)) {
            getArcade().setUsername("");
            getArcade().replacePanel("MainDefault");
            getArcade().replacePanel("UserDefault");
            getArcade().replacePanel("SearchDefault");
            getArcade().replacePanel("NavDefault");
        }
        else {
            wrongPassword.setVisible(true);
        }
    }
}


