package com.afterofficetest.model.register;

public class ResponseRegister {
    /**
     * {
    "id": "67",
    "email": "garinyr@gmail.com",
    "full_name": "Garin YR",
    "department": "Technology",
    "phone_number": "08121122334",
    "create_at": "2025-05-18T12:52:22.551Z",
    "update_at": "2025-05-18T12:52:22.551Z"
}
     */
    private String id;
    private String email;
    private String full_name;
    private String department;
    private String phone_number;
    private String create_at;
    private String update_at;

    public ResponseRegister() {
    }

    public ResponseRegister(String id, String email, String full_name, String department, String phone_number, String create_at, String update_at) {
        this.id = id;
        this.email = email;
        this.full_name = full_name;
        this.department = department;
        this.phone_number = phone_number;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    
}
