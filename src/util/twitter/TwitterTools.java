package util.twitter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


/**
 * Functions for interfacing with Twitter.
 * 
 * @author Howard
 * 
 */
public class TwitterTools {
    private Twitter myTwitter;
    private String myConsumerKey;
    private String myConsumerSecret;

    /**
     * Constructs an instance.
     */
    public TwitterTools () {
        myConsumerKey = "z5zRan2VqwBMLdq5VMRzXA";
        myConsumerSecret = "T7whmI8IBtcHUEBNsWoQhu39f68loybHOmSYl8DMDg";
        myTwitter = TwitterFactory.getSingleton();
        myTwitter.setOAuthConsumer(myConsumerKey, myConsumerSecret);
    }

    /**
     * Requests an access token from Twitter.
     * 
     * @return
     */
    public AccessToken requestAccessToken () throws IOException, URISyntaxException {
        AccessToken accessToken = null;
        try {
            RequestToken requestToken = myTwitter.getOAuthRequestToken();
            while (null == accessToken) {
                java.awt.Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
                String pin = javax.swing.JOptionPane.showInputDialog("Enter PIN:");
                if (pin.length() > 0) {
                    accessToken = myTwitter.getOAuthAccessToken(requestToken, pin);
                }
                else {
                    accessToken = myTwitter.getOAuthAccessToken();
                }
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

        return accessToken;
    }
/**
 * Posts a status to Twitter.
 * @param statusText Text of the messsage.
 * @param at AccessToken to validate with
 * @throws Exception
 */
    public void updateStatus (String statusText, AccessToken at) throws Exception {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(myConsumerKey);
        cb.setOAuthConsumerSecret(myConsumerSecret);
        cb.setOAuthAccessToken(at.getToken());
        cb.setOAuthAccessTokenSecret(at.getTokenSecret());
        myTwitter.setOAuthAccessToken(at);

        Status status = myTwitter.updateStatus(statusText);
        System.out.println("Successfully updated the status to [" + status.getText() + "].");
    }

}
