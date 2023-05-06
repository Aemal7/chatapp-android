package com.example.smd_assignment2_v1;

public class Message {
    public static String SENT = "sent";
    public static String RECEIVED = "received";
    private int conversationId;
    private String message;
    private String status;

    public Message(int conversationId, String message, String status) {
        this.conversationId = conversationId;
        this.message = message;
        this.status = status;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
