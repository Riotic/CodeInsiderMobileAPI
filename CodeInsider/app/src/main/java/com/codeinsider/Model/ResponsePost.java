package com.codeinsider.Model;

public class ResponsePost {
    public String error;
    public String success;

    public void ResponsePost(String error, String success) {
        this.error = error;
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
