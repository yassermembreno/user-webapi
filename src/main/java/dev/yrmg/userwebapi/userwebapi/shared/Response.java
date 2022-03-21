package dev.yrmg.userwebapi.userwebapi.shared;

import java.util.List;

public class Response<T> {
    private boolean success;
    private String message;
    private List<String> messages;
    private T data;

    public Response(){        
    }

    public Response(boolean success, String message, List<String> messages){
        this.success = success;
        this.message = message;
        this.messages = messages;
    }

    public Response(boolean success, String message, List<String> messages, T data){
        this(success, message, messages);
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    
}
