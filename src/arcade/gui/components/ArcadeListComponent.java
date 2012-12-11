package arcade.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;
import arcade.gui.panel.ArcadePanel;
import arcade.utility.ImageReader;


/**
 * Generalized Arcade List Component.
 * Good for showing lists of data / menu items.
 * 
 * @author Robert Bruce
 */
@SuppressWarnings("serial")
public class ArcadeListComponent extends JComponent implements
        Comparable<ArcadeListComponent> {

    protected ArcadePanel myContainer;
    private String myLargeText;
    private String mySubText;
    private Image myImage;
    private int myHeight = 100;
    private Color myBackgroundColor1 = Color.red;
    private Color myBackgroundColor2 = Color.black;
    private Color myTextColor1 = Color.white;
    private Color myTextColor2 = Color.white;
    private Font myFont1 = new Font("sansserif", Font.BOLD, 32);
    private Font myFont2 = new Font("sansserif", Font.ITALIC, 18);

    public ArcadeListComponent (Image img, String largeText, String subText,
            ArcadePanel theContainer) {
        myImage = img;
        myLargeText = largeText;
        mySubText = subText;
        myContainer = theContainer;
        setPreferredSize(new Dimension(theContainer.getWidth(), 110));
        initComponents();
    }

    public ArcadeListComponent (String imgLocation, String largeText,
            String subText, ArcadePanel theContainer) {
        myImage = getPicture(imgLocation);
        myLargeText = largeText;
        mySubText = subText;
        myContainer = theContainer;
        setPreferredSize(new Dimension(theContainer.getWidth(), 110));
        initComponents();
    }

    protected void initComponents () {
        // Default no components.
    }

    public void setSubText (String newSubText) {
        mySubText = newSubText;
    }

    private Image getPicture (String fullLocation) {
        String fileName = fullLocation.substring(fullLocation.lastIndexOf("/"));
        String directoryLocation = fullLocation.substring(0,
                fullLocation.lastIndexOf("/"));
        return ImageReader.loadImage(directoryLocation, fileName);
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        g.setColor(myBackgroundColor1);
        g.fillRect(25, 5, getWidth() - 75, getHeight() - 5);
        g.setColor(myBackgroundColor2);
        g.fillRect(30, 10, getWidth() - 85, getHeight() - 15);
        g.drawImage(myImage, 35, 12, 81, 81, Color.black, this);
        g.setColor(myTextColor1);
        g.setFont(myFont1);
        g.drawString(myLargeText, 125, 60);
        g.setColor(myTextColor2);
        g.setFont(myFont2);
        g.drawString(mySubText, 145, 85);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension(myContainer.getWidth(), myHeight);
    }

    @Override
    public int getHeight () {
        return myHeight;
    }

    @Override
    public int compareTo (ArcadeListComponent o) {
        return 0;
    }

    /**
     * Minimum Height of 100.
     * 
     * @param myHeight Set the height of the component.
     */
    public void setHeight (int height) {
        myHeight = height;
        if (myHeight < 100) {
            myHeight = 100;
        }
    }

    /**
     * @return the myTextColor1
     */
    public Color getMyTextColor1 () {
        return myTextColor1;
    }

    /**
     * @param myTextColor1 the myTextColor1 to set
     */
    public void setMyTextColor1 (Color myTextColor1) {
        this.myTextColor1 = myTextColor1;
    }

    /**
     * @return the myBackgroundColor2
     */
    public Color getMyBackgroundColor2 () {
        return myBackgroundColor2;
    }

    /**
     * @param myBackgroundColor2 the myBackgroundColor2 to set
     */
    public void setMyBackgroundColor2 (Color myBackgroundColor2) {
        this.myBackgroundColor2 = myBackgroundColor2;
    }

    /**
     * @return the myBackgroundColor1
     */
    public Color getMyBackgroundColor1 () {
        return myBackgroundColor1;
    }

    /**
     * @param myBackgroundColor1 the myBackgroundColor1 to set
     */
    public void setMyBackgroundColor1 (Color myBackgroundColor1) {
        this.myBackgroundColor1 = myBackgroundColor1;
    }

    /**
     * @return the myTextColor2
     */
    public Color getMyTextColor2 () {
        return myTextColor2;
    }

    /**
     * @param myTextColor2 the myTextColor2 to set
     */
    public void setMyTextColor2 (Color myTextColor2) {
        this.myTextColor2 = myTextColor2;
    }

    /**
     * @return the myFont1
     */
    public Font getMyFont1 () {
        return myFont1;
    }

    /**
     * @param newFont1 Set the font for the Large Text.
     */
    public void setMyFont1 (Font newFont1) {
        myFont1 = newFont1;
    }

    /**
     * @return The font for the SubText.
     */
    public Font getMyFont2 () {
        return myFont2;
    }

    /**
     * @param newFont2 Set the font for the SubText.
     */
    public void setMyFont2 (Font newFont2) {
        myFont2 = newFont2;
    }

}
