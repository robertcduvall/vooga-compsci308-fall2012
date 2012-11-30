package arcade.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.User;

@SuppressWarnings("serial")
public class UserListComponent extends JComponent implements ActionListener {

    private String myUser;
    private ArcadePanel myContainer;
    private JLabel usernameLabel;
    private JButton viewProfileButton;

    public UserListComponent(String theUser, ArcadePanel theContainer){
        myUser = theUser;
        myContainer = theContainer;
        this.setPreferredSize(new Dimension(theContainer.getWidth(), 100));
        //initComponents();
    }

    private void initComponents () {
        usernameLabel = new JLabel(myUser);
        viewProfileButton = new JButton("View Profile");
        this.add(usernameLabel);
        this.add(viewProfileButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.black);
        g.fillRect(5, 5, this.getWidth()-10, this.getHeight()-10);
        
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(myContainer.getWidth(), 100);
    }
    
    @Override
    public void actionPerformed (ActionEvent arg0) {
        // TODO Auto-generated method stub

    }

}
