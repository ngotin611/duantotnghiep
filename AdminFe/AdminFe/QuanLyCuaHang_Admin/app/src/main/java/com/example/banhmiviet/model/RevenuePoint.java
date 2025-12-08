package com.example.banhmiviet.model;

public class RevenuePoint {

    private String label; // D1, D2, D3 hoặc "Ngày 1", "Ngày 2"
    private double value;

    public RevenuePoint(String label, double value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
