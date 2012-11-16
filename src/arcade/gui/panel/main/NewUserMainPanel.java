package arcade.gui.panel.main;

import java.awt.BorderLayout;
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
 *
 */
public class NewUserMainPanel extends AMainPanel implements ActionListener {
    private static final String NORTH = "n";
    private static final String SOUTH = "s";
    private static final String EAST = "e";
    private static final String WEST = "w";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel wrongPassword;
    private GridBagConstraints c;
    ArcadePanel myPanel;


    public NewUserMainPanel (Arcade a) {
        super(a);
        createPanel();
    }

    @Override
    public ArcadePanel createPanel () {
        myPanel = initializeNewPanel();
        myPanel.setBackground(Color.YELLOW);
        System.out.println("NewUserMainPanel");
        
        myPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        myPanel = addField("Username", 17, 0, 0, SOUTH);
        myPanel = addLoginButton();

        System.out.println("LoginMainPanel");

        return myPanel;
    }

    private ArcadePanel addField (String name, int length, int x, int y, String direction) {
        JTextField textField = new JTextField(17);
        textField.setName(name);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = x;
        c.gridy = y;
        myPanel.add(textField, c);
        JLabel label = new JLabel(name);
        label.setLabelFor(textField);
        c.fill = GridBagConstraints.HORIZONTAL;
        if (NORTH.equals(direction)) {
            c.gridx = x;
            c.gridy = y-1;
        }
        else if (SOUTH.equals(direction)) {
            c.gridx = x;
            c.gridy = y+1;
        }
        else if (EAST.equals(direction)) {
            c.gridx = x+1;
            c.gridy = y;
        }
        else if (WEST.equals(direction)) {
            c.gridx = x-1;
            c.gridy = y;
        }
        else {
            c.gridx = x;
            c.gridy = y;
        }
        myPanel.add(label, c);

        return myPanel;
    }

    private ArcadePanel addWrongPasswordLabel () {
        wrongPassword = new JLabel("Wrong Username or Password.");
        wrongPassword.setVisible(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        myPanel.add(wrongPassword, c);
        return myPanel;
    }

    private ArcadePanel addLoginButton () {
        JButton loginButton = new JButton("Login");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 3;

        loginButton.setActionCommand("test");
        loginButton.addActionListener(this);

        myPanel.add(loginButton, c);

        return myPanel;
    }

    private ArcadePanel addNewUserButton () {
        JButton newUserButton = new JButton("New User");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 4;

        newUserButton.setActionCommand("");
        newUserButton.addActionListener(this);

        myPanel.add(newUserButton, c);

        return myPanel;
    }

    private ArcadePanel addUserNameField () {
        usernameField = new JTextField(17);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        myPanel.add(usernameField, c);

        JLabel label = new JLabel("Username: ");
        label.setLabelFor(usernameField);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        myPanel.add(label, c);

        return myPanel;
    }

    private ArcadePanel addPasswordField () {
        passwordField = new JPasswordField(17);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        myPanel.add(passwordField, c);

        JLabel label = new JLabel("Password: ");
        label.setLabelFor(passwordField);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        myPanel.add(label, c);

        return myPanel;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("test".equals(cmd)){
            JTextField textF = (JTextField) myPanel.getComponent("Username");
            System.out.println(textF.getText());
        }
        else if ("".equals(cmd)){
            newUser();
        }

    }

    private void newUser () {
        System.out.println("Attempt New User");
        this.getArcade().replacePanel("NewUser");
    }

    private void login () {
        System.out.println("Attempt Login");
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        if (this.getArcade().getUserManager().loginUser(username, password)) {
            this.getArcade().replacePanel("MainHome");
        }
        else {
            wrongPassword.setVisible(true);
        }
    }
}
