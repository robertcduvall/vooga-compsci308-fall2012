package util.networking.datatable;

import java.net.UnknownHostException;
import util.datatable.DataTable;
import util.networking.Server;

public class DataServer extends Server {
    
    private DataProtocol myDataProtocol;
    
    private final static int DEFAULT_MAX_CONNECTIONS = 1;
    
    
    public DataServer (DataProtocol d) throws UnknownHostException {
        this(DEFAULT_MAX_CONNECTIONS, d);
    }
    
    public DataServer(int maxConnections, DataProtocol d) throws UnknownHostException{
        super(maxConnections);
        myDataProtocol = d;
    }
    
    
}
