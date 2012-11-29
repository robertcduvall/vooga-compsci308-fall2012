package vooga.turnbased.gui.interactionpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import vooga.turnbased.gamecore.GameLoopMember;

/**
 * A GUI panel for dialogue box etc. to be displayed in game
 * 
 * @author rex
 *
 */
public class InteractionPanel {

    private static final int ROW_NUMBER = 3;
    private static final int COLUMN_NUMBER = 2;
    private static final double MARGIN_PROPORTION = 0.1;
    private static Image myBulletPointImage = Toolkit.getDefaultToolkit()
            .createImage("src/vooga/turnbased/resources/image/GUI/bullet-point-1.png");
    private static Dimension ourBulletSize = new Dimension(40, 40);

    private int myBulletPointIndex = 0;

    private Image myPanelImage;

    // to be passed down for drawing
    private Graphics myImageGraphics;
    private List<StrategyOption> myOptions;
    private List<Point> myOptionPositions;

    public InteractionPanel () {
        initializePanelImage();
        myOptionPositions = initializeOptionPositions();
        myOptions = new ArrayList<StrategyOption>();
        createHardcodedOptions();
    }

    private void createHardcodedOptions () {
        myOptions.add(new StrategyOption("option1", myOptionPositions.get(0)));
        myOptions.add(new StrategyOption("Another boring option", myOptionPositions.get(1)));
        myOptions.add(new StrategyOption("Even more option!!!", myOptionPositions.get(2)));
    }

    public Image renderImage () {
        initializePanelImage();
        for (StrategyOption option : myOptions) {
            option.paintOption(myImageGraphics);
        }
        drawBulletPoint(myImageGraphics);
        return myPanelImage;
    }
    
    private void initializePanelImage() {
        try {
            myPanelImage =
                    ImageIO.read(new File("src/vooga/turnbased/resources/image/GUI/dialogue1.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        myImageGraphics = (myPanelImage.getGraphics());
    }

    public void highlightOption (MouseEvent e) {
        for (int i=0; i< myOptions.size(); i++) {
            myOptions.get(i).highlight(e);
            if (myOptions.get(i).optionIsHighlighted()) {
                myBulletPointIndex = i;
            }
        }
    }

    public void dehighlightOption (MouseEvent e) {
        for (StrategyOption option : myOptions) {
            option.dehighlight(e);
        }
    }
    
    private void drawBulletPoint(Graphics g) {
        int x = myOptionPositions.get(myBulletPointIndex).x - ourBulletSize.width;
        int y = myOptionPositions.get(myBulletPointIndex).y - ourBulletSize.height / 2;
        g.drawImage(myBulletPointImage, x, y, ourBulletSize.width, ourBulletSize.height, null);
    }

    private List<Point> initializeOptionPositions () {
        List<Point> positions = new ArrayList<Point>();
        int width = myPanelImage.getWidth(null);
        int height = myPanelImage.getHeight(null);
        for (int i = 1; i <= ROW_NUMBER; i++) {
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                int xCoordinate = (int) Math.round(MARGIN_PROPORTION * width) + width * j / COLUMN_NUMBER;
                positions.add(new Point(xCoordinate, height * i / 4));
            }
        }
        return positions;
    }
    
    public Dimension getPanelSize() {
        return new Dimension(myPanelImage.getWidth(null), myPanelImage.getHeight(null));
    }
}
