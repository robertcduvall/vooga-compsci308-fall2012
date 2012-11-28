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
 *
 */
public class NewUserMainPanel extends AMainPanel implements ActionListener {

    ArcadePanel myPanel;

    private HintTextField username;
    private HintTextField pswd1;
    private HintTextField pswd2;
    private HintTextField fn;
    private HintTextField ln;

    private JLabel message;

    private String usernameStr;
    private String pswd1Str;
    private String pswd2Str;
    private String fnStr;
    private String lnStr;

    public NewUserMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        myPanel = initializeNewPanel();
        System.out.println("NewUserMainPanel");

        JLabel blank = new JLabel("");

        username = new HintTextField("Username");
        username.setPreferredSize(new Dimension(200, 20));
        pswd1 = new HintTextField("Password");
        pswd1.setPreferredSize(new Dimension(200, 20));
        pswd2 = new HintTextField("Re-enter Password");
        pswd2.setPreferredSize(new Dimension(200, 20));
        fn = new HintTextField("First Name");
        fn.setPreferredSize(new Dimension(200, 20));
        ln = new HintTextField("Last Name");
        ln.setPreferredSize(new Dimension(200, 20));

        JButton submit = new JButton("Submit");
        submit.addActionListener(this);

        message = new JLabel("");
        message.setForeground(Color.WHITE);

        myPanel.setLayout(new MigLayout("", "[grow]", "[60][]10[]10[]10[]10[]30[]5[]"));

        myPanel.add(blank, "align center, wrap");
        myPanel.add(username, "align center, wrap");
        myPanel.add(pswd1, "align center, wrap");
        myPanel.add(pswd2, "align center, wrap");
        myPanel.add(fn, "align center, wrap");
        myPanel.add(ln, "align center, wrap");
        myPanel.add(submit, "wrap, align center");
        myPanel.add(message, "align center");

        return myPanel;
    }

    @Override
    public void actionPerformed (ActionEvent e) {

        // get input data
        usernameStr = username.getText();
        pswd1Str = pswd1.getText();
        pswd2Str = pswd2.getText();
        fnStr = fn.getText();
        lnStr = ln.getText();

        // check that all data is filled
        if (!usernameStr.isEmpty() && !pswd1Str.isEmpty() && !pswd2Str.isEmpty() &&
            !fnStr.isEmpty() && !lnStr.isEmpty()) {

            // check password match
            if (pswd1Str.equals(pswd2Str) && !pswd1Str.isEmpty() && !pswd2Str.isEmpty()) {
                message.setText("Processing...");

                // execute server call
                if (getArcade().getModelInterface().executeNewUser(usernameStr, pswd1Str, fnStr, lnStr)){
                    
                    // new user created
                    getArcade().setUsername(usernameStr);
                    
                    getArcade().replacePanel("NormUser");
                    getArcade().replacePanel("NormMain");
                    getArcade().replacePanel("NormNav");
                    getArcade().replacePanel("NormSearch");
                    
                    
                    
                }else {
                    // new user not created
                    message.setText("Error. Username is invalid. Please try again.");
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
