package games.dynamicControlSwitchingGame;

/*
 * This applet demonstrates Focus events and Key events. A colored square
 * is drawn on the applet. By pressing the arrow keys, the user can move
 * the square up, down, left, or right. By pressing the keys
 * R, G, B, or K, the user can change the color of the square to red,
 * green, blue, or black, respectively. Of course, none of the keys
 * will have any effect if the applet does not have the keyboard input
 * focus. The applet changes appearance when it has the input focus.
 * A cyan-colored border is drawn around it. When it doesn not have
 * the input focus, the message "Click to activate" is displayed
 * and the border is gray.
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.input.configurecontrollergui.RunControlGUISwitcher;
import util.input.core.KeyboardController;
import util.input.core.MouseController;
import util.input.factories.ControllerFactory;
import util.input.tests.SpriteTestingInput;
import vooga.platformer.leveleditor.Sprite;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;


/**
 * Press Space to activate the dynamic control switching
 * 
 * @author Amay
 *
 */
public class MovingBlock extends Applet {
    // (Note: MouseListener is implemented only so that
    // the applet can request the input focus when
    // the user clicks on it.)

    static final int SQUARE_SIZE = 40;  // Length of a side of the square.

    Color squareColor;  // The color of the square.

    int squareTop, squareLeft;  // Coordinates of top-left corner of square.

    boolean focussed = false;   // True when this applet has input focus.

    private MouseController myMouseController;

    private KeyboardController myKeyController;

    SpriteTestingInput mainPlayer = new SpriteTestingInput();

    public void init () {
        // Initialize the applet; set it up to receive keyboard
        // and focus events. Place the square in the middle of
        // the applet, and make the initial color of the square red.
        setControllers();
        try {
            configureActions();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setBackground(Color.white);
        squareTop = getSize().height / 2 - SQUARE_SIZE / 2;
        squareLeft = getSize().width / 2 - SQUARE_SIZE / 2;
        squareColor = Color.red;
        setFocusable(true);
        requestFocus();
        // addFocusListener(this);
    }

    private void setControllers () {
        myMouseController = (MouseController) ControllerFactory.createMouseController(this);
        // myMouseController.subscribe(this);
        myKeyController = (KeyboardController) ControllerFactory.createKeyboardController(this);
    }

    public void configureActions () throws IllegalAccessException, InstantiationException {
        try {
            //myMouseController.setControl(MouseEvent.BUTTON1, MouseController.PRESSED, mainPlayer,
                                         //"move");
            // myMouseController.setControl(WiiController.WIIMOTE_BUTTON_LEFT,
            // WiiController.BUTTON_PRESSED, mainPlayer, "jump");
            myKeyController.setControl(KeyEvent.VK_UP, KeyboardController.PRESSED, this,
                                       "moveUp", "Up Pressed", "move up");
            myKeyController.setControl(KeyEvent.VK_DOWN, KeyboardController.PRESSED, this,
                    "moveDown", "Down Pressed", "move down");
            myKeyController.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED, this,
                    "moveLeft","Left Pressed", "move left");
            myKeyController.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED, this,
                    "moveRight", "Right Pressed", "move right");
            myKeyController.setControl(KeyEvent.VK_W, KeyboardController.PRESSED, this,
                    "doNothing", "W Pressed", "do nothing");
            myKeyController.setControl(KeyEvent.VK_A, KeyboardController.PRESSED, this,
                    "doNothing", "A Pressed", "do nothing");
            myKeyController.setControl(KeyEvent.VK_S, KeyboardController.PRESSED, this,
                    "doNothing", "S Pressed", "do nothing");
            myKeyController.setControl(KeyEvent.VK_D, KeyboardController.PRESSED, this,
                    "doNothing","D Pressed", "do nothing");
            myKeyController.setControl(KeyEvent.VK_SPACE, KeyboardController.PRESSED, this,
                    "launchSwitcher");
        }
        catch (NoSuchMethodException e) {
            //Do nothing
        }

    }
    
    public void launchSwitcher() throws NoSuchMethodException, IllegalAccessException, RepeatedColumnNameException, InvalidXMLTagException {
        RunControlGUISwitcher obj = new RunControlGUISwitcher();
        obj.initGUISwitcher(myKeyController);
    }
    
    public void doNothing() {
        //literally does nothing
    }

    public void paint (Graphics g) {
        // Draw the contents of the applet.

        /*
         * Draw a 3-pixel border, colored cyan if the applet has the
         * keyboard focus, or in light gray if it does not.
         */

        if (focussed)
            g.setColor(Color.cyan);
        else g.setColor(Color.lightGray);

        int width = getSize().width;  // Width of the applet.
        int height = getSize().height; // Height of the applet.
        g.drawRect(0, 0, width - 1, height - 1);
        g.drawRect(1, 1, width - 3, height - 3);
        g.drawRect(2, 2, width - 5, height - 5);

        /* Draw the square. */

        g.setColor(squareColor);
        g.fillRect(squareLeft, squareTop, SQUARE_SIZE, SQUARE_SIZE);

    }  // end paint()

    public void focusGained (FocusEvent evt) {
        // The applet now has the input focus.
        focussed = true;
        repaint();  // redraw with cyan border
    }

    public void focusLost (FocusEvent evt) {
        // The applet has now lost the input focus.
        focussed = false;
        repaint();  // redraw without cyan border
    }
    
    public void moveLeft() {
        squareLeft -= 8;
        if (squareLeft < 3) squareLeft = 3;
        repaint();
    }
    
    public void moveRight() {
        squareLeft += 8;
        if (squareLeft > getSize().width - 3 - SQUARE_SIZE)
            squareLeft = getSize().width - 3 - SQUARE_SIZE;
        repaint();
    }
    
    public void moveUp() {
        squareTop -= 8;
        if (squareTop < 3) squareTop = 3;
        repaint();
    }
    
    public void moveDown() {
        squareTop += 8;
        if (squareTop > getSize().height - 3 - SQUARE_SIZE)
            squareTop = getSize().height - 3 - SQUARE_SIZE;
        repaint();
    }
}