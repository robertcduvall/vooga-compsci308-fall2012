package util.sound;

public class Example {
    public static void main (String[] args) {
        SoundPlayer p = new SoundPlayer("src/util/sound/sounds/teleport.wav");
        // every time playOnce is called, a new thread is created. it would be removed after playback is over
        p.playOnce();
        p.playOnce();
        SoundPlayer p1 = new SoundPlayer("src/util/sound/sounds/laser.wav");
        // loop also runs in its own thread.
        p1.startLoop();
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
        }
        // loop needs to be stopped at some point
        p1.stopLoop();
        SoundPlayer p2 = new SoundPlayer("src/util/sound/sounds/benny_benassi.wav");
        p2.playOnce();
    }
}
