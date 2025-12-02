package com.example.duantn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class OrderSuccessActivity extends AppCompatActivity {

    private static final String TAG = "OrderSuccessActivity";
    private TextView orderAmountTxt, orderNumberTxt, paymentMethodTxt;
    private Button backToHomeBtn, viewOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Log.d(TAG, "onCreate started");
            setContentView(R.layout.activity_order_success);
            Log.d(TAG, "Layout set successfully");

            // Lấy dữ liệu từ Intent
            double orderAmount = getIntent().getDoubleExtra("order_amount", 0);
            String paymentMethod = getIntent().getStringExtra("payment_method");

            // Khởi tạo views
            initViews();

            // Hiển thị dữ liệu
            displayOrderInfo(orderAmount, paymentMethod);

            // Setup listeners
            setupListeners();

            Log.d(TAG, "onCreate completed successfully");

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage(), e);
            showErrorDialog();
        }
    }

    private void initViews() {
        orderAmountTxt = findViewById(R.id.orderAmountTxt);
        orderNumberTxt = findViewById(R.id.orderNumberTxt);
        paymentMethodTxt = findViewById(R.id.paymentMethodTxt);
        backToHomeBtn = findViewById(R.id.backToHomeBtn);
        viewOrderBtn = findViewById(R.id.viewOrderBtn);
    }

    private void displayOrderInfo(double orderAmount, String paymentMethod) {
        try {
            DecimalFormat formatter = new DecimalFormat("#,###");
            orderAmountTxt.setText(formatter.format(orderAmount) + "₫");

            String orderNumber = "DH" + System.currentTimeMillis() % 100000;
            orderNumberTxt.setText(orderNumber);

            paymentMethodTxt.setText(paymentMethod != null ? paymentMethod : "Tiền mặt");

        } catch (Exception e) {
            Log.e(TAG, "Error displaying order info: " + e.getMessage());
        }
    }

    private void setupListeners() {
        backToHomeBtn.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(OrderSuccessActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Log.e(TAG, "Error navigating to MainActivity: " + e.getMessage());
                Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });

        viewOrderBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
        });
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Lỗi")
                .setMessage("Có lỗi xảy ra khi tải màn hình. Vui lòng thử lại.")
                .setPositiveButton("OK", (dialog, which) -> {
                    finish();
                })
                .setCancelable(false)
                .show();
    }
}