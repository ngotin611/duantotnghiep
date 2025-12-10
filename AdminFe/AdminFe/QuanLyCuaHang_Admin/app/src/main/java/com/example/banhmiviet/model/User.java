package com.example.banhmiviet.model;

public class User {

    public enum Status {
        PENDING,   // Chờ admin duyệt
        ACTIVE,    // Được phép login
        REJECTED   // Bị từ chối
    }

    private String username;
    private String password;
    private String role;   // "Admin" / "Nhân viên"
    private Status status;

    // Thông tin hồ sơ để map sang Employee
    private String name;
    private String phone;
    private String email;
    private int age;
    private String gender;

    // Constructor dùng cho admin mẫu (không cần info)
    public User(String username, String password, String role, Status status) {
        this(username, password, role, status, null, null, null, 0, null);
    }

    // Constructor đầy đủ
    public User(String username, String password, String role, Status status,
                String name, String phone, String email, int age, String gender) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public boolean isCredentialMatch(String inputUser, String inputPass, String inputRole) {
        return username.equals(inputUser)
                && password.equals(inputPass)
                && role.equalsIgnoreCase(inputRole);
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public boolean isPending() {
        return status == Status.PENDING;
    }

    public boolean isRejected() {
        return status == Status.REJECTED;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // thông tin hồ sơ
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void updateProfile(String name, String phone, String email, int age, String gender) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
