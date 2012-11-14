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
public class OnButton {

    private static JFrame startFrame;
    private static JPanel startPanel;

    private static final int FRAME_HEIGHT = 500;
    private static final int FRAME_WIDTH = 500;

    private static final int BUTTON_HEIGHT = 310;
    private static final int BUTTON_WIDTH = 310;

    /**
     * This code starts it all!
     * 
     * @param args
     */
    public static void main (String[] args) {

        // sets up the jframe and jpanel
        startFrame = new JFrame("Are you ready?");
        startFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        startFrame.setResizable(false);
        startPanel = new JPanel();
//        startPanel.setLayout(new BorderLayout());
        startPanel.setBackground(Color.BLACK);

        addPowerButton();

//        startFrame.getContentPane().add(startPanel, BorderLayout.CENTER);
        startFrame.getContentPane().add(startPanel);

        // starts the jframe
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLocationRelativeTo(null);

        startFrame.pack();
        startFrame.setVisible(true);
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
                startFrame.dispose();
            }
        });

        startPanel.add(powerButton, BorderLayout.CENTER);
    }
}
