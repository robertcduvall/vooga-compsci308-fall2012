package util.datatable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        
        //setting column name by adding an array
        String [] sarray=new String[2];
        sarray[0]="favorite color";
        sarray[1]="favorite band";
        try {
            table.addNewColumn(sarray);
        }
        catch (RepeatedColumnNameException e) {
            // exception thrown when column already exists
            e.printStackTrace();
        }
        
        //get table's column names
        List<String> columnNames=table.getColumnNames();

        
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


        table.viewContents();

        Map<String,Object> newData= new HashMap<String, Object>();
        newData.put("address","LAAZ");
        newData.put("favorite color", "blue");
        
        try {
            table.editRowEntry("name","bob",newData);
        }
        catch (UnrecognizedColumnNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        table.viewContents();
       
        //delete row entry
        table.deleteRowEntry("name","bob");
        
        UnmodifiableRowElement rowE= table.find("name","bob");
        System.out.println(rowE);
        
        //loading and saving
        table.save("/resources/data.txt");
        table.load("/resources/data.txt");
        
        
        //demo of inserting objects
        RowElement rey= new RowElement();
        try {
            rey.addNewColumn("phone");
        }
        catch (RepeatedColumnNameException e) {
            // TODO Auto-generated catch block
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
        Map<String,Object> myMap=new HashMap<String,Object>();
        myMap.put("ObjectTest", rey);
        myMap.put("address","DC");
        table.addNewRowEntry(myMap);
        
        table.viewContents();
        UnmodifiableRowElement mmrey=table.find("address", "DC");
        ((RowElement) mmrey.getEntry("ObjectTest")).printData();
    }
}
