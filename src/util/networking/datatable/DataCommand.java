package util.networking.datatable;

import util.networking.chat.ChatCommand;

/**
 *
 * @author Oren Bukspan
 */
public enum DataCommand {

    ADD_COLUMNS ("ADDCOLUMNS"),
    ADD_ROW ("ADDROW"),
    DELETE ("DELETE"),
    GET ("GET"),
    EDIT ("EDIT"),
    FIND ("FIND"),
    FILE ("FILE"),
    ROWELEMENT ("ROWELEMENT");;
    
    private String myCommandName;
    
    private DataCommand(String commandName) {
        myCommandName = commandName;
    }
    
    public String toString()  {
        return myCommandName;
    }

    /**
     * Finds a corresponding ChatCommand from the given String.
     * @param commandName A case-insensitive string to match.
     * @return The ChatCommand whose name matches this String.
     */
    public static DataCommand getFromString(String commandName) {
        String capitalName = commandName.toUpperCase();
        for (DataCommand c : values()) {
            if (c.myCommandName.equals(capitalName)) {
                return c;
            }
        }
        return null;
    }
    
}
