package arcade.utility;

import java.io.File;
import java.util.List;

public class ReadWriter {

    /**
     * Searches the indicated file for a tag matching the indicated query. This
     * could be used as a utility class for both users and games. Returns a List
     * of the game names that match the indicated query.
     * 
     * @param f file to be searched
     * @param query query to search for
     */
    public List<String> search (File f, String query) {
        // TODO
        return null;
    }

    /**
     * Stores a specific piece of data in a specific file, with a tag given to
     * specify where in the file it should be stored. This is a general method,
     * potentially a utility class, that can be used for any interaction with
     * the xml files.
     * 
     * @param f file in which to store data
     * @param tag location of the data
     * @param data data to be stored
     */
    public void storeData (File f, String tag, String data) {
        // TODO
    }

    /**
     * Stores a specific piece of data in a specific file, with a tag given to
     * specify where in the file it should be stored. This is a general method,
     * potentially a utility class, that can be used for any interaction with
     * the xml files.
     * 
     * @param f file to get data from
     * @param tag location of the data
     */
    public String loadData (File f, String tag) {
        // TODO
        return null;
    }
}
