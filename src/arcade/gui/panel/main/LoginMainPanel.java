package arcade.gui.panel.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import edu.cmu.relativelayout.Binding;
import edu.cmu.relativelayout.BindingFactory;
import edu.cmu.relativelayout.RelativeConstraints;
import edu.cmu.relativelayout.RelativeLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;
import arcade.utility.ImageReader;

/**
 * 
 * @author Michael Deng, Robert Bruce
 * 
 */
public class LoginMainPanel extends AMainPanel implements ActionListener {
    
    private static String LOGIN_ACTION = "login";
    private static String NEW_USER_ACTION = "newuser";
    
    public LoginMainPanel (Arcade a) {
        super(a);
        createPanel();
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        
        myPanel.setBackground(Color.GREEN);
        myPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        myPanel = addLoginButton(myPanel, c);
        myPanel = addNewUserButton(myPanel, c);
        myPanel = addUserNameField(myPanel, c);
        myPanel = addPasswordField(myPanel, c);
        

        System.out.println("LoginMainPanel");

        return myPanel;
    }
    
    private ArcadePanel addLoginButton (ArcadePanel myPanel, GridBagConstraints c) {
        JButton loginButton = new JButton("Login");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 3;
        
        loginButton.setActionCommand(LOGIN_ACTION);
        loginButton.addActionListener(this);
        
        myPanel.add(loginButton, c);
        
        return myPanel;
    }
    
    private ArcadePanel addNewUserButton (ArcadePanel myPanel, GridBagConstraints c) {
        JButton newUserButton = new JButton("New User");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 4;
        
        newUserButton.setActionCommand(NEW_USER_ACTION);
        newUserButton.addActionListener(this);
        
        myPanel.add(newUserButton, c);
        
        return myPanel;
    }
    
    private ArcadePanel addUserNameField (ArcadePanel myPanel, GridBagConstraints c) {
        JTextField userNameField = new JTextField(17);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        myPanel.add(userNameField, c);
        
        JLabel label = new JLabel("Username: ");
        label.setLabelFor(userNameField);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        myPanel.add(label, c);
        
        return myPanel;
    }

    private ArcadePanel addPasswordField (ArcadePanel myPanel, GridBagConstraints c) {
        JPasswordField passwordField = new JPasswordField(17);
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
        
        if (LOGIN_ACTION.equals(cmd)){
            login();
        }
        else if (NEW_USER_ACTION.equals(cmd)){
            newUser();
        }
        
    }

    private void newUser () {
        System.out.println("Attempt New User");
        this.getArcade().replacePanel("NewUser");
        // TODO Auto-generated method stub
        
    }

    private void login () {
        System.out.println("Attempt Login");
        // TODO Auto-generated method stub
        
    }

}
