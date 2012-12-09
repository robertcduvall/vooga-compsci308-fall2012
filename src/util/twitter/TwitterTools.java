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
    private String ourConsumerKey;
    private String ourConsumerSecret;
    private Twitter myTwitter;
    private RequestToken myRequestToken;

    /**
     * Constructs an instance.
     */
    public TwitterTools () {
        ourConsumerKey = "z5zRan2VqwBMLdq5VMRzXA";
        ourConsumerSecret = "T7whmI8IBtcHUEBNsWoQhu39f68loybHOmSYl8DMDg";
        myTwitter = TwitterFactory.getSingleton();
        myTwitter.setOAuthConsumer(ourConsumerKey, ourConsumerSecret);
        try {
            myRequestToken = myTwitter.getOAuthRequestToken();
        }
        catch (TwitterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Requests an access token from Twitter.
     * 
     * @return
     */
    public AccessToken requestAccessToken () throws IOException, URISyntaxException {

        AccessToken accessToken = null;
        try {
            while (null == accessToken) {
                java.awt.Desktop.getDesktop().browse(new URI(myRequestToken.getAuthorizationURL()));
                String pin = javax.swing.JOptionPane.showInputDialog("Enter PIN:");
                accessToken = myTwitter.getOAuthAccessToken(myRequestToken, pin);
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
     * 
     * @param statusText Text of the messsage.
     * @param at AccessToken to validate with
     * @throws Exception
     */
    public void updateStatus (String statusText, AccessToken at) throws Exception {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(ourConsumerKey);
        cb.setOAuthConsumerSecret(ourConsumerSecret);
        cb.setOAuthAccessToken(at.getToken());
        cb.setOAuthAccessTokenSecret(at.getTokenSecret());

        myTwitter.setOAuthAccessToken(at);

        Status status = myTwitter.updateStatus(statusText);
        System.out.println("Successfully updated the status to [" + status.getText() + "].");
    }

}
