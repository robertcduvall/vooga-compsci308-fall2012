package util.datatable;

import java.util.HashMap;
import java.util.Map;


public class UnmodifiableRowElement {

    protected Map<String , Object> myData;
    
    public UnmodifiableRowElement (RowElement re) {
        myData=new HashMap<String , Object>();
        myData.putAll(re.getAllData());
    }
    
    public Object getEntry(String s) {
        return myData.get(s);
    }
   
    protected Map <String , Object> getAllData(){
        Map<String , Object> cpmap= new HashMap<String, Object> ();
        cpmap.putAll(myData);
        return cpmap;
    }

}
