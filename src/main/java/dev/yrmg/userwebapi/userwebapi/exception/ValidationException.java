package dev.yrmg.userwebapi.userwebapi.exception;

public class ValidationException extends Exception{
    private String message;

    public ValidationException(String message){
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
