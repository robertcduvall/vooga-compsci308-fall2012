package util.input.tests;


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
import util.input.core.MouseController;
import util.input.factories.ControllerFactory;
import vooga.platformer.leveleditor.Sprite;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;


public class InputTester extends Applet implements MouseInputListener {
    // (Note: MouseListener is implemented only so that
    // the applet can request the input focus when
    // the user clicks on it.)

    static final int SQUARE_SIZE = 40;  // Length of a side of the square.

    Color squareColor;  // The color of the square.

    int squareTop, squareLeft;  // Coordinates of top-left corner of square.

    boolean focussed = false;   // True when this applet has input focus.

    private MouseController myMouseController;

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
        // Wiimote wiiObj = connectWii();
        // System.out.println(wiiObj);
        myMouseController = (MouseController) ControllerFactory
                .createMouseController(this);
        myMouseController.subscribe(this);
    }

    private Wiimote connectWii () {
        Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
        Wiimote wiimote = wiimotes[0];
        return wiimote;
    }

    public void configureActions () throws IllegalAccessException,
            InstantiationException {
        try {
            System.out.println("configureActions");
            myMouseController.setControl(MouseEvent.BUTTON1,
                    MouseController.PRESSED, Sprite.class, "move");
            // myMouseController.setControl(WiiController.WIIMOTE_BUTTON_LEFT,
            // WiiController.BUTTON_PRESSED, mainPlayer, "jump");
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

        /* If the applet does not have input focus, print a message. */

        if (!focussed) {
            g.setColor(Color.magenta);
            g.drawString("Click to activate", 7, 20);
        }

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

    public void keyTyped (KeyEvent evt) {
        // The user has typed a character, while the
        // apple thas the input focus. If it is one
        // of the keys that represents a color, change
        // the color of the square and redraw the applet.

        char ch = evt.getKeyChar();  // The character typed.

        if (ch == 'B' || ch == 'b') {
            squareColor = Color.blue;
            repaint();
        }
        else if (ch == 'G' || ch == 'g') {
            squareColor = Color.green;
            repaint();
        }
        else if (ch == 'R' || ch == 'r') {
            squareColor = Color.red;
            repaint();
        }
        else if (ch == 'K' || ch == 'k') {
            squareColor = Color.black;
            repaint();
        }

    }  // end keyTyped()

    public void keyPressed (KeyEvent evt) {
        // Called when the user has pressed a key, which can be
        // a special key such as an arrow key. If the key pressed
        // was one of the arrow keys, move the square (but make sure
        // that it doesn't move off the edge, allowing for a
        // 3-pixel border all around the applet).

        int key = evt.getKeyCode();  // keyboard code for the key that was
                                    // pressed

        if (key == KeyEvent.VK_LEFT) {
            squareLeft -= 8;
            if (squareLeft < 3) squareLeft = 3;
            repaint();
        }
        else if (key == KeyEvent.VK_RIGHT) {
            squareLeft += 8;
            if (squareLeft > getSize().width - 3 - SQUARE_SIZE)
                squareLeft = getSize().width - 3 - SQUARE_SIZE;
            repaint();
        }
        else if (key == KeyEvent.VK_UP) {
            squareTop -= 8;
            if (squareTop < 3) squareTop = 3;
            repaint();
        }
        else if (key == KeyEvent.VK_DOWN) {
            squareTop += 8;
            if (squareTop > getSize().height - 3 - SQUARE_SIZE)
                squareTop = getSize().height - 3 - SQUARE_SIZE;
            repaint();
        }

    }  // end keyPressed()

    public void keyReleased (KeyEvent evt) {
        // empty method, required by the KeyListener Interface
    }

    public void mousePressed (MouseEvent evt) {
        // Request the input focus when the user clicks on
        // the applet. (On most platforms, there is no need
        // for this. However, Sun's own Java implementation
        // requires it.)
        requestFocus();
    }

    public void mouseEntered (MouseEvent evt) {
    }  // Required by the

    public void mouseExited (MouseEvent evt) {
    }   // MouseListener

    public void mouseReleased (MouseEvent evt) {
    } // interface.

    public void mouseClicked (MouseEvent evt) {
    }

    @Override
    public void mouseDragged (MouseEvent e) {

    }

    @Override
    public void mouseMoved (MouseEvent e) {
        System.out.println("Mouse Moved" + e);

    }

}