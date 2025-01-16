package com.muvo.springapp.model;

public class LoginDTO {
    private int userId;
    private String email;
    private String token;

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginDTO() {
    }

    public LoginDTO(int userId, String email, String token) {
        this.userId = userId;
        this.email = email;
        this.token = token;
    }
}
