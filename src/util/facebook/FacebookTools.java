package util.facebook;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;


public class FacebookTools {
    private String MY_APP_SECRET = "8dd9279bccf9d6d81ba95d984143c86b";
    private String MY_APP_ID = "541735812503918";
    private DefaultFacebookClient myFacebookClient = new DefaultFacebookClient();

    public AccessToken requestAccessToken () throws IOException, URISyntaxException {
        java.awt.Desktop
                .getDesktop()
                .browse(new URI(
                                "https://graph.facebook.com/oauth/authorize?client_id=" +
                                        MY_APP_ID +
                                        "&redirect_uri=http://www.facebook.com/connect/login_success.html&scope=publish_stream&response_type=token"));

        String userAccessToken = JOptionPane.showInputDialog(null, "Enter Access Token:");
        return myFacebookClient
                .obtainExtendedAccessToken(MY_APP_ID, MY_APP_SECRET, userAccessToken);
    }

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
