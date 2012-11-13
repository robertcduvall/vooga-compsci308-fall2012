import java.awt.BorderLayout;
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

    private static final int frameHeight = 400;
    private static final int frameWidth = 400;

    private static final int buttonHeight = 400;
    private static final int buttonWidth = 400;

    /**
     * @param args
     */
    public static void main (String[] args) {

        // creates JFrame and waits for button press.
        startFrame = new JFrame("Are you ready?");
        startFrame.setSize(new Dimension(frameWidth, frameHeight));
        startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());

        addPowerButton();

        startFrame.getContentPane().add(startPanel, BorderLayout.CENTER);

        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.pack();
        startFrame.setVisible(true);

    }

    private static void addPowerButton () {
        ImageIcon icon =
                new ImageIcon(ImageReader.loadImage("src/arcade/gui/images", "powerbutton.png"));
        JButton powerButton = new JButton(icon);
        powerButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
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
