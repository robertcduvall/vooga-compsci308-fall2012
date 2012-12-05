package vooga.platformer.leveleditor;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import vooga.platformer.gameobject.GameObject;

public class GameObjectEditor extends JPopupMenu {
    public GameObjectEditor (GameObject obj) {
        JPanel myTop = new JPanel();
        JLabel myImage = new JLabel();
        myImage.setIcon(new ImageIcon(obj.getCurrentImage()));
        myTop.add(myImage);
        add(myTop);
        
        JPanel myBottom = new JPanel();
        List<String> attributes = getAttributes();
        myBottom.setLayout(new GridLayout(attributes.size(),2));
        JTextField text;
        JLabel label;
        for(String att : attributes) {
            label = new JLabel(att);
            text = new JTextField();
            myBottom.add(label);
            myBottom.add(text);
        }
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
