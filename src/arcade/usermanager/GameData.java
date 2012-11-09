package arcade.usermanager;

public class GameData {
    private String myGameName;
    private String myGameInfo;
    private int myHighScore;
    private int myTimesPlayed;

    public GameData (String name, String gameInfo, int highScore, int timesPlayed) {
        myGameName = name;
        myGameInfo = gameInfo;
        myHighScore = highScore;
        myTimesPlayed = timesPlayed;
    }
}
