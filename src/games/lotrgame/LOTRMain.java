package games.lotrgame;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * 
 * @author Michael Deng
 *
 */
public class LOTRMain implements IArcadeGame {

    private static final String DESCRIPTION = "This is the LOTR Game!";
    private static final String NAME = "The Lord of the Rings Game";
    private static final String MAIN_IMAGE = "balrog.png";
    
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> screenShots = new ArrayList<Image>();
        screenShots.add(getMainImage());
        return screenShots;
    }

    @Override
    public Image getMainImage () {
        ImageIcon mainImage = new ImageIcon(getClass().getResource(MAIN_IMAGE));
        return mainImage.getImage();
    }

    @Override
    public String getDescription () {
        return DESCRIPTION;
    }

    @Override
    public String getName () {
        return NAME;

    }

}
