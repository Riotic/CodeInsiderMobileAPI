package com.codeinsider.Model;

public class Entreprise {

    private Integer id;
    private String name;
    private String localization;
    private String description;
    private String phone_number;
    private String email;

    public Entreprise(Integer id, String name, String localization, String description, String phone_number, String email){
        this.id = id;
        this.name = name;
        this.localization = localization;
        this.description = description;
        this.phone_number = phone_number;
        this.email = email;
    }

    public String getId(){
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
