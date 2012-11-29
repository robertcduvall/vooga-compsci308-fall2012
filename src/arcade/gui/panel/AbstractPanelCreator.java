package arcade.gui.panel;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.utility.ImageReader;


/**
 * The is the top-level abstract class for the panel hierarchy.
 * 
 * @author Michael Deng
 * @author Robert Bruce
 * 
 */
public abstract class AbstractPanelCreator {

    private String myPanelType;
    private Arcade myArcade;

    public AbstractPanelCreator (Arcade a) {
        System.out.println(this.getClass().toString());
        myArcade = a;
    }

    //    /**
    //     * Initializes the PanelCreator
    //     * 
    //     * @param a Arcade
    //     */
    //    public void creatorSetup (Arcade a) {
    //        myArcade = a;
    //    }

    /**
     * Used by subclasses as the first step in creating a new ArcadePanel
     * 
     * @return a new ArcadePanel
     */
    protected ArcadePanel initializeNewPanel () {
        ArcadePanel newPanel = new ArcadePanel(myArcade, myPanelType);
        newPanel.setBackground(Color.BLACK);
        return newPanel;
    }

    /**
     * Call this method to have this panel creator create you a new panel
     * 
     * @return ArcadePanel (special form of JPanel)
     */
    abstract public ArcadePanel createPanel ();

    /**
     * Allows subclasses to set the panel type
     * 
     * @param panelType the panel type as a string
     */
    protected void setPanelType (String panelType) {
        myPanelType = panelType;
    }

    /**
     * Adds an image loaded from src/arcade/gui/images folder to a panel with given
     * layout and constraints.
     * @param fileName src/arcade/gui/images + fileName.
     * @param myPanel Panel to add image to.
     * @param layout MigLayout of image & panel.
     * @param alignment Of image in MigLayout
     * @return The ArcadePanel with the image inserted.
     */
    protected ArcadePanel addGuiImage (String fileName, ArcadePanel myPanel,
            MigLayout layout, Object constraints) {
        return addImage("/arcade/gui/images" + fileName, myPanel, layout, constraints);
    }

    /**
     * Adds an image loaded from src to a panel with given layout and constraints.
     * @param fileName src + fileName.
     * @param myPanel Panel to add image to.
     * @param layout MigLayout of image & panel.
     * @param alignment Of image in MigLayout
     * @return The ArcadePanel with the image inserted.
     */
    protected ArcadePanel addImage (String fileName, ArcadePanel myPanel,
            MigLayout layout, Object constraints) {

        ImageIcon icon = new ImageIcon(ImageReader.loadImage("src", fileName));
        JLabel picLabel = new JLabel(icon);
        myPanel.setLayout(layout);
        myPanel.add(picLabel, constraints);
        return myPanel;
    }

    /**
     * Adds an image to a panel with given layout and constraints.
     * @param image Image to be added.
     * @param myPanel Panel to add image to.
     * @param layout MigLayout of image & panel.
     * @param alignment Of image in MigLayout
     * @return The ArcadePanel with the image inserted.
     */
    protected ArcadePanel addImage (Image image, ArcadePanel myPanel,
            MigLayout layout, Object constraints) {
        JLabel picLabel = new JLabel(new ImageIcon(image));
        myPanel.setLayout(layout);
        myPanel.add(picLabel, constraints);
        return myPanel;
    }

    /**
     * Allows subclasses to get reference to the Arcade
     * 
     * @return reference to the Arcade
     */
    protected Arcade getArcade () {
        return myArcade;
    }

}
