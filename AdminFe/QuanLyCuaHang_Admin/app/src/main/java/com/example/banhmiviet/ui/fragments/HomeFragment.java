package com.example.banhmiviet.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.banhmiviet.R;
import com.example.banhmiviet.data.DataRepository;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.*;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private TextView tvRevenue, tvOrders, tvCustomers, tvProducts;
    private LineChart lineChart;
    private CardView cardRevenue, cardOrders, cardCustomers, cardProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvRevenue = view.findViewById(R.id.tvRevenue);
        tvOrders = view.findViewById(R.id.tvOrders);
        tvCustomers = view.findViewById(R.id.tvCustomers);
        tvProducts = view.findViewById(R.id.tvProducts);
        lineChart = view.findViewById(R.id.lineChart);

        cardRevenue = view.findViewById(R.id.cardRevenue);
        cardOrders = view.findViewById(R.id.cardOrders);
        cardCustomers = view.findViewById(R.id.cardCustomers);
        cardProducts = view.findViewById(R.id.cardProducts);

        // load và hiển thị dữ liệu
        refreshDashboard();

        // click handlers -> gọi Activity để chuyển fragment
        cardOrders.setOnClickListener(v -> {
            if (getActivity() instanceof com.example.banhmiviet.MainActivity) {
                ((com.example.banhmiviet.MainActivity) getActivity()).openOrders();
            } else {
                // fallback: replace fragment trực tiếp
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new OrderFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        cardProducts.setOnClickListener(v -> {
            if (getActivity() instanceof com.example.banhmiviet.MainActivity) {
                ((com.example.banhmiviet.MainActivity) getActivity()).openProducts();
            } else {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ProductFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        cardCustomers.setOnClickListener(v -> {
            if (getActivity() instanceof com.example.banhmiviet.MainActivity) {
                ((com.example.banhmiviet.MainActivity) getActivity()).openCustomers();
            } else {
                // if you have CustomerFragment replace here
            }
        });

        cardRevenue.setOnClickListener(v -> {
            if (getActivity() instanceof com.example.banhmiviet.MainActivity) {
                ((com.example.banhmiviet.MainActivity) getActivity()).openStatistics();
            } else {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new StatisticsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void refreshDashboard() {
        DataRepository repo = DataRepository.getInstance();

        double revenue = repo.getTotalRevenue();
        int orders = repo.getOrdersCount();
        int customers = repo.getCustomersCount();
        int products = repo.getProductsCount();

        tvRevenue.setText(formatVND(revenue));
        tvOrders.setText(String.valueOf(orders));
        tvCustomers.setText(String.valueOf(customers));
        tvProducts.setText(String.valueOf(products));

        // build entries for chart using orders totals (demo)
        List<Entry> entries = new ArrayList<>();
        List<DataRepository.Order> orderList = repo.getOrders();
        for (int i = 0; i < orderList.size(); i++) {
            entries.add(new Entry(i, (float) orderList.get(i).getTotalAmount()));
        }
        // ensure at least 6 points (for display) — fill zeros if needed
        while (entries.size() < 6) entries.add(new Entry(entries.size(), 0f));

        setupLineChart(entries);
    }

    private String formatVND(double value) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        return nf.format((long) value) + " đ";
    }

    private void setupLineChart(List<Entry> entries) {
        if (lineChart == null) return;

        LineDataSet set = new LineDataSet(entries, "Doanh thu");
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawFilled(true);
        set.setFillAlpha(110);
        set.setColor(Color.parseColor("#FF8800"));
        set.setLineWidth(2f);
        set.setCircleColor(Color.parseColor("#FF8800"));
        set.setCircleRadius(4f);
        set.setDrawValues(false);

        LineData data = new LineData(set);
        lineChart.setData(data);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int idx = Math.round(value) + 1;
                return "D" + idx; // D1, D2... or T1..T6 as you want
            }
        });

        YAxis left = lineChart.getAxisLeft();
        final NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        left.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return vnFormat.format(Math.round(value));
            }
        });
        left.setAxisMinimum(0f);
        lineChart.getAxisRight().setEnabled(false);

        Description desc = new Description();
        desc.setText("");
        lineChart.setDescription(desc);
        lineChart.getLegend().setEnabled(false);

        lineChart.invalidate();
    }
}
