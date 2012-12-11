package arcade.gui.panel.main;
import java.awt.Dimension;
import java.util.Collections;
import java.util.List;
import arcade.gui.Arcade;
import arcade.gui.components.ArcadeListComponent;
import arcade.gui.panel.ArcadePanel;
import arcade.gui.panel.main.AMainPanel;


/**
 * An abstract superclass, subclasses simply
 * need to implement loadListComponents, but may
 * want to override addComponentsToTop or
 * addComponentsToBottom.
 * 
 * @author Robert Bruce
 */
abstract public class ArcadeListMainPanel extends AMainPanel {

    List<ArcadeListComponent> myListComponents;
    ArcadePanel myPanel;

    /**
     * Basic / default Constructor.
     * 
     * @param a Arcade container for the panel.
     */
    public ArcadeListMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        myPanel = initializeNewPanel();
        myPanel.setPreferredSize(new Dimension(750, 950));
        myPanel = addComponentsToTop(myPanel);
        myListComponents = loadListComponents();
        Collections.sort(myListComponents);
        for (ArcadeListComponent listComponent : myListComponents) {
            myPanel.add(listComponent);
        }
        myPanel = addComponentsToBottom(myPanel);
        setPreferredSize();
        return myPanel;
    }

    /**
     * Default adds nothing. Override to add components above the list.
     * 
     * @param panel ArcadePanel to be returned.
     * @return Unaltered panel.
     */
    protected ArcadePanel addComponentsToTop (ArcadePanel panel) {
        return panel;
    }

    /**
     * Default adds nothing. Override to add components below the list.
     * 
     * @param panel ArcadePanel to be returned.
     * @return Unaltered panel.
     */
    protected ArcadePanel addComponentsToBottom (ArcadePanel panel) {
        return panel;
    }

    protected void setPreferredSize () {
        int totalSize = 100;
        int width = 0;
        for (ArcadeListComponent listComponent : myListComponents) {
            totalSize += listComponent.getHeight();
            if (listComponent.getWidth() > width) {
                width = listComponent.getWidth();
            }
        }
        myPanel.setPreferredSize(new Dimension(750, totalSize));
    }

    protected abstract List<ArcadeListComponent> loadListComponents ();

}
