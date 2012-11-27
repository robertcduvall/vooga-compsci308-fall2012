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
 *
 */
public class NewUserMainPanelOld extends AMainPanel implements ActionListener {
    private static final String NORTH = "n";
    private static final String SOUTH = "s";
    private static final String EAST = "e";
    private static final String WEST = "w";
    private static final String SUBMIT = "Submit";

    private GridBagConstraints c;
    ArcadePanel myPanel;


    public NewUserMainPanelOld (Arcade a) {
        super(a);
        createPanel();
    }

    @Override
    public ArcadePanel createPanel () {
        myPanel = initializeNewPanel();
        System.out.println("NewUserMainPanel");

        myPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        myPanel = addField("Username", 17, 1, 1, NORTH);
        myPanel = addField("Password", 17, 1, 3, NORTH);
        myPanel = addField("Confirm Password", 17, 1, 5, NORTH);
        myPanel = addSubmitButton();
        myPanel = addLabel("The passwords don't match or " +
                "are shorter than 4 characters.",
                "warning", 5, 5, false);



        return myPanel;
    }

    private ArcadePanel addLabel (String text, String name, int x, int y, boolean visible) {
        JLabel label = new JLabel(text);
        label.setName(name);
        System.out.println(label.getName());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = x;
        c.gridy = y;
        label.setVisible(visible);
        myPanel.add(label, c);


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
        label.setName(name+" :");
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

    private ArcadePanel addSubmitButton () {
        JButton loginButton = new JButton(SUBMIT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 5;

        loginButton.setActionCommand(SUBMIT);
        loginButton.addActionListener(this);

        myPanel.add(loginButton, c);

        return myPanel;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (SUBMIT.equals(cmd)) {
            if (validatePasswords()) {
//                this.getArcade().getSocialCenter().registerUser(
//                        ((JTextField) myPanel.getComponent("Password")).getText(),
//                        ((JTextField) myPanel.getComponent("Password")).getText(), "");
//                this.getArcade().getSocialCenter().logOnUser(
//                        ((JTextField) myPanel.getComponent("Password")).getText(),
//                        ((JTextField) myPanel.getComponent("Password")).getText());
                this.getArcade().replacePanel("UserPanel");
                this.getArcade().replacePanel("MainHome");
                //TODO add picture stuff...
                //Kinda waiting for other people to get stuff done so we can do more work on this...
            }
            else {
                ((JLabel) myPanel.getComponent("warning")).setVisible(true);
            }
        }
    }

    private boolean validatePasswords () {
        return ((((JTextField) myPanel.getComponent("Password")).getText() != null) && 
                (((JTextField) myPanel.getComponent("Confirm Password")).getText() != null) &&
                (((JTextField) myPanel.getComponent("Password")).getText().equals(
                        ((JTextField) myPanel.getComponent("Confirm Password")).getText())) &&
                        ((((JTextField) myPanel.getComponent("Password")).getText().length()) > 4));
    }
}
