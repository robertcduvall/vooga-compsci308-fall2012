package util.datatable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;




public class Test {
    
    public static void main(String [] args){
        DataTable table=new DataTable();
        
        //setting column name by string (columns names are separated by comma)
        try {
            table.addNewColumn("name,gender,password,address");
        }
        catch (RepeatedColumnNameException e) {
            // exception thrown when column already exists
            e.printStackTrace();
        } 
        catch (InvalidXMLTagException e){
            e.printStackTrace();
        }
        
        //setting column name by adding an array
        String [] sarray=new String[1];
        sarray[0]="favoritecolor";
        try {
            table.addNewColumn(sarray);
        }
        catch (RepeatedColumnNameException e) {
            // exception thrown when column already exists
            e.printStackTrace();
        }
        catch (InvalidXMLTagException e){
            e.printStackTrace();
        }
        
        //get table's column names
        List<String> columnNames=(List<String>) table.getColumnNames();

        //adding a new row --null value will be stored in place of undefined entry
        Map<String,Object> storingData= new HashMap<String, Object>();
        storingData.put("address","LA");
        storingData.put("name", "bob");
        
        table.addNewRowEntry(storingData);
        

        //retrieving a table entry
        UnmodifiableRowElement re= table.find("name","bob");
        System.out.println(re.getEntry("name"));
        

        //edit row entry data
        try {
            table.editRowEntry("name","bob","password","newpassword");
        }
        catch (UnrecognizedColumnNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        System.out.println(table);

        Map<String,Object> newData= new HashMap<String, Object>();
        newData.put("address","LAAZ");
        newData.put("favoritecolor", "blue");
        
        try {
            table.editRowEntry("name","bob",newData);
        }
        catch (UnrecognizedColumnNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("oldtable");
        System.out.println(table);
       
        //delete row entry
      //  table.deleteRowEntry("name","bob");
        
       // UnmodifiableRowElement rowE= table.find("name","bob");
       // System.out.println(rowE + "hi");
        
        //loading and saving
        
        table.save("src/util/datatable/resources/data.txt");
        
        //DataTable 
        DataTable newtable = null;
        try {
            newtable = new DataTable();
            newtable.load("src/util/datatable/resources/data.txt");
        }
        catch (RepeatedColumnNameException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch ( InvalidXMLTagException e2){
            e2.printStackTrace();
        }
        System.out.println("new table");
        System.out.println(newtable);
        
        //retrieving a null entry from newly loaded table
        UnmodifiableRowElement rt = newtable.find("address", "LAAZ");
        System.out.println(rt.getEntry("gender").equals(""));
        
        
        
        //demo of inserting objects
        ModifiableRowElement rey= new ModifiableRowElement();
        try {
            rey.addNewColumn("phone");
        }
        catch (RepeatedColumnNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvalidXMLTagException e){
            e.printStackTrace();
        }
        
        try {
            rey.setEntry("phone","09090");
        }
        catch (UnrecognizedColumnNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            table.addNewColumn("ObjectTest");
        }
        catch (RepeatedColumnNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvalidXMLTagException e){
            e.printStackTrace();
        }
        
        Map<String,Object> myMap=new HashMap<String,Object>();
        myMap.put("ObjectTest", rey);
        myMap.put("address","DC");
        table.addNewRowEntry(myMap);
        
        System.out.println(table);
        UnmodifiableRowElement mmrey=table.find("address", "DC");
        System.out.println((RowElement) mmrey.getEntry("ObjectTest"));
    }
}
