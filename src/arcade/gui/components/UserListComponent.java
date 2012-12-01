package arcade.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import edu.cmu.relativelayout.Direction;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.User;
import arcade.utility.ImageReader;

@SuppressWarnings("serial")
public class UserListComponent extends JComponent implements ActionListener {

    private String myUser;
    private String userName;
    private String userInfo;
    private ArcadePanel myContainer;
    private Image profilePic;
    private JLabel usernameLabel;
    private JButton viewProfileButton;
    private JButton sendMessageButton;

    public UserListComponent(String theUser, ArcadePanel theContainer){
        this.setLayout(new MigLayout("", "[500][]5[]", "[30][][30]"));
        myUser = theUser;
        getUserInfo();
        myContainer = theContainer;
        profilePic = ImageReader.loadImage("src/arcade/database/images", "default.jpg");
        //ImageIcon anImage = new ImageIcon(ImageReader.loadImage("src/arcade/database/images", "default.jpg"));
        this.setPreferredSize(new Dimension(theContainer.getWidth(), 110));
        initComponents();
    }

    private void getUserInfo () {
        userName = myUser;
        userInfo = "Robert Bruce";
    }

    private void initComponents () {
        viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("View profile of " + myUser);
                    //getArcade().replacePanel("SendMessage");
            }
              
          });
        sendMessageButton = new JButton("Send Message");
        sendMessageButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("Send message to " + myUser);
                    //getArcade().replacePanel("SendMessage");
            }
              
          });
        this.add(viewProfileButton, "cell 1 1, align center, split 2");
        this.add(sendMessageButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillRect(50, 5, this.getWidth()-50, this.getHeight()-5);
        g.setColor(Color.black);
        g.fillRect(55, 10, this.getWidth()-60, this.getHeight()-15);
        g.drawImage(profilePic, 60, 12, 81, 81, Color.black, this);
        g.setColor(Color.white);
        g.setFont(new Font("sansserif", Font.BOLD, 40));
        g.drawString(myUser, 150, 60);
        g.setFont(new Font("sansserif", Font.ITALIC, 18));
        g.drawString(userInfo, 170, 85);
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
