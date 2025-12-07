package com.example.banhmiviet.data;

import com.example.banhmiviet.model.RevenuePoint;

import java.util.ArrayList;
import java.util.List;

public class RevenueRepository {

    private static RevenueRepository instance;
    private final List<RevenuePoint> revenuePoints = new ArrayList<>();

    private RevenueRepository() { mockData(); }

    public static RevenueRepository getInstance() {
        if (instance == null) {
            instance = new RevenueRepository();
        }
        return instance;
    }

    private void mockData() {
        // giả sử D1..D6
        revenuePoints.add(new RevenuePoint("D1", 28000));
        revenuePoints.add(new RevenuePoint("D2", 41000));
        revenuePoints.add(new RevenuePoint("D3",  0));
        revenuePoints.add(new RevenuePoint("D4",  0));
        revenuePoints.add(new RevenuePoint("D5",  0));
        revenuePoints.add(new RevenuePoint("D6",  0));
    }

    public List<RevenuePoint> getRevenuePoints() {
        return new ArrayList<>(revenuePoints);
    }
}
