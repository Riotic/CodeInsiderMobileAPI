package com.codeinsider.Model;

public class Etudiant {
    private Integer id;
    private String email;
    private String firstname;
    private String name;
    private String birthday;
    private String localization;
    private String curriculum_year;
    private String requested_remuneration;
    private String skills;
    private String phone_number;
    private String contract_type;
    private String contract_time;

    public Etudiant(Integer id,String email, String firstname, String name, String birthday, String localization, String phone_number, String curriculum_year, String requested_remuneration, String skills, String contract_type, String contract_time) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.name = name;
        this.birthday = birthday;
        this.localization = localization;
        this.phone_number = phone_number;
        this.curriculum_year = curriculum_year;
        this.requested_remuneration = requested_remuneration;
        this.skills = skills;
        this.contract_type = contract_type;
        this.contract_time = contract_time;
    }

    public String getId(){
        return id.toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getCurriculum_year() {
        return curriculum_year;
    }

    public void setCurriculum_year(String curriculum_year) {
        this.curriculum_year = curriculum_year;
    }

    public String getRequested_remuneration() {
        return requested_remuneration;
    }

    public void setRequested_remuneration(String requested_remuneration) {
        this.requested_remuneration = requested_remuneration;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public String getContract_time() {
        return contract_time;
    }

    public void setContract_time(String contract_time) {
        this.contract_time = contract_time;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
