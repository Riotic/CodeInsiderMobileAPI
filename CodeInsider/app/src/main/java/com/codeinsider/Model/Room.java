package com.codeinsider.Model;

public class Room {
    private String id;
    private String student_id;
    private String company_id;

    public void Room(String id, String student_id, String company_id) {
        this.id = id;
        this.student_id = student_id;
        this.company_id = company_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getId() {
        return id;
    }

    public String getStudent_id() {
        return student_id;
    }
}
