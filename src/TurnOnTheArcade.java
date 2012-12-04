import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import arcade.gui.Arcade;
import arcade.utility.ImageReader;


/**
 * This class turns on the arcade. Just press the On Button.
 * 
 * @author Michael Deng
 * 
 */
public class TurnOnTheArcade {

    /**
     * Select whether or not to load arcade with a power button
     */
    private static final boolean START_WITH_POWER_BUTTON = false;

    private static final int FRAME_HEIGHT = 500;
    private static final int FRAME_WIDTH = 500;

    private static final int BUTTON_HEIGHT = 190;
    private static final int BUTTON_WIDTH = 190;

    private static JFrame myStartFrame;
    private static JPanel myStartPanel;

    /**
     * This code starts it all!
     * 
     * @param args
     */
    public static void main (String[] args) {
        if (START_WITH_POWER_BUTTON) {
            startWithPowerButton();
        }
        else {
            startWithoutPowerButton();
        }
    }

    /**
     * Starts the arcade without a power button
     */
    private static void startWithoutPowerButton () {
        Arcade theArcade = new Arcade();
    }

    /**
     * Starts the arcade with a power button
     */
    private static void startWithPowerButton () {

        // sets up the jframe and jpanel
        myStartFrame = new JFrame("Ready?");
        myStartFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        myStartFrame.setResizable(false);
        myStartPanel = new JPanel();
        myStartPanel.setBackground(Color.BLACK);

        addPowerButton();

        myStartFrame.getContentPane().add(myStartPanel);

        // starts the jframe
        myStartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myStartFrame.setLocationRelativeTo(null);

        myStartFrame.pack();
        myStartFrame.setVisible(true);

    }

    private static void addPowerButton () {
        ImageIcon icon =
                new ImageIcon(ImageReader.loadImage("src/arcade/gui/images", "powerbutton.png"));
        JButton powerButton = new JButton(icon);
        powerButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        powerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                @SuppressWarnings("unused")
                Arcade theArcade = new Arcade();
                myStartFrame.dispose();
            }
        });

        myStartPanel.add(powerButton, BorderLayout.CENTER);
    }
}
