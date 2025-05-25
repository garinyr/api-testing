package com.afterofficetest.model.register;

public class RequestRegister {
    private String email;
    private String full_name;
    private String password;
    private String department;
    private String phone_number;

    public RequestRegister() {
    }

    public RequestRegister(String email, String full_name, String password, String department, String phone_number) {
        this.email = email;
        this.full_name = full_name;
        this.password = password;
        this.department = department;
        this.phone_number = phone_number;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    

}
