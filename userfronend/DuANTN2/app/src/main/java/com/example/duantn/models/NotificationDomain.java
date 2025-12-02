package com.example.duantn.models;

public class NotificationDomain {
    private int id;
    private String title;
    private String message;
    private String time;
    private String type; // "order", "promotion", "system"
    private boolean isRead;
    private String icon;

    public NotificationDomain(int id, String title, String message, String time, String type, boolean isRead, String icon) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.time = time;
        this.type = type;
        this.isRead = isRead;
        this.icon = icon;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRead() { return isRead; }
    public void setIsRead(boolean read) { isRead = read; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}