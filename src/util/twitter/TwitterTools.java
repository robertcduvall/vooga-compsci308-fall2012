package util.twitter;

import java.net.URI;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterTools {
    private Twitter twitter;
    private String consumerKey;
    private String consumerSecret;
    
    public TwitterTools(){
    String consumerKey = "z5zRan2VqwBMLdq5VMRzXA";
    String consumerSecret = "T7whmI8IBtcHUEBNsWoQhu39f68loybHOmSYl8DMDg";
    twitter = TwitterFactory.getSingleton();
    twitter.setOAuthConsumer(consumerKey, consumerSecret);
    }
    
    public AccessToken requestAccessToken () throws Exception {

        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        while (null == accessToken) {
            java.awt.Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
            String pin = javax.swing.JOptionPane.showInputDialog("Enter PIN:");
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
        return accessToken;
    }

    public void updateStatus (String statusText, AccessToken at) throws Exception {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(consumerKey);
        cb.setOAuthConsumerSecret(consumerSecret);
        cb.setOAuthAccessToken(at.getToken());
        cb.setOAuthAccessTokenSecret(at.getTokenSecret());
        twitter.setOAuthAccessToken(at);
        
        Status status = twitter.updateStatus(statusText);
        System.out.println("Successfully updated the status to [" + status.getText() + "].");
    }

}
