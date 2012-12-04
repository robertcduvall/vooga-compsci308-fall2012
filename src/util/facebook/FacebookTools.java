package util.facebook;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;


/**
 * Provides functions for interfacing with Facebook.
 * 
 * @author Howard
 * 
 */
public class FacebookTools {
    private String myAppSecret = "8dd9279bccf9d6d81ba95d984143c86b";
    private String myAppID = "541735812503918";
    private DefaultFacebookClient myFacebookClient = new DefaultFacebookClient();

    /**
     * Requests an access token from Facebook, extends its expiration, and
     * returns it.
     * 
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public AccessToken requestAccessToken () throws IOException, URISyntaxException {
        java.awt.Desktop
                .getDesktop()
                .browse(new URI(
                                "https://graph.facebook.com/oauth/authorize?client_id=" +
                                        myAppID +
                                        "&redirect_uri=http://www.facebook.com/connect/login_success.html&scope=publish_stream&response_type=token"));

        String redirectURL =
                JOptionPane.showInputDialog(null, "Enter Redirect URL of Success page:");
        AccessToken at =
                DefaultFacebookClient.AccessToken.fromQueryString(redirectURL.split("#")[1]);
        return myFacebookClient
                .obtainExtendedAccessToken(myAppID, myAppSecret, at.getAccessToken());
    }

    /**
     * Sends a post to Facebook.
     * 
     * @param message
     * @param at
     * @throws IOException
     * @throws URISyntaxException
     * @throws Exception
     */
    public void publishPost (String message, AccessToken at) throws IOException,
                                                            URISyntaxException, Exception {
        // Publishing a simple message.
        myFacebookClient = new DefaultFacebookClient(at.getAccessToken());
        FacebookType publishMessageResponse =
                myFacebookClient.publish("me/feed", FacebookType.class,
                                         Parameter.with("message", message));
        System.out.println("Published message ID: " + publishMessageResponse.getId());
    }

}
