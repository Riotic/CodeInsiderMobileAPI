package com.codeinsider.Model;

public class LoginResponseEntreprise {
    public String error;
    public String success;
    public Entreprise user;
    public String api_token;

    public void LoginResponseEntreprise(String error, String success, Entreprise user, String api_token) {
        this.error = error;
        this.success = success;
        this.user = user;
        this.api_token = api_token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Entreprise getUser() {
        return user;
    }

    public void setUser(Entreprise user) {
        this.user = user;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }
}