package com.codeinsider.Model;

public class EditPost {
    private String job_name;
    private String contract_type;
    private String description;


    public EditPost(String job_name, String contract_type, String description){
        this.job_name = job_name;
        this.contract_type = contract_type;
        this.description = description;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
