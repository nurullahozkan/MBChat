package com.example.testnew.Model;

public class Status {


    private String sender;
    private String message;
    private String type;

    public Status(String sender, String message, String type) {
        this.sender=sender;
        this.message=message;
        this.type=type;
    }

    public Status() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender=sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message=message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type=type;
    }
}
