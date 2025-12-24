package com.example.duantn.models.page;

import java.util.List;

public class PageResponse <T>{
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int number; // số trang hiện tạihư
    private int size;   // số phần tử mỗi trang

    // Getter và Setter
    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

}
