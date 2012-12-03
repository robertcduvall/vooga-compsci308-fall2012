package arcade.usermanager;

import java.util.Map;
import twitter4j.auth.AccessToken;
import util.twitter.TwitterTools;


public class TwitterTest {

    private TwitterTools tt = new TwitterTools();
    private UserManager um = UserManager.getInstance();

    public void updateStatus (String name, String statusText) {
        try {
            Map<String, String[]> twittokens = um.getTwitterTokens();
            AccessToken at;
            
            if (!twittokens.keySet().contains(name)) {
                at = tt.requestAccessToken();
                um.addTwitterToken(name, at);
            }
            else {
                at = new AccessToken(twittokens.get(name)[0], twittokens.get(name)[1], Long.parseLong(twittokens.get(name)[2]));
            }
            tt.updateStatus(statusText, at);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        TwitterTest test = new TwitterTest();
        test.updateStatus("michaeldeng", "test tweet no reauthentication part 8");
    }
}