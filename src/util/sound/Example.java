package util.sound;

/**
 * Example usage of Sound Player utility
 * 
 * @author volodymyr
 *
 */
public class Example {
    public static void main (String[] args) {
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
        }
        catch (InterruptedException e) {
        }
    }
}

