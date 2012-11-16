package arcade.gui.panel;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JPanel;
import arcade.gui.Arcade;


/**
 * The arcades implementation of JPanel
 * 
 * @author Michael Deng
 * 
 */
public class ArcadePanel extends JPanel {

    private String myPanelType;
    private Arcade myArcade;

    public ArcadePanel (Arcade a, String panelType) {
        myArcade = a;
        myPanelType = panelType;
    }

    public String getPanelType () {
        return myPanelType;
    }

    public Arcade getArcade(){
        return myArcade;
    }

    public JComponent getComponent(String name){
        Component[] allComponents = this.getComponents();
        int i = 0;
        while (i < allComponents.length) {
            if (allComponents[i].getName() != null){
                if (allComponents[i].getName().equals(name)){
                    return (JComponent) allComponents[i];
                }
            }
            i++;
        }
        return null;
    }

}
