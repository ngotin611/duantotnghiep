package com.example.duantn;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.adapter.PaymentHistoryAdapter;
import com.example.duantn.helper.OrderController;
import com.example.duantn.models.OrderDomain;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView btnBack;
    private TextView totalRevenueTxt, emptyText;
    private View emptyLayout;
    private OrderController orderController;
    private PaymentHistoryAdapter adapter;
    private ArrayList<OrderDomain> paidOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        orderController = new OrderController(this);

        initViews();
        setupListeners();
        loadPaymentHistory();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_payment_history);
        btnBack = findViewById(R.id.btn_back);
        totalRevenueTxt = findViewById(R.id.total_revenue_txt);
        emptyText = findViewById(R.id.empty_text);
        emptyLayout = findViewById(R.id.empty_layout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadPaymentHistory() {
        // Lấy tất cả đơn hàng đã thanh toán
        paidOrders = orderController.getAllPaidOrders();

        if (paidOrders.isEmpty()) {
            showEmptyState();
        } else {
            hideEmptyState();
            displayOrders();
            calculateTotalRevenue();
        }
    }

    private void displayOrders() {
        adapter = new PaymentHistoryAdapter(paidOrders);
        recyclerView.setAdapter(adapter);
    }

    private void calculateTotalRevenue() {
        double total = 0;
        for (OrderDomain order : paidOrders) {
            total += order.getPrice();
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        totalRevenueTxt.setText("Tổng doanh thu: " + formatter.format(total) + "₫");
    }

    private void showEmptyState() {
        emptyLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        totalRevenueTxt.setVisibility(View.GONE);
    }

    private void hideEmptyState() {
        emptyLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        totalRevenueTxt.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPaymentHistory();
    }
}



