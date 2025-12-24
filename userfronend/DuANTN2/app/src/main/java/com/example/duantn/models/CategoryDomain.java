package com.example.duantn.models;

public class CategoryDomain {
    private Long id;
    private String title;
    private String pic;
    private String name; // Tên category từ backend (nếu backend dùng "name" thay vì "title")

    // Constructor không tham số (cần cho Gson)
    public CategoryDomain() {}

    // Constructor với title và pic (giữ lại để tương thích)
    public CategoryDomain(String title, String pic) {
        this.title = title;
        this.pic = pic;
    }

    // Constructor đầy đủ
    public CategoryDomain(Long id, String title, String pic) {
        this.id = id;
        this.title = title;
        this.pic = pic;
    }

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title != null ? title : name; // Nếu title null thì dùng name
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
