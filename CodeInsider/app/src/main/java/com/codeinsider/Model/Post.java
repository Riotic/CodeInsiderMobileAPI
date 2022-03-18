package com.codeinsider.Model;

public class Post {
    private String id;
    private String job_name;
    private String description;
    private String contract_type;
    private String company_id;

    public void Post(String id, String company_id, String job_name, String contract_type, String description){
        this.id = id;
        this.company_id = company_id;
        this.job_name = job_name;
        this.contract_type = contract_type;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getCompany_id() {
        return company_id;
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
