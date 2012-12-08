package games.squareattack;

import games.squareattack.gui.GameFrame;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class PlaySquareAttack implements IArcadeGame {

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        new GameFrame();

    }

    @Override
    public List<Image> getScreenshots () {
        Image myImage = new ImageIcon("src/games/squareattack/squareattack.png").getImage();
        List<Image> imageList = new ArrayList<Image>();
        imageList.add(myImage);
        return null;
    }

    @Override
    public Image getMainImage () {
        Image myImage = new ImageIcon("src/games/squareattack/squareattack.png").getImage();
        return myImage;
    }

    @Override
    public String getDescription () {
        return "Square Attack! is an intense 4 player game of keep away. It supports multiple controller types and user features of an android touch screen and controller vibrations!";
    }

    @Override
    public String getName () {
        return "Square Attack!";
    }

    public static void main (String[] args) {

        GameFrame g = new GameFrame();

    }

}
