package vooga.turnbased.gui.interactionpanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;


/**
 * A GUI panel for dialogue box etc. to be displayed in game
 * 
 * @author rex
 * 
 */
public class InteractionPanel {

    private static final String QUIT_MESSAGE = "Quit";
    private static final int ROW_NUMBER = 3;
    private static final int COLUMN_NUMBER = 2;
    private static final double MARGIN_PROPORTION = 0.1;
    private static Image myBulletPointImage = Toolkit.getDefaultToolkit()
            .createImage("src/vooga/turnbased/resources/image/GUI/bullet-point-1.png");
    private static Dimension ourBulletSize = new Dimension(40, 40);

    private int myBulletPointIndex;
    private Point myPreviousPosition;

    private Image myPanelImage;

    // to be passed down for drawing
    private Graphics myImageGraphics;
    private List<StrategyOption> myOptions;
    private List<Point> myOptionPositions;

    public InteractionPanel (List<String> options) {
        this();
        myOptions = addOptions(options);
    }
    
    public InteractionPanel (Set<String> options) {
        this();
        myOptions = addOptions(options);
    }
    
    protected InteractionPanel() {
        initializePanelImage();
        myOptionPositions = initializeOptionPositions();
        myBulletPointIndex = 0;
        myPreviousPosition = null;
    }
    
    private List<StrategyOption> addOptions (List<String> options) {
        List<StrategyOption> strategyOptions = new ArrayList<StrategyOption>();
        options.add(QUIT_MESSAGE);
        for (int i = 0; i < options.size(); i++) {
            strategyOptions.add(new StrategyOption(options.get(i), myOptionPositions.get(i)));
        }
        return strategyOptions;
    }
    
    private List<StrategyOption> addOptions (Set<String> options) {
        List<String> strategyOptions = new ArrayList<String>();
        for (String option: options) {
            strategyOptions.add(option);
        }
        return addOptions(strategyOptions);
    }

    public Image renderImage () {
        initializePanelImage();
        for (StrategyOption option : myOptions) {
            option.paintOption(myImageGraphics);
        }
        drawBulletPoint(myImageGraphics);
        return myPanelImage;
    }

    private void initializePanelImage () {
        try {
            myPanelImage =
                    ImageIO.read(new File("src/vooga/turnbased/resources/image/GUI/dialogue1.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        myImageGraphics = (myPanelImage.getGraphics());
    }

    public void highlightOption (Point mousePosition) {
        boolean highlighted = false;
        for (int i = 0; i < myOptions.size(); i++) {
            highlighted = myOptions.get(i).highlight(mousePosition);
            if (myOptions.get(i).optionIsHighlighted()) {
                myBulletPointIndex = i;
            }
        }
        if (!highlighted) {
            myPreviousPosition = mousePosition;
        }
    }

    public void dehighlightOption () {
        for (StrategyOption option : myOptions) {
            option.dehighlight();
        }
        myPreviousPosition = null;
    }

    private void drawBulletPoint (Graphics g) {
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
                int xCoordinate =
                        (int) Math.round(MARGIN_PROPORTION * width) + width * j / COLUMN_NUMBER;
                positions.add(new Point(xCoordinate, height * i / 4));
            }
        }
        return positions;
    }

    public Dimension getPanelSize () {
        return new Dimension(myPanelImage.getWidth(null), myPanelImage.getHeight(null));
    }
    
    /**
     * get previous position of the mouse on this panel
     * @return previous position of the mouse
     */
    public Point getPreviousPosition() {
        return myPreviousPosition;
    }
    
    /**
     * set the previous position to the new position
     * It is called when the previous position has been processed
     * @param newPosition new position of the mouse
     */
    public void setPreviousPosition(Point newPosition) {
        myPreviousPosition = newPosition;
    }
}