package arcade;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterTools {
    private String consumerKey = "z5zRan2VqwBMLdq5VMRzXA";
    private String consumerSecret = "T7whmI8IBtcHUEBNsWoQhu39f68loybHOmSYl8DMDg";

    
    public AccessToken requestAccessToken () throws Exception {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println("Open the following URL and grant access to your account:");
            java.awt.Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Enter the PIN(if available) or just hit enter.[PIN]:");
            String pin = br.readLine();
            try {
                if (pin.length() > 0) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                }
                else {
                    accessToken = twitter.getOAuthAccessToken();
                }
            }
            catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    System.out.println("Unable to get the access token.");
                }
                else {
                    te.printStackTrace();
                }
            }
        }
        // persist to the accessToken for future reference.
        storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
        return accessToken;
    }

    private void storeAccessToken (long useId, AccessToken accessToken) {
        String token = accessToken.getToken();
        String tokenSecret = accessToken.getTokenSecret();
        //TODO: add code to store tokens in user XML?
    }
    
    private void createConfig (String token, String tokenSecret) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(token)
                .setOAuthAccessTokenSecret(tokenSecret);
    }

    public void updateStatus (String statusText) throws Exception {
        
        //TODO: Make this happen only if needing a new access token
        AccessToken at = requestAccessToken();
        //TODO: createConfig with loaded access tokens from user XML
        createConfig(at.getToken(),at.getTokenSecret());
        
        Twitter twitter = TwitterFactory.getSingleton();
        Status status = twitter.updateStatus(statusText);
        System.out.println("Successfully updated the status to [" + status.getText() + "].");
        System.exit(0);
    }

    public static void main (String[] args) {
        try {
            TwitterTools run = new TwitterTools();
            run.updateStatus("This tweet made via CS308 Arcade");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
