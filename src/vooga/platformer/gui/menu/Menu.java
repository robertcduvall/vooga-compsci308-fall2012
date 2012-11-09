package vooga.platformer.gui.menu;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;


/**
 * @author Yaqi Zhang
 * @author Xi Du
 */
public class Menu extends JComponent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final double MENU_SIZE_RATIO = 0.66;
    private double myRatio = MENU_SIZE_RATIO;
    private Map<String, GameButton> myButtonMap = new HashMap<String, GameButton>();

    /**
     * @param gameCanvas JComponent where the game painted
     */
    public Menu (JComponent gameCanvas) {
        Dimension gameSize = gameCanvas.getSize();
        Dimension size = new Dimension((int) (gameSize.width * myRatio),
                (int) (gameSize.height * myRatio));
        setPreferredSize(size);
        gameCanvas.setLayout(new GridBagLayout());
        gameCanvas.add(this, new GridBagConstraints());
        this.setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent (Graphics pen) {
        pen.setColor(Color.WHITE);
        pen.fillRect(1, 1, getSize().width - 2, getSize().height - 2);
        pen.setColor(Color.BLACK);
        pen.drawRect(1, 1, getSize().width - 2, getSize().height - 2);
    }

    /**
     * @param fileName of the button image
     * @param command name of this button
     * @param gl GameListener to listen to this button.
     */
    public void addButtons (String fileName, String command, GameListener gl) {
        GameButton gb = new GameButton(fileName, command, gl);
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

}