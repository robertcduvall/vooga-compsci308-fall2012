package vooga.turnbased.gui.interactionpanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import vooga.turnbased.gamecore.GameLoopMember;


public class InteractionPanel implements GameLoopMember {
    
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    private Image myPanelImage;
    // to be passed down for drawing
    private Graphics myImageGraphics;

    public InteractionPanel () {
        try {
            myPanelImage = ImageIO.read(new File("src/vooga/turnbased/resources/image/GUI/dialogue1.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        myImageGraphics = (myPanelImage.getGraphics());
    }

    public void update () {

    }

    public void paint (Graphics g) {
        myImageGraphics.setColor(Color.MAGENTA);
        myImageGraphics.drawString("try to draw something", 10, 10);
        g.drawImage(myPanelImage, 0, 0, null);
    }
}
