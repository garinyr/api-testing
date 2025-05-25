package com.afterofficetest.model.login;

public class ResponseLogin {
    private String token;

    public ResponseLogin() {}

    public ResponseLogin(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
