package games.buttongame;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


/**
 * A game of patience and endurance.  Implements highscore submission.
 * 
 * @author Howard
 * 
 */

public class ButtonGame implements IArcadeGame {
    private JFrame myFrame;
    private JLabel scoreLabel;
    private int score;
    private GameSaver myGameSaver;
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        myFrame = new JFrame("The Button Game");
        score = 0;
        myGameSaver = s;

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, 3));

        scoreLabel = new JLabel("Score: 0");
        myPanel.add(scoreLabel);

        JButton button = new JButton("Click me to increase your score");
        button.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                increaseScore();
            }
        });
        myPanel.add(button);

        JButton surrender = new JButton("I give up, this game is too hard");
        surrender.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                endGame();
            }
        });
        myPanel.add(surrender);

        myFrame.setLocationRelativeTo(null);
        myFrame.add(myPanel);
        myFrame.pack();
        myFrame.setVisible(true);

    }

    private void increaseScore () {
        score++;
        scoreLabel.setText("Score: " + score);
    }

    private void endGame () {
        JOptionPane.showMessageDialog(null, "Your score was " + score);
        myGameSaver.saveHighScore(String.valueOf(score));
        myFrame.dispose();
    }

    @Override
    public List<Image> getScreenshots () {
        return null;
    }

    @Override
    public Image getMainImage () {
        BufferedImage profilepic = null;
        try {
            profilepic = ImageIO.read(new File("src/games/buttongame/push-button.jpg"));
        }
        catch (IOException e) {
            System.out.println("Error! Could not find image!");
        }
        return profilepic;
    }

    @Override
    public String getDescription () {
        return "How many times can YOU click the button?";
    }

    @Override
    public String getName () {
        return "The Button Game";
    }

}
