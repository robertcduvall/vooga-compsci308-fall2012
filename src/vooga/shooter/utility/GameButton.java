package src.vooga.shooter.utility;
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
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_BUTTON_SIZE = 200;
    private Image myImg;
    private String myImgName;
    private Dimension myImgSize = new Dimension(DEFAULT_BUTTON_SIZE,
            DEFAULT_BUTTON_SIZE);
    private MouseListener myMouseListener;

    /**
     * Create a game button that allows customizing the image of the button.
     */
    public GameButton () {
        setPreferredSize(myImgSize);
        setMouseListener(null);
    }

    @Override
    protected void paintComponent (Graphics pen) {
        pen.drawImage(myImg, 0, 0, myImgSize.width, myImgSize.height, null);
    }

    /**
     * @param img 
     */
    public void setImage (Image img) {
        myImg = img;
    }

    /**
     * @param fileName 
     */
    public void setImage (String fileName) {
        myImgName = fileName;
        setImage(fileName, "normal");
    }

    private void changeImage (String state) {
        setImage(myImgName, state);
    }

    /**
     * User define what will happen after this button being clicked in
     * actionPerformed() of a GameListener.
     * 
     * @param gl GameListener
     */
    public void setMouseListener (final GameListener gl) {
        myMouseListener = new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent arg0) {
                changeImage("pressed");
                try {
                    gl.actionPerformed();
                }
                catch (NullPointerException e) {
                    
                }
            }

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
        myImgSize.width = width;
        myImgSize.height = height;
        setPreferredSize(new Dimension(width, height));
    }

    private void setImage (String fileName, String state) {
        try {
            myImg = ImageIO.read(new File("src/buttonimg/" + fileName + "."
                    + state + ".png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }
}
