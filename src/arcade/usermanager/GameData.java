package arcade.usermanager;

/**
 * 
 * @author Howard, modified by difan
 *
 */

public class GameData {
    private String myGameName;
    private String myGameInfo;
    private int myHighScore;
    private int myTimesPlayed;
    

    public GameData (String name, String gameInfo, int highScore, int timesPlayed) {
        setMyGameName(name);
        setMyGameInfo(gameInfo);
        setMyHighScore(highScore);
        setMyTimesPlayed(timesPlayed);
    }

    public String getMyGameName () {
        return myGameName;
    }

    public void setMyGameName (String myGameName) {
        this.myGameName = myGameName;
    }

    public String getMyGameInfo () {
        return myGameInfo;
    }

    public void setMyGameInfo (String myGameInfo) {
        this.myGameInfo = myGameInfo;
    }

    public int getMyHighScore () {
        return myHighScore;
    }

    public void setMyHighScore (int myHighScore) {
        this.myHighScore = myHighScore;
    }

    public int getMyTimesPlayed () {
        return myTimesPlayed;
    }

    public void setMyTimesPlayed (int myTimesPlayed) {
        this.myTimesPlayed = myTimesPlayed;
    }
}
