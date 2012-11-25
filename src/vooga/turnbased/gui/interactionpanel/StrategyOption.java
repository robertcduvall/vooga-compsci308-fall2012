package vooga.turnbased.gui.interactionpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;


public class StrategyOption {

    private static final int FONT_SIZE = 20;
    private String myOptionMessage;
    private Point myPosition;
    private FontMetrics myFontMetrics;
    private Rectangle mySize;
    private Color myColor;
    private static Image myBulletPointImage = Toolkit.getDefaultToolkit()
            .createImage("src/vooga/turnbased/resources/image/GUI/bullet-point-1.png");
    private static Dimension ourBulletSize = new Dimension(50, 50);

    public StrategyOption (String message, Point position) {
        myOptionMessage = message;
        myPosition = position;
        myColor = Color.RED;
        // FontMetrics metr = this.getFontMetrics(gameFont);
    }

    public void paintOption (Graphics g) {
        Font optionFont = new Font("Helvetica", Font.BOLD, FONT_SIZE);
        g.setFont(optionFont);
        g.setColor(myColor);
        myFontMetrics = g.getFontMetrics();
        if (mySize == null) {
            mySize =
                    new Rectangle(myPosition.x, myPosition.y - myFontMetrics.getHeight(),
                                  myFontMetrics.stringWidth(myOptionMessage),
                                  myFontMetrics.getHeight());
        }
        drawBulletPoint(g);
        g.drawString(myOptionMessage, myPosition.x, myPosition.y);
    }
    
    private void drawBulletPoint(Graphics g) {
        int x = myPosition.x - ourBulletSize.width;
        int y = myPosition.y - ourBulletSize.height / 2;
        g.drawImage(myBulletPointImage, x, y, ourBulletSize.width, ourBulletSize.height, null);
    }

    public void handleMouseEvent (MouseEvent e) {
        if (mySize.contains(e.getPoint())) {
            myColor = Color.CYAN;
        }
    }
}
