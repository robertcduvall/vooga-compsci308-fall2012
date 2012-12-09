package util.ingamemenu;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JComponent;


/**
 * This class is an in-game menu that can be brought up when game is running.
 * However pausing the game when menu is out is not and cannot be implemented by
 * this class. The size of the menu is proportionally to the initial size of the
 * initial game canvas.
 * 
 * @author Yaqi Zhang
 */
public class Menu extends JComponent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final double MENU_SIZE_RATIO = 0.66;
    private static final int INTERVAL = 20;
    public static final Color TRANSP_COLOR = new Color(160, 100, 100, 100);
    private double myRatio = MENU_SIZE_RATIO;
    private JComponent myGameCanvas;
    private ArrayList<GameButton> myButtonList = new ArrayList<GameButton>();
    private Color myColor = Color.WHITE;

    /**
     * @param gameCanvas JComponent where the game painted
     */
    public Menu (JComponent gameCanvas) {
        myGameCanvas = gameCanvas;
        setSize(myGameCanvas.getSize());
        gameCanvas.add(this, BorderLayout.CENTER);
        gameCanvas.repaint();
    }

    @Override
    protected void paintComponent (Graphics pen) {
        pen.setColor(myColor);
        double myWidth = getSize().width * myRatio;
        double myHeight = getSize().height * myRatio;
        pen.fillRect((int) ((getSize().width / 2) - (myWidth / 2)),
                (int) ((getSize().height / 2) - (myHeight / 2)),
                (int) (myWidth - 2), (int) (myHeight - 2));
        pen.setColor(Color.BLACK);
        pen.drawRect((int) ((getSize().width / 2) - (myWidth / 2)),
                (int) ((getSize().height / 2) - (myHeight / 2)),
                (int) (myWidth - 2), (int) (myHeight - 2));
    }

    @Override
    public Dimension getSize () {
        return myGameCanvas.getSize();
    }

    /**
     * @param fileName of the button image
     * @param command name of this button
     */
    public void addButton (String fileName, String command, MouseListener ml) {
        GameButton gb = new GameButton(fileName, command, ml);
        addButton(gb);
    }

    /**
     * @param button to add
     */
    public void addButton (GameButton button) {
        myButtonList.add(button);
        add(button);
    }

    public void pack () {
        Insets insets = myGameCanvas.getInsets();
        Dimension canvasSize = myGameCanvas.getSize();
        int numOfButtons = myButtonList.size();
        int sumOfWidth = INTERVAL * (numOfButtons - 1);
        for (GameButton gb : myButtonList) {
            sumOfWidth += gb.getSize().width;
        }
        int start = canvasSize.width / 2 - sumOfWidth / 2;
        int location = 0;
        for (GameButton gb : myButtonList) {
            Dimension size = gb.getSize();
            gb.setBounds(insets.left + start + location, insets.top
                    + canvasSize.height / 2 - size.height / 2, size.width,
                    size.height);
            location += size.width + INTERVAL;
        }
    }

    /**
     * @param ratio of the size of the menu to that of game canvas.
     */
    public void setRatio (double ratio) {
        myRatio = ratio;
    }

    /**
     * @param keyValue the key associate to the button. eg. KeyEvent.VK_N gives
     *        the int number of key N.
     * @param button will be counted as pressed when player pressing this key
     */
    public void addHotKey (int keyValue, Button button) {
        // TODO
    }

    /**
     * @param color
     */
    public void setColor (Color color) {
        myColor = color;
        repaint();
    }

    /**
     * 
     */
    public void setTransparent () {
        myColor = TRANSP_COLOR;
        repaint();
    }

}
