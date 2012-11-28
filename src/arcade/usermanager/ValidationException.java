package arcade.usermanager;

public final class ValidationException extends RuntimeException{
    private String myCause;
    public ValidationException(String status){
        myCause=status;
    }


}
