package util.datatable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;




public class Test {
    
    public static void main(String [] args){
        
        
        DataTable table=new DataTable();
        
        //TWO WAYS TO SET COLUMN NAME
        try {
            table.addNewColumn("");
           // System.out.println(table.getColumnNames().get(2));
        }
        catch (RepeatedColumnNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        String [] sarray=new String[3];
        sarray[0]="name";
        sarray[1]="gender";
        sarray[2]="password";
        try {
            table.addNewColumn(sarray);
           // System.out.println(table.getColumnNames().get(0));
        }
        catch (RepeatedColumnNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //get table's column names
        List<String> l=table.getColumnNames();


        //add new row
        Map<String,String> mappy= new HashMap<String, String>();
        mappy.put("address","LA");
        mappy.put("name", "bob");
        
        table.addNewRowEntry(mappy);
        
        
        //retrieve data
        UnmodifiableRowElement re= table.find("name","bob");
        
        //add new column
        try {
            table.addNewColumn("namez");
        }
        catch (RepeatedColumnNameException e) {
            // TODO Auto-generated catch block
            System.out.println("broke");
            e.getMessage();
        } //will add entry to every column
        
        List<String> li=table.getColumnNames();
        System.out.println(li.get(3));
        try {
            table.addNewColumn("passwordz");
        }
        catch (RepeatedColumnNameException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //edit row entry data
        try {
            table.editRowEntry("name","bob","password","newpassword");
        }
        catch (UnrecognizedColumnNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
        
        Map<String,String> map= new HashMap<String, String>();
        mappy.put("address","LAAZ");
        mappy.put("name", "bobby");
        
        //retrieve data
        UnmodifiableRowElement rez= table.find("name","bob");
        System.out.println(rez.getEntry("password"));
        
        try {
            table.editRowEntry("name","lance",map);
        }
        catch (UnrecognizedColumnNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        //delete row entry
        table.deleteRowEntry("name","bob");
        
        UnmodifiableRowElement rezz= table.find("name","bob");
        System.out.println(rezz.getEntry("password"));
        
        //loading and saving
        table.save("/resources/data.txt");
        table.load("/resources/data.txt");
        
        
    }
}
