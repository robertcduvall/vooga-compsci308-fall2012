package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.components.HintTextField;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * @author Robert Bruce
 * 
 */
public class NewUserMainPanel extends AMainPanel implements ActionListener {

    ArcadePanel myPanel;

    private HintTextField usernameField;
    private HintTextField passwordField1;
    private HintTextField passwordField2;
    private HintTextField firstNameField;
    private HintTextField lastNameField;

    private JLabel message;

    private String username;
    private String password1;
    private String password2;
    private String firstName;
    private String lastName;

    public NewUserMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        myPanel = initializeNewPanel();

        JLabel blank = new JLabel("");

        usernameField = new HintTextField("Username");
        usernameField.setPreferredSize(new Dimension(200, 20));
        passwordField1 = new HintTextField("Password");
        passwordField1.setPreferredSize(new Dimension(200, 20));
        passwordField2 = new HintTextField("Re-enter Password");
        passwordField2.setPreferredSize(new Dimension(200, 20));
        firstNameField = new HintTextField("First Name");
        firstNameField.setPreferredSize(new Dimension(200, 20));
        lastNameField = new HintTextField("Last Name");
        lastNameField.setPreferredSize(new Dimension(200, 20));

        JButton submit = new JButton("Submit");
        submit.addActionListener(this);

        message = new JLabel("");
        message.setForeground(Color.WHITE);

        myPanel.setLayout(new MigLayout("", "[grow]", "[60][]10[]10[]10[]10[]30[]5[]"));

        myPanel.add(blank, "align center, wrap");
        myPanel.add(usernameField, "align center, wrap");
        myPanel.add(passwordField1, "align center, wrap");
        myPanel.add(passwordField2, "align center, wrap");
        myPanel.add(firstNameField, "align center, wrap");
        myPanel.add(lastNameField, "align center, wrap");
        myPanel.add(submit, "wrap, align center");
        myPanel.add(message, "align center");

        return myPanel;
    }

    @Override
    public void actionPerformed (ActionEvent e) {

        // get input data
        username = usernameField.getText();
        password1 = passwordField1.getText();
        password2 = passwordField2.getText();
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();

        // check that all data is filled
        if (!username.isEmpty() && !password1.isEmpty() && !password2.isEmpty() &&
            !firstName.isEmpty() && !lastName.isEmpty()) {

            // check password match
            if (password1.equals(password2) && !password1.isEmpty() && !password2.isEmpty()) {
                message.setText("Processing...");

                if (username.length() <= 16) {
                    // execute server call
                    if (getArcade().getModelInterface().executeNewUser(username, password1,
                                                                       firstName, lastName)) {

                        // new user created
                        getArcade().setUsername(username);

                        getArcade().replacePanel("NormUser");
                        getArcade().replacePanel("NormMain");
                        getArcade().replacePanel("NormNav");
                        getArcade().replacePanel("NormSearch");

                    }

                    else {
                        // new user not created
                        message.setText("Error. Username is taken. Please try again.");
                    }
                }
                else {
                    message.setText("Max username length is 16.");
                }

            }
            else {
                message.setText("Passwords do not match!");
            }
        }
        else {
            message.setText("All fields are required.");
        }
    }
}
