package com.example.banhmiviet.model;

public class Employee {
    private String name, email, phone, gender, username, password;
    private int age;

    public Employee(String name, String email, String phone, int age, String gender, String username, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.username = username;
        this.password = password;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
