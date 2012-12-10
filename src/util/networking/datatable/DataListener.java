package util.networking.datatable;

/**
 * @author Connor Gordon
 * @author Oren Bukspan
 *
 */
public interface DataListener {
        
        /**
         * Takes the MessageReceivedEvent triggered and handles it appropriately.
         * @param e MessageReceivedEvent passed in
         */
        void handleFoundRowElementEvent(FoundRowElementEvent e);
        
        /**
         * Takes the ErrorEvent triggered and handles it appropriately.
         * @param e ErrorEventEvent passed in
         */
        void handleColumnNamesEvent(ColumnNamesEvent e);
        
        /**
         * Takes the UsersUpdateEvent triggered and handles it appropriately.
         * @param e UsersUpdateEvent passed in
         */
        void handleDataRowsEvent(DataRowsEvent e);
}
