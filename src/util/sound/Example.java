package util.sound;

public class Example {
    public static void main (String[] args) {
        SoundPlayer p = new SoundPlayer("src/util/sound/sounds/teleport.wav");
        p.playOnce();
        p = new SoundPlayer("src/util/sound/sounds/laser.wav");
        p.startLoop();
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
        }
        p.stopLoop();
        p = new SoundPlayer("src/util/sound/sounds/benny_benassi.wav");
        p.playOnce();
    }
}
