package vooga.platformer.leveleditor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import vooga.platformer.gameobject.GameObject;

public class GameObjectEditor extends JPopupMenu {
    public GameObjectEditor (GameObject obj) {
        //Top panel gets image (eventually animation?)
        JPanel myTop = new JPanel();
        JLabel myImage = new JLabel();
        myImage.setIcon(new ImageIcon(obj.getCurrentImage()));
        myTop.add(myImage);
        add(myTop);
        
        //Bottom panel has fields and buttons
        JPanel myBottom = new JPanel();
        List<String> attributes = getAttributes();
        myBottom.setLayout(new GridLayout(attributes.size() + 1,2));

        JLabel label;
        JTextField textField;
        ActionListener getText = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                // TODO Auto-generated method stub
                
            }
        };
        for(String att : attributes) {
            label = new JLabel(att);
            textField = new JTextField();
            myBottom.add(label);
            myBottom.add(textField);
        }
        JButton accept = new JButton();
        accept.addActionListener(getText);
        JButton addStrategy = new JButton();
        addStrategy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
        add(myBottom);
    }
    
    private List<String> getAttributes() {
        List <String> availableAttributes = new ArrayList<String>();
        availableAttributes.add("X");
        availableAttributes.add("Y");
        availableAttributes.add("Width");
        availableAttributes.add("Height");
        availableAttributes.add("Image");
        return availableAttributes;
    }

}
