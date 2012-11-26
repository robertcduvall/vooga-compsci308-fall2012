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
        Map<String,String> storingData= new HashMap<String, String>();
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
        
        Map<String,String> newData= new HashMap<String, String>();
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
    }
}
