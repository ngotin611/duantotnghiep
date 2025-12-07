package com.example.banhmiviet.model;

public class Employee {

    private String id;
    private String name;
    private String role;
    private String phone;
    private String email;
    private int age;
    private String gender;

    // thêm
    private String username;
    private String password;

    // Constructor cũ (giữ nguyên để tương thích)
    public Employee(String id, String name, String role,
                    String phone, String email, int age, String gender) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    // Constructor mới có cả tài khoản & mật khẩu
    public Employee(String id, String name, String role,
                    String phone, String email, int age, String gender,
                    String username, String password) {
        this(id, name, role, phone, email, age, gender);
        this.username = username;
        this.password = password;
    }

    // ===== Getter & Setter =====
    public String getId()       { return id; }
    public String getName()     { return name; }
    public String getRole()     { return role; }
    public String getPhone()    { return phone; }
    public String getEmail()    { return email; }
    public int    getAge()      { return age; }
    public String getGender()   { return gender; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setId(String id)           { this.id = id; }
    public void setName(String name)       { this.name = name; }
    public void setRole(String role)       { this.role = role; }
    public void setPhone(String phone)     { this.phone = phone; }
    public void setEmail(String email)     { this.email = email; }
    public void setAge(int age)            { this.age = age; }
    public void setGender(String gender)   { this.gender = gender; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}
