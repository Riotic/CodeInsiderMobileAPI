package com.codeinsider.Model;

public class ResponseMessage {
    private String chatroom;
    private String error;

    public void ResponseMessage(String chatroom, String error){
        this.chatroom = chatroom;
        this.error = error;
    }

    public String getChatroom() {
        return chatroom;
    }

    public String getError() {
        return error;
    }
}
