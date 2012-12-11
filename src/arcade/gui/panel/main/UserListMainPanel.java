package arcade.gui.panel.main;
import java.util.ArrayList;
import java.util.List;
import arcade.gui.Arcade;
import arcade.gui.components.ArcadeListComponent;
import arcade.gui.components.UserListComponent;
import arcade.usermanager.UserProfile;


/**
 * A useful example of an ArcadeListMainPanel.
 * Displays an alphabetized list of all users with
 * buttons to go to their profiles or send them messages.
 * 
 * @author Robert Bruce
 */
public class UserListMainPanel extends ArcadeListMainPanel {

    /**
     * 
     * @param a Arcade to draw in.
     */
    public UserListMainPanel (Arcade a) {
        super(a);
    }

    @Override
    protected List<ArcadeListComponent> loadListComponents () {
        List<ArcadeListComponent> ret = new ArrayList<ArcadeListComponent>();
        List<UserProfile> allUsers = getArcade().getModelInterface()
                .getAllUsers();
        for (UserProfile user : allUsers) {
            ret.add(new UserListComponent(user, myPanel));
        }
        return ret;
    }
}
