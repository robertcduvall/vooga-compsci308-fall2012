package util.sound;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Time;
import javax.media.format.AudioFormat;


/**
 * Plays audio files. Now supports compressed formats (e.g. mp3).
 * Clears up used memory and threads after playback is finished.
 * Comes with a library of various sounds (util/sound/sounds/).
 * 
 * Usage: <a
 * href='https://gist.github.com/4187082'>https://gist.github.com/4187082</a>
 * 
 * @author volodymyr
 * 
 */
public class SoundPlayer {
    private LoopPlayback myLoop;
    private URL mySoundURL;
    private boolean myLoopIsPlaying;

    private final int TIME_DELAY = 100;

    /**
     * Player constructor
     * 
     * @param soundFilePath path to uncompressed sound file
     */
    public SoundPlayer (String soundFilePath) {
        try {
            mySoundURL = new File(soundFilePath).toURI().toURL();
            Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
            Format input2 = new AudioFormat(AudioFormat.MPEG);
            Format output = new AudioFormat(AudioFormat.LINEAR);
            PlugInManager.addPlugIn("com.sun.media.codec.audio.mp3.JavaDecoder", new Format[] {
                    input1, input2 }, new Format[] { output }, PlugInManager.CODEC);
        }
        catch (Exception e) {

        }
    }

    /**
     * Play once on demand
     */
    public void playOnce () {
        Thread singlePlayback = new SinglePlayback();
        singlePlayback.start();
    }

    /**
     * Start playing in a loop
     */
    public void startLoop () {
        if (!myLoopIsPlaying) {
            myLoop = new LoopPlayback();
            myLoop.start();
            myLoopIsPlaying = true;
        }
    }

    /**
     * Loop needs to be stopped at some point.
     */
    public void stopLoop () {
        if (myLoopIsPlaying) {
            myLoop.stopLoop();
            myLoopIsPlaying = false;
            myLoop = null;
        }
    }

    /**
     * See if loop is currently playing
     */
    public boolean loopIsRunning () {
        return myLoopIsPlaying;
    }

    class SinglePlayback extends Thread {

        public SinglePlayback () {
            setDaemon(false);
        }

        @Override
        public void run () {
            try {
                Player singlePlayback = Manager.createPlayer(new MediaLocator(mySoundURL));
                singlePlayback.start();
                while (singlePlayback.getState() != Player.Prefetched) {
                    Thread.sleep(TIME_DELAY);
                }
                singlePlayback.close();
            }
            catch (IOException e) {
                System.err.println("Error Reading Sound File");
            }
            catch (NoPlayerException e) {
                System.err.println("Wrong Sound File");
            }
            catch (InterruptedException e) {
                System.err.println("Process interrupted");
            }
        }

    }

    class LoopPlayback extends Thread {
        private boolean myIsRunning;
        private Player myLoop;

        public LoopPlayback () {
            setDaemon(false);
        }

        @SuppressWarnings("static-access")
        @Override
        public void run () {
            myIsRunning = true;
            try {
                myLoop = Manager.createPlayer(new MediaLocator(mySoundURL));
                myLoop.start();
                while (myIsRunning) {
                    Thread.sleep(TIME_DELAY);
                    if (myLoop.getState() == myLoop.Prefetched) {
                        myLoop.setMediaTime(new Time(0));
                        myLoop.start();
                    }
                }
                myLoop.close();
            }
            catch (IOException e) {
                System.err.println("Error Reading Sound File");
            }
            catch (NoPlayerException e) {
                System.err.println("Wrong Sound File");
            }
            catch (InterruptedException e) {
                System.err.println("Process interrupted");
            }
        }

        public void stopLoop () {
            myIsRunning = false;
        }
    }
}
