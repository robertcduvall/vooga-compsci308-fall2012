package util.ingamemenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;


/**
 * @author Yaqi Zhang
 * 
 */
public class GameButton extends JComponent {
    /**
     * String locates at the bottom of the button
     */
    public static final int BOTTOM = 1;

    /**
     * String locates at the center of the button
     */
    public static final int CENTER = 0;
    /**
     * String locates at the top of the button
     */
    public static final int TOP = 2;
    private static final long serialVersionUID = 1L;
    public static final int DEFAULT_BUTTON_WIDTH = 130;
    public static final int DEFAULT_BUTTON_HEIGHT = 40;

    private Image myImg;
    private String myImgName;
    private String myCommand = "";
    private MouseListener myMouseListener;
    private Dimension mySize;
    private int myStringLocation = CENTER;
    private Color myStringColor;

    /**
     * Create a game button that allows to customize the image of the button.
     * 
     * @param fileName of the button image
     */
    public GameButton (String fileName) {
        setName(fileName);
        setImage(fileName);
        mySize = new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT);
        setPreferredSize(mySize);
        setMouseListener();
    }

    /**
     * Create a game button that allows to customize the image of the button.
     * 
     * @param fileName of the button image
     * @param command String appears on the button.
     */
    public GameButton (String fileName, String command) {
        this(fileName);
        paintString(command);
    }

    /**
     * Create a game button that allows to customize the image of the button.
     * 
     * @param fileName of the button image
     * @param command String appears on the button.
     * @param ml MouseListener
     */
    public GameButton (String fileName, String command, MouseListener ml) {
        this(fileName, command);
        addMouseListener(ml);
    }

    /**
     * Create a game button that allows to customize the image of the button.
     * 
     * @param fileName of the button image
     * @param command String appears on the button.
     * @param ml MouseListener
     * @param size of the Button
     */
    public GameButton (String fileName, String command, MouseListener ml,
            Dimension size) {
        this(fileName, command, ml);
        setSize(size);
    }

    /**
     * Paint String on the button.
     * 
     * @param command String appears on the button
     */
    public void paintString (String command) {
        paintString(command, Color.BLACK);
    }

    /**
     * Paint String on the button.
     * 
     * @param command String appears on the button
     */
    public void paintString (String command, Color color) {
        myCommand = command;
        myStringColor = color;
    }

    /**
     * Paint String on the button.
     * 
     * @param command String appears on the button
     * @param location of the String appear on the button
     */
    public void paintString (String command, int location, Color color) {
        myStringLocation = location;
        paintString (command, color);
    }

    private void paintString (Graphics pen) {
        pen.setColor(myStringColor);
        if (myStringLocation == CENTER) {
            pen.drawString(myCommand, (getSize().width / 4), (int) (getSize()
                    .getHeight() / 2));
        }
        else if (myStringLocation == BOTTOM) {
            pen.drawString(myCommand, (getSize().width / 4), (int) (getSize()
                    .getHeight() - 10));
        }
        else if (myStringLocation == TOP) {
            pen.drawString(myCommand, (getSize().width / 4), 10);
        }
    }

    @Override
    protected void paintComponent (Graphics pen) {
        Dimension myButtonSize = getSize();
        pen.drawImage(myImg, 0, 0, myButtonSize.width, myButtonSize.height,
                null);
        paintString(pen);
    }

    @Override
    public void paint (Graphics pen) {
        paintComponent(pen);
    }

    /**
     * @param img image
     */
    public void setImage (Image img) {
        myImg = img;
    }

    /**
     * @param fileName in String
     */
    public void setImage (String fileName) {
        myImgName = fileName;
        setImage(fileName, "normal");
    }

    private void changeImage (String state) {
        setImage(myImgName, state);
    }

    private void setMouseListener () {
        removeMouseListener(myMouseListener);
        myMouseListener = new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent arg0) {
                changeImage("pressed");
            }

            @Override
            public void mouseReleased (MouseEvent arg0) {
                changeImage("normal");
            }
        };
        addMouseListener(myMouseListener);
    }

    /**
     * @param width
     * @param height
     */
    public void setButtonSize (int width, int height) {
        mySize = new Dimension(width, height);
        setPreferredSize(mySize);
    }

    private void setImage (String fileName, String state) {
        try {
            myImg = ImageIO.read(new File("src/util/ingamemenu/buttonimg/"
                    + fileName + "." + state + ".png"));
        }
        catch (IOException e) {
            System.out.println("src/vooga/platformer/gui/menu/buttonimg/"
                    + fileName + "." + state + ".png was not found");
            e.printStackTrace();
        }
        repaint();
    }

    /**
     * @param size of the menu
     */
    @Override
    public void setSize (Dimension size) {
        mySize = size;
        setPreferredSize(size);
    }

    @Override
    public Dimension getSize () {
        return mySize;
    }
}
