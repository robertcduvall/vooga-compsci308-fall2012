package util.datatable;

import java.util.HashMap;
import java.util.Map;


public class UnmodifiableRowElement {

    protected Map<String,String> myData;
    
    public UnmodifiableRowElement (RowElement re) {
        myData=new HashMap<String,String>();
        myData.putAll(re.getAllData());
    }
    
    public String getEntry(String s) {
        return myData.get(s);
    }
   
    protected Map<String,String> getAllData(){
        Map<String,String> cpmap= new HashMap<String, String> ();
        cpmap.putAll(myData);
        return cpmap;
    }

}
