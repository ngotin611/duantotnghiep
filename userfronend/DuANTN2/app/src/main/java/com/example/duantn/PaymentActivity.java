package com.example.duantn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duantn.helper.ManagmentCart;
import com.example.duantn.helper.OrderController;
import com.example.duantn.helper.TableManager;
import com.example.duantn.models.FoodDomain;
import com.example.duantn.models.OrderDomain;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    private TextView totalAmountTxt, tableInfoTxt;
    private RadioGroup paymentMethodGroup;
    private RadioButton cashPayment, cardPayment, momoPayment;
    private Button confirmPaymentBtn;
    private LinearLayout cardInfoLayout, momoQrLayout;
    private EditText cardNumberEdt, cardNameEdt, cardExpiryEdt, cardCvvEdt;
    private ImageView momoQrImg, backBtn;
    private ManagmentCart managmentCart;
    private TableManager tableManager;
    private OrderController orderController;
    private double totalAmount;
    private String selectedPaymentMethod = "";
    private String note = ""; // Ghi chú từ CartActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        managmentCart = new ManagmentCart(this);
        tableManager = new TableManager(this);
        orderController = new OrderController(this);
        totalAmount = getIntent().getDoubleExtra("total_amount", 0);
        note = getIntent().getStringExtra("note"); // Lấy ghi chú nếu có

        initView();
        setupListeners();
        displayOrderSummary();
    }

    private void initView() {
        totalAmountTxt = findViewById(R.id.totalAmountTxt);
        tableInfoTxt = findViewById(R.id.tableInfoTxt);
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
        backBtn = findViewById(R.id.backBtn);
    }

    private void setupListeners() {
        backBtn.setOnClickListener(v -> finish());
        
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
        
        // Hiển thị thông tin bàn/loại đơn
        String orderType = tableManager.getOrderType();
        String tableNumber = tableManager.getTableNumber();
        
        if ("Tại quán".equals(orderType)) {
            tableInfoTxt.setText("Bàn: " + tableNumber);
        } else if ("Mang đi".equals(orderType)) {
            tableInfoTxt.setText("Loại đơn: Mang đi");
        } else {
            tableInfoTxt.setText("Chưa chọn bàn");
        }
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
            
            // Lưu đơn hàng đã thanh toán
            savePaidOrder();
            
            managmentCart.clearCart();
            tableManager.clearTableInfo(); // Xóa thông tin bàn sau khi thanh toán
            Intent intent = new Intent(PaymentActivity.this, OrderSuccessActivity.class);
            intent.putExtra("order_amount", totalAmount);
            intent.putExtra("payment_method", selectedPaymentMethod);
            startActivity(intent);
            finish();
        }, 1500);
    }

    private void savePaidOrder() {
        ArrayList<FoodDomain> cartItems = managmentCart.getListCart();
        if (cartItems.isEmpty()) {
            return;
        }

        // Tạo mã đơn tự động
        String orderId = orderController.generateOrderId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String orderDate = sdf.format(new Date());

        // Lấy thông tin bàn
        String tableNumber = tableManager.getTableNumber();
        String orderType = tableManager.getOrderType();

        // Tạo danh sách món ăn
        StringBuilder foodNames = new StringBuilder();
        int totalQuantity = 0;
        for (FoodDomain item : cartItems) {
            if (foodNames.length() > 0) foodNames.append(", ");
            foodNames.append(item.getTitle() + " x" + item.getNumberInCart());
            totalQuantity += item.getNumberInCart();
        }

        // Tạo đơn hàng đã thanh toán
        OrderDomain paidOrder = new OrderDomain();
        paidOrder.setOrderId(orderId);
        paidOrder.setFoodName(foodNames.toString());
        paidOrder.setQuantity(totalQuantity);
        paidOrder.setPrice((int) totalAmount);
        paidOrder.setStatus("Hoàn thành"); // Đã thanh toán
        paidOrder.setOrderDate(orderDate);
        paidOrder.setTableNumber(tableNumber);
        paidOrder.setOrderType(orderType);
        paidOrder.setPaymentMethod(selectedPaymentMethod);
        paidOrder.setNote(note != null ? note : ""); // Lưu ghi chú

        // Lưu đơn hàng
        orderController.addOrder(paidOrder);
    }
}