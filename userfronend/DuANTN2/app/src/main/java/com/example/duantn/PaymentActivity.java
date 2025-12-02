package com.example.duantn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duantn.helper.ManagmentCart;
import java.text.DecimalFormat;

public class PaymentActivity extends AppCompatActivity {

    private TextView totalAmountTxt, deliveryAddressTxt;
    private RadioGroup paymentMethodGroup;
    private RadioButton cashPayment, cardPayment, momoPayment;
    private Button confirmPaymentBtn;
    private LinearLayout cardInfoLayout, momoQrLayout;
    private EditText cardNumberEdt, cardNameEdt, cardExpiryEdt, cardCvvEdt;
    private ImageView momoQrImg;
    private ManagmentCart managmentCart;
    private double totalAmount;
    private String selectedPaymentMethod = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        managmentCart = new ManagmentCart(this);
        totalAmount = getIntent().getDoubleExtra("total_amount", 0);

        initView();
        setupListeners();
        displayOrderSummary();
    }

    private void initView() {
        totalAmountTxt = findViewById(R.id.totalAmountTxt);
        deliveryAddressTxt = findViewById(R.id.deliveryAddressTxt);
        paymentMethodGroup = findViewById(R.id.paymentMethodGroup);
        cashPayment = findViewById(R.id.cashPayment);
        cardPayment = findViewById(R.id.cardPayment);
        momoPayment = findViewById(R.id.momoPayment);
        confirmPaymentBtn = findViewById(R.id.confirmPaymentBtn);

        cardInfoLayout = findViewById(R.id.cardInfoLayout);
        momoQrLayout = findViewById(R.id.momoQrLayout);
        cardNumberEdt = findViewById(R.id.cardNumberEdt);
        cardNameEdt = findViewById(R.id.cardNameEdt);
        cardExpiryEdt = findViewById(R.id.cardExpiryEdt);
        cardCvvEdt = findViewById(R.id.cardCvvEdt);
        momoQrImg = findViewById(R.id.momoQrImg);
    }

    private void setupListeners() {
        paymentMethodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.cashPayment) {
                selectedPaymentMethod = "Tiền mặt";
                cardInfoLayout.setVisibility(View.GONE);
                momoQrLayout.setVisibility(View.GONE);
            } else if (checkedId == R.id.cardPayment) {
                selectedPaymentMethod = "Thẻ tín dụng/ghi nợ";
                cardInfoLayout.setVisibility(View.VISIBLE);
                momoQrLayout.setVisibility(View.GONE);
            } else if (checkedId == R.id.momoPayment) {
                selectedPaymentMethod = "Ví MoMo";
                cardInfoLayout.setVisibility(View.GONE);
                momoQrLayout.setVisibility(View.VISIBLE);
            }
            updateConfirmButton();
        });

        confirmPaymentBtn.setOnClickListener(v -> {
            if (selectedPaymentMethod.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedPaymentMethod.equals("Thẻ tín dụng/ghi nợ")) {
                String cardNumber = cardNumberEdt.getText().toString().trim();
                String cardName = cardNameEdt.getText().toString().trim();
                String cardExpiry = cardExpiryEdt.getText().toString().trim();
                String cardCvv = cardCvvEdt.getText().toString().trim();
                if (cardNumber.length() < 12 || cardName.isEmpty() || cardExpiry.isEmpty() || cardCvv.length() < 3) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin thẻ!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            showConfirmationDialog();
        });
    }

    private void displayOrderSummary() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        totalAmountTxt.setText(formatter.format(totalAmount) + "₫");
        deliveryAddressTxt.setText("123 Đường ABC, Quận 1, TP.HCM");
    }

    private void updateConfirmButton() {
        confirmPaymentBtn.setEnabled(!selectedPaymentMethod.isEmpty());
    }

    private void showConfirmationDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Xác nhận thanh toán");
        builder.setMessage("Bạn có chắc chắn muốn thanh toán " +
                new DecimalFormat("#,###").format(totalAmount) + "₫" +
                " bằng " + selectedPaymentMethod + "?");

        builder.setPositiveButton("Xác nhận", (dialog, which) -> processPayment());
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void processPayment() {
        confirmPaymentBtn.setEnabled(false);
        confirmPaymentBtn.setText("ĐANG XỬ LÝ...");

        new android.os.Handler().postDelayed(() -> {
            if (selectedPaymentMethod.equals("Tiền mặt")) {
                Toast.makeText(this, "Thanh toán tiền mặt thành công (demo)!", Toast.LENGTH_SHORT).show();
            } else if (selectedPaymentMethod.equals("Thẻ tín dụng/ghi nợ")) {
                Toast.makeText(this, "Thanh toán thẻ thành công (demo)!", Toast.LENGTH_SHORT).show();
            } else if (selectedPaymentMethod.equals("Ví MoMo")) {
                Toast.makeText(this, "Vui lòng quét mã QR MoMo để hoàn tất (demo)!", Toast.LENGTH_SHORT).show();
            }
            managmentCart.clearCart();
            Intent intent = new Intent(PaymentActivity.this, OrderSuccessActivity.class);
            intent.putExtra("order_amount", totalAmount);
            intent.putExtra("payment_method", selectedPaymentMethod);
            startActivity(intent);
            finish();
        }, 1500);
    }
}