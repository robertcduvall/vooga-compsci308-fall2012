package util.datatable.exceptions;

public class RepeatedColumnNameException extends Exception{
        
        private final static String DESCRIPTION=" Repeated Column Name ";
        public RepeatedColumnNameException(String repeatedColumn){
            super(DESCRIPTION+ repeatedColumn);
        }


}

