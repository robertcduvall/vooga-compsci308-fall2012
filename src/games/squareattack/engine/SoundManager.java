package games.squareattack.engine;

import java.util.HashMap;
import java.util.Map;
import util.sound.SoundPlayer;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class SoundManager {
    private static SoundManager ourInstance;
    public static int COLLISION_SOUND = 1;
    public static int PLANT_SQUARE_SOUND = 2;
    public static int THEME_ONE = 10;
    public static int WALL_BALL_BREAK = 11;
    private Map<Integer, SoundPlayer> mySoundMap = new HashMap<Integer, SoundPlayer>();

    private SoundManager () {

        createSoundMap();

    }

    private void createSoundMap () {
        mySoundMap.put(COLLISION_SOUND,
                       new SoundPlayer("src/games/squareattack/enemy_death_one.mp3"));
        mySoundMap.put(THEME_ONE, new SoundPlayer("src/games/squareattack/theme.mp3"));
        mySoundMap
                .put(PLANT_SQUARE_SOUND, new SoundPlayer("src/games/squareattack/shield_pop.mp3"));
        mySoundMap.put(WALL_BALL_BREAK, new SoundPlayer("src/games/squareattack/plasma_shot.mp3"));
    }

    public void playSound (int soundID) {
        mySoundMap.get(soundID).playOnce();
    }

    public void playSound (int soundID, boolean loop) {
        if (loop) {
            mySoundMap.get(soundID).startLoop();
        }
        else {
            mySoundMap.get(soundID).playOnce();
        }
    }

    public static SoundManager getInstance () {
        if (ourInstance == null) {
            ourInstance = new SoundManager();
        }
        return ourInstance;
    }

}
