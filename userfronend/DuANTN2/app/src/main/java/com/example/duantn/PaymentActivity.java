package com.example.duantn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duantn.helper.ManagmentCart;
import com.example.duantn.helper.OrderController;
import com.example.duantn.helper.OrderMapper;
import com.example.duantn.helper.TableManager;
import com.example.duantn.interface_api.OrderApi;
import com.example.duantn.models.FoodDomain;
import com.example.duantn.models.OrderDomain;
import com.example.duantn.models.order.CreateOrderRequest;
import com.example.duantn.models.order.ResponseOrder;
import com.example.duantn.retrofit.RetrofitClient;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private OrderApi orderApi;
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
        orderApi = RetrofitClient.getRetrofitInstance().create(OrderApi.class);
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

        // Tạo đơn hàng và gửi lên backend
        createOrderOnBackend();
    }

    /**
     * Tạo đơn hàng trên backend
     */
    private void createOrderOnBackend() {
        ArrayList<FoodDomain> cartItems = managmentCart.getListCart();
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            confirmPaymentBtn.setEnabled(true);
            confirmPaymentBtn.setText("XÁC NHẬN THANH TOÁN");
            return;
        }

        // Lấy thông tin bàn
        String tableNumber = tableManager.getTableNumber();
        String orderType = tableManager.getOrderType();

        // Tạo OrderDomain tạm để dùng cho mapper
        OrderDomain tempOrder = new OrderDomain();
        tempOrder.setTableNumber(tableNumber);
        tempOrder.setOrderType(orderType);
        tempOrder.setPaymentMethod(selectedPaymentMethod);
        tempOrder.setNote(note != null ? note : "");

        // Chuyển đổi sang CreateOrderRequest
        CreateOrderRequest orderRequest = OrderMapper.toCreateOrderRequest(tempOrder, cartItems);

        // Gửi request lên backend
        Call<ResponseOrder> call = orderApi.createOrder(orderRequest);
        call.enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseOrder responseOrder = response.body();
                    
                    // Hiển thị thông báo thanh toán thành công
                    if (selectedPaymentMethod.equals("Tiền mặt")) {
                        Toast.makeText(PaymentActivity.this, "Thanh toán tiền mặt thành công!", Toast.LENGTH_SHORT).show();
                    } else if (selectedPaymentMethod.equals("Thẻ tín dụng/ghi nợ")) {
                        Toast.makeText(PaymentActivity.this, "Thanh toán thẻ thành công!", Toast.LENGTH_SHORT).show();
                    } else if (selectedPaymentMethod.equals("Ví MoMo")) {
                        Toast.makeText(PaymentActivity.this, "Vui lòng quét mã QR MoMo để hoàn tất!", Toast.LENGTH_SHORT).show();
                    }
                    
                    // Chuyển đổi ResponseOrder sang OrderDomain và lưu local
                    OrderDomain savedOrder = OrderMapper.toOrderDomain(responseOrder);
                    orderController.addOrder(savedOrder);
                    
                    // Xóa giỏ hàng và thông tin bàn
                    managmentCart.clearCart();
                    tableManager.clearTableInfo();
                    
                    // Chuyển đến màn hình thành công
                    Intent intent = new Intent(PaymentActivity.this, OrderSuccessActivity.class);
                    intent.putExtra("order_amount", totalAmount);
                    intent.putExtra("payment_method", selectedPaymentMethod);
                    intent.putExtra("order_id", responseOrder.getOrderId());
                    startActivity(intent);
                    finish();
                } else {
                    // API trả về lỗi
                    Log.e("PaymentActivity", "Failed to create order: " + response.code());
                    Toast.makeText(PaymentActivity.this, "Không thể tạo đơn hàng. Mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                    
                    // Fallback: lưu local nếu backend thất bại
                    savePaidOrderLocal();
                    
                    confirmPaymentBtn.setEnabled(true);
                    confirmPaymentBtn.setText("XÁC NHẬN THANH TOÁN");
                }
            }

            @Override
            public void onFailure(Call<ResponseOrder> call, Throwable t) {
                Log.e("PaymentActivity", "Error creating order", t);
                Toast.makeText(PaymentActivity.this, "Lỗi kết nối: " + t.getMessage() + ". Đã lưu đơn hàng local.", Toast.LENGTH_LONG).show();
                
                // Fallback: lưu local nếu không kết nối được backend
                savePaidOrderLocal();
                
                confirmPaymentBtn.setEnabled(true);
                confirmPaymentBtn.setText("XÁC NHẬN THANH TOÁN");
            }
        });
    }

    /**
     * Lưu đơn hàng local (fallback khi không kết nối được backend)
     */
    private void savePaidOrderLocal() {
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
        
        // Xóa giỏ hàng và thông tin bàn
        managmentCart.clearCart();
        tableManager.clearTableInfo();
        
        // Chuyển đến màn hình thành công
        Intent intent = new Intent(PaymentActivity.this, OrderSuccessActivity.class);
        intent.putExtra("order_amount", totalAmount);
        intent.putExtra("payment_method", selectedPaymentMethod);
        startActivity(intent);
        finish();
    }

}