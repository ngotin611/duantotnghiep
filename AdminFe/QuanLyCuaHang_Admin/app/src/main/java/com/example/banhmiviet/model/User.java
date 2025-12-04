package com.example.banhmiviet.model;

public class User {
    private String username;
    private String password;
    private String role; // "Admin" hoặc "Nhân viên"

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean checkLogin(String inputUser, String inputPass, String inputRole) {
        return username.equals(inputUser)
                && password.equals(inputPass)
                && role.equalsIgnoreCase(inputRole);
    }

    public String getRole() {
        return role;
    }
}
