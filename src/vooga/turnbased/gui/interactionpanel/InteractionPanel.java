package vooga.turnbased.gui.interactionpanel;

import java.awt.Color;
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


public class InteractionPanel implements GameLoopMember {
    
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private static final int ROW_NUMBER = 3;
    private static final int COLUMN_NUMBER = 2;
    private static final double MARGIN_PROPORTION = 0.11;

    private Image myPanelImage;

    // to be passed down for drawing
    private Graphics myImageGraphics;
    private List<StrategyOption> myOptions;
    private List<Point> myOptionPositions;

    public InteractionPanel () {
        try {
            myPanelImage = ImageIO.read(new File("src/vooga/turnbased/resources/image/GUI/dialogue1.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        myImageGraphics = (myPanelImage.getGraphics());
        myOptionPositions = initializeOptionPositions();
        myOptions = new ArrayList<StrategyOption>();
        createHardcodedOptions();
    }

    private void createHardcodedOptions() {
        myOptions.add(new StrategyOption("option1", myOptionPositions.get(0)));
        myOptions.add(new StrategyOption("Another boring option", myOptionPositions.get(1)));
        myOptions.add(new StrategyOption("Even more option!!!", myOptionPositions.get(2)));
    }
    
    public void update () {

    }

    public void paint (Graphics g) {
        for (StrategyOption option: myOptions) {
            option.paintOption(myImageGraphics);
        }
        g.drawImage(myPanelImage, 0, 0, null);
    }
    
    public void highlightOption(MouseEvent e) {
        for (StrategyOption option: myOptions) {
            option.highlight(e);
        }
    }
    
    public void dehighlightOption(MouseEvent e) {
        for (StrategyOption option: myOptions) {
            option.dehighlight(e);
        }
    }
    
    private List<Point> initializeOptionPositions() {
        List<Point> positions = new ArrayList<Point>();
        int width = myPanelImage.getWidth(null);
        int height = myPanelImage.getHeight(null);
        for (int i=1; i<=ROW_NUMBER; i++) {
            positions.add(new Point((int)Math.round(MARGIN_PROPORTION * width), height * i / 4));
            positions.add(new Point(width / 2, height * i / 4));
        }
        return positions;
    }
}
