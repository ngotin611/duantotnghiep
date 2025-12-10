package com.example.banhmiviet.model;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final List<User> userList = new ArrayList<>();

    public static void initDefaultUsers() {
        if (userList.isEmpty()) {
            // Admin mặc định
            userList.add(new User(
                    "admin",
                    "1234",
                    "Admin",
                    User.Status.ACTIVE,
                    "Quản trị viên",
                    "0900000000",
                    "admin@banhmiviet.com",
                    30,
                    "Nam"
            ));

            // Nhân viên mẫu
            userList.add(new User(
                    "staff",
                    "5678",
                    "Nhân viên",
                    User.Status.ACTIVE,
                    "Nhân viên mẫu",
                    "0911111111",
                    "staff@banhmiviet.com",
                    25,
                    "Nữ"
            ));
        }
    }

    public static List<User> getAllUsers() {
        return userList;
    }

    public static void addUser(User user) {
        userList.add(user);
    }

    public static User findByUsername(String username) {
        for (User u : userList) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    public static List<User> getPendingUsers() {
        List<User> pending = new ArrayList<>();
        for (User u : userList) {
            if (u.isPending()) {
                pending.add(u);
            }
        }
        return pending;
    }

    public static void updateStatus(String username, User.Status newStatus) {
        User u = findByUsername(username);
        if (u != null) {
            u.setStatus(newStatus);
        }
    }
}
