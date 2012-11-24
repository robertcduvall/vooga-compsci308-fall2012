package util.datatable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;

public class RowElement {
    
    protected Map<String,String> myData;
    
    public RowElement(){
        myData=new HashMap<String,String>();
    }
    
    public RowElement(Map<String,String> map){
        this();
        myData.putAll(map);
    }
    
    public void setEntry(String s,String s2) throws UnrecognizedColumnNameException{
        if (myData.containsKey(s)){
            myData.put(s, s2);
        }
        else{
            throw new UnrecognizedColumnNameException(s);
        }
    }
    

    public void setEntry (Map<String, String> map) throws UnrecognizedColumnNameException {
        Set<String> keyset=map.keySet();
        Iterator<String> it=keyset.iterator();
        while(it.hasNext()){
            String pKey=(String) it.next();
            if(myData.containsKey(pKey)){
                myData.put(pKey,map.get(pKey));
            }
            else{
                throw new UnrecognizedColumnNameException(pKey);
            }
        }
    }
    
    public String getEntry(String s) {
        return myData.get(s);
    }
   
    protected Map<String,String> getAllData(){
        Map<String,String> cpmap= new HashMap<String, String> ();
        cpmap.putAll(myData);
        return cpmap;
    }
    
    protected void addNewColumn(String s) throws RepeatedColumnNameException  {
        if (!myData.containsKey(s)){
            myData.put( s, null);    
        }
        else{
            throw new RepeatedColumnNameException(s);   
        }
    }

    public void printData () {
        System.out.println(myData);
        
    }
}
