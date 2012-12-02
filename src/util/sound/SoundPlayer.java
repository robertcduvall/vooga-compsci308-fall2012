package util.sound;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * Provides lightweight player for *uncompressed* audio files.
 * Does not require external dependencies. Works on Java 6. 
 * Clears up used memory and threads after playback is finished.
 * Comes with a library of various sounds (util/sound/sounds/).
 * 
 * Usage: <a href='https://gist.github.com/4187082'>https://gist.github.com/4187082</a>
 * 
 * @author volodymyr
 *
 */
public class SoundPlayer {
    private Clip clip;
    private File soundFile;
    private AudioInputStream stream;

    /**
     * Player constructor
     * @param soundFilePath path to uncompressed sound file
     */
    public SoundPlayer (String soundFilePath) {
        soundFile = new File(soundFilePath);
    }

    private void initializeClip () {
        try {
            stream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(stream);
        }
        catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported File Format");
        }
        catch (IOException e) {
            System.err.println("Error Reading Sound File");
        }
        catch (LineUnavailableException e) {
            System.err.println("File Is Too Large");
        }

    }
    
    /**
     * Play once on demand
     */
    public void playOnce () {
        initializeClip();
        clip.start();
        clip.drain();
        clip.stop();
        clip.close();
    }

    /**
     * Start playing in a loop
     */
    public void startLoop () {
        initializeClip();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Loop needs to be stopped at some point.
     */
    public void stopLoop () {
        clip.stop();
        clip.close();
    }
}