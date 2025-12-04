package com.example.banhmiviet.model;

import java.util.List;

public class DashboardSummary {
    public long revenue;
    public int ordersCount;
    public int customersCount;
    public int productsCount;
    public List<LabelValue> revenueByMonth;

    public static class LabelValue {
        public String label;
        public long value;
    }
}
