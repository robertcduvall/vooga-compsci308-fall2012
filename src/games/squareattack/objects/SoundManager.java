package games.squareattack.objects;

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
    public static int COLLISION_SOUND =1;
    public static int PLANT_SQUARE_SOUND =2;
    public static int THEME_ONE = 10;
    private Map<Integer,SoundPlayer> mySoundMap = new HashMap<Integer,SoundPlayer>();
   
    
    private SoundManager(){
        
        createSoundMap();
    
       
    }
    
    private void createSoundMap(){
        mySoundMap.put(COLLISION_SOUND, new SoundPlayer("src/games/squareattack/enemy_death_one.mp3"));
        mySoundMap.put(THEME_ONE, new SoundPlayer("src/games/squareattack/theme.mp3"));
    }
    
    public void playSound(int soundID){
        mySoundMap.get(soundID).playOnce();
    }
    public void playSound(int soundID, boolean loop){
        if(loop){
            mySoundMap.get(soundID).startLoop();
        }
        else{
            mySoundMap.get(soundID).playOnce();
        }
    }
    
    public static SoundManager getInstance(){
        if (ourInstance == null) {
            ourInstance = new SoundManager();
        }
        return ourInstance;
    }
    
    private void sampleCode(){
        try {
            SoundPlayer p = new SoundPlayer("src/util/sound/sounds/teleport.wav");
            // every time playOnce is called, a new thread is created. it would
            // be removed after playback is over
            p.playOnce();
            SoundPlayer p1 = new SoundPlayer("src/util/sound/sounds/laser.wav");
            // loop also runs in its own thread.
            p1.startLoop();
            Thread.sleep(5000);
            // loop needs to be stopped at some point
            p1.stopLoop();
            SoundPlayer p2 = new SoundPlayer("src/util/sound/sounds/benny_benassi.mp3");
            p2.playOnce();
            Thread.sleep(30000);
            // sounds threads are daemons, meaning JVM will stop when no other threads are active
        }
        catch (InterruptedException e) {
        }
    }

}
