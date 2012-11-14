package arcade.gui.panel.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.Serializable;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import arcade.gui.Arcade;

/**
 * This is the normal user panel post-login.
 * This will show:
 * "Welcome, Michael" and then have 2 buttons: Logout and Exit
 * 
 * @author Michael Deng
 *
 */
public class UserPanel extends AUserPanel {

    public UserPanel (Arcade a) {
        super(a);
        
        Map<K, V> m = a.getDataMap("getAvatar");
        m.put("username", a.getUsername());
        Map <String, Serializable> response = a.getUserManager().request(m);
        ImageIcon avatarIcon = (ImageIcon) response.get("avatar");
        Image avatarImage = avatarIcon.getImage();
        
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void makeListeners () {
        // TODO Auto-generated method stub

    }

    @Override
    protected void addComponents () {
        this.setBackground(Color.RED);
        // TODO Auto-generated method stub

    }

    @Override
    public void refresh () {
        // TODO Auto-generated method stub

    }
    
    public static void main(String [] args){
        Arcade testArcade = new Arcade();
        JFrame testFrame = new JFrame();
        UserPanel testMe = new UserPanel(testArcade);
        testFrame.getContentPane().add(testMe);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.pack();
        testFrame.setSize(new Dimension(500, 500));
        testFrame.setVisible(true);
        
    }

}
