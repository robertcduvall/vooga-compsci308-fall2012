package arcade.gui.panel;

import javax.swing.JPanel;


/**
 * The is the top-level abstract class for the panel hierarchy.
 * 
 * @author Michael Deng
 * 
 */
public abstract class AbstractPanel extends JPanel {

    public AbstractPanel (JPanel thePanel) {

        makeListeners();
        addComponents();
    }

    abstract protected void addComponents ();

    abstract protected void makeListeners ();
    
    abstract public void refresh();

}
