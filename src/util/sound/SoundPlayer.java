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
    private LoopPlayback loop;
    private URL soundURL;
    private boolean loopIsPlaying;

    /**
     * Player constructor
     * 
     * @param soundFilePath path to uncompressed sound file
     */
    public SoundPlayer (String soundFilePath) {
        try {
            soundURL = new File(soundFilePath).toURI().toURL();
            Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
            Format input2 = new AudioFormat(AudioFormat.MPEG);
            Format output = new AudioFormat(AudioFormat.LINEAR);
            PlugInManager.addPlugIn("com.sun.media.codec.audio.mp3.JavaDecoder",
                                    new Format[] { input1, input2 }, new Format[] { output },
                                    PlugInManager.CODEC);
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
        if (!loopIsPlaying) {
            loop = new LoopPlayback();
            loop.start();
            loopIsPlaying = true;
        }
    }

    /**
     * Loop needs to be stopped at some point.
     */
    public void stopLoop () {
        if (loopIsPlaying) {
            loop.stopLoop();
            loopIsPlaying = false;
            loop = null;   
        }
    }

    /**
     * See if loop is currently playing
     */
    public boolean loopIsRunning () {
        return loopIsPlaying;
    }

    class SinglePlayback extends Thread {

        public SinglePlayback() {
            setDaemon(false);
        }
        @Override
        public void run () {
            try {
                Player singlePlayback = Manager.createPlayer(new MediaLocator(soundURL));
                singlePlayback.start();
                while(singlePlayback.getState() != Player.Prefetched) {
                    Thread.sleep(100);
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
            }
        }

    }

    class LoopPlayback extends Thread {
        private boolean isRunning;
        private Player loop;

        public LoopPlayback() {
            setDaemon(false);
        }
        @Override
        public void run () {
            isRunning = true;
            try {
                loop = Manager.createPlayer(new MediaLocator(soundURL));
                loop.start();
                while (isRunning) {
                    Thread.sleep(100);
                    if (loop.getState() == loop.Prefetched) {
                        loop.setMediaTime(new Time(0));
                        loop.start();
                    }
                }
                loop.close();
            }
            catch (IOException e) {
                System.err.println("Error Reading Sound File");
            }
            catch (NoPlayerException e) {
                System.err.println("Wrong Sound File");
            }
            catch (InterruptedException e) {
            }
        }

        public void stopLoop () {
            isRunning = false;
        }
    }
}