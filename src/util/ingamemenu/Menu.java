package util.ingamemenu;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
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
    private static final Color TRANSP_COLOR = new Color(160, 100, 100, 100);
    private double myRatio = MENU_SIZE_RATIO;
    private Map<String, GameButton> myButtonMap = new HashMap<String, GameButton>();
    private JComponent myGameCanvas;
    private Color myColor;

    /**
     * @param gameCanvas JComponent where the game painted
     */
    public Menu (JComponent gameCanvas) {
        myGameCanvas = gameCanvas;
        setSize(myGameCanvas.getSize());
        gameCanvas.add(this, BorderLayout.CENTER);
        setLayout(new GridBagLayout());
        gameCanvas.repaint();
    }

    @Override
    protected void paintComponent (Graphics pen) {
        pen.setColor(Color.WHITE);
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
    public void addButtons (String fileName, String command, MouseListener ml) {
        GameButton gb = new GameButton(fileName, command, ml);
        myButtonMap.put(command, gb);
        add(gb, new GridBagConstraints());
    }

    /**
     * @param button to add
     */
    public void addButtons (GameButton button) {
        add(button, new GridBagConstraints());
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
