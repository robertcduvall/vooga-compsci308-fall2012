package util.networking.datatable;

import util.networking.chat.ChatCommand;

/**
 *
 * @author Oren Bukspan
 * @author Connor Gordon
 */
public enum DataCommand {

    ADD_COLUMNS ("AddColumns"),
    ADD_ROW ("AddRow"),
    CLEAR ("Clear"),
    DATA_ROWS ("DataRows"),
    COLUMN_NAMES ("ColumnNames"),
    DELETE ("Delete"),
    GET_DATA_ROWS ("GetDataRows"),
    GET_COLUMN_NAMES("GetColumnNames"),
    EDIT ("Edit"),
    FIND ("Find"),
    FOUND ("Found"),
    ROW_ELEMENT ("RowElement"),
    UNKNOWN ("Unknown");
    
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
        for (DataCommand c : values()) {
            if (c.myCommandName.equals(commandName)) {
                return c;
            }
        }
        return null;
    }
    
}
