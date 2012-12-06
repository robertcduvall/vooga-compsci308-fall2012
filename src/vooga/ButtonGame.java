package vooga;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


public class ButtonGame implements IArcadeGame {
    private JFrame myFrame;
    private JLabel scoreLabel;
    private int score;
    private boolean gameRunning;
    private GameSaver myGameSaver;

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        myFrame = new JFrame();
        score = 0;
        gameRunning = true;
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

        JButton surrender = new JButton("I surrender, this game has beaten me");
        surrender.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                endGame();
            }
        });
        myPanel.add(surrender);

        myFrame.add(myPanel);
        myFrame.pack();
        myFrame.setVisible(true);

    }

    private void increaseScore () {
        score++;
        scoreLabel.setText("Score: " + score);
    }

    private void endGame () {
        gameRunning = false;
        myGameSaver.saveHighScore(score);
        myFrame.dispose();
    }

    @Override
    public List<Image> getScreenshots () {
        return null;
    }

    @Override
    public Image getMainImage () {
        return null;
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
