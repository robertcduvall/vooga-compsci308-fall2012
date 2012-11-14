package vooga.shooter.implementation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.Enemy;


public class Level1 extends Level {

    private List<Sprite> mySprites;
    private Image enemyImage;
    private ImageIcon imageIcon;

    public void startLevel () {
        imageIcon = new ImageIcon(this.getClass().getResource("../vooga/shooter/images/alien.png"));
        enemyImage = imageIcon.getImage();

        mySprites.add(new Enemy(new Point(100, 100), new Dimension(20, 20), enemyImage, 10));
    }
}
