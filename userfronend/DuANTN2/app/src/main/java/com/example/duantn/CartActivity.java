package com.example.duantn;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.adapter.CartAdapter;
import com.example.duantn.helper.ManagmentCart;
import com.example.duantn.helper.TableManager;
import com.example.duantn.models.FoodDomain;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private TextView subtotalTxt, deliveryFeeTxt, totalAmountTxt;
    private TextView emptyCartTxt, itemCountTxt;
    private Button checkoutBtn, shopNowBtn;
    private ImageView backBtn;
    private CartAdapter adapter;
    private ManagmentCart managmentCart;
    private TableManager tableManager;
    private View emptyCartLayout, cartContentLayout;

    // Bạn có thể chỉnh về 0 nếu là order tại quán, không giao hàng
    private static final double DELIVERY_FEE = 0; // trước là 15000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managmentCart = new ManagmentCart(this);
        tableManager = new TableManager(this);

        initView();
        initList();
        calculateTotal();
        setupListeners();
    }

    private void initView() {
        recyclerViewCart = findViewById(R.id.cartRecyclerView);
        subtotalTxt = findViewById(R.id.subtotalTxt);
        deliveryFeeTxt = findViewById(R.id.deliveryFeeTxt);
        totalAmountTxt = findViewById(R.id.totalAmountTxt);
        checkoutBtn = findViewById(R.id.checkoutBtn);
        backBtn = findViewById(R.id.backBtn);
        emptyCartTxt = findViewById(R.id.emptyCartTxt);
        itemCountTxt = findViewById(R.id.itemCountTxt);
        emptyCartLayout = findViewById(R.id.emptyCartLayout);
        cartContentLayout = findViewById(R.id.cartContentLayout);
        shopNowBtn = findViewById(R.id.shopNowBtn);
    }

    private void setupListeners() {
        backBtn.setOnClickListener(v -> finish());
        shopNowBtn.setOnClickListener(v -> finish());

        checkoutBtn.setOnClickListener(v -> {
            if (managmentCart.getListCart().isEmpty()) {
                Toast.makeText(CartActivity.this, "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            // Kiểm tra đã chọn bàn chưa
            if (!tableManager.isTableSelected()) {
                Toast.makeText(CartActivity.this, "Vui lòng chọn bàn trước!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CartActivity.this, TableSelectionActivity.class);
                startActivity(intent);
                return;
            }
            // Hiển thị dialog nhập ghi chú (đã có thông tin bàn)
            showNoteDialog();
        });
    }

    // Dialog nhập ghi chú (thông tin bàn đã có sẵn)
    private void showNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_order, null);
        builder.setView(dialogView);

        RadioGroup orderTypeGroup = dialogView.findViewById(R.id.orderTypeGroup);
        RadioButton rbTable = dialogView.findViewById(R.id.rbTable);
        RadioButton rbTakeAway = dialogView.findViewById(R.id.rbTakeAway);
        EditText edtTableNumber = dialogView.findViewById(R.id.edtTableNumber);
        EditText edtNote = dialogView.findViewById(R.id.edtNote);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirmOrder);
        Button btnCancel = dialogView.findViewById(R.id.btnCancelOrder);

        AlertDialog dialog = builder.create();

        // Lấy thông tin bàn đã chọn
        String savedOrderType = tableManager.getOrderType();
        String savedTableNumber = tableManager.getTableNumber();

        // Hiển thị thông tin bàn đã chọn (chỉ đọc, không cho sửa)
        if ("Tại quán".equals(savedOrderType)) {
            rbTable.setChecked(true);
            edtTableNumber.setVisibility(View.VISIBLE);
            edtTableNumber.setText(savedTableNumber);
            edtTableNumber.setEnabled(false); // Không cho sửa
        } else {
            rbTakeAway.setChecked(true);
            edtTableNumber.setVisibility(View.GONE);
        }

        // Vô hiệu hóa radio group (không cho đổi loại đơn)
        orderTypeGroup.setEnabled(false);
        rbTable.setEnabled(false);
        rbTakeAway.setEnabled(false);

        btnConfirm.setOnClickListener(v -> {
            String note = edtNote.getText().toString().trim();
            // Chuyển đến màn hình thanh toán (không tạo đơn hàng ở đây)
            // Đơn hàng sẽ được tạo sau khi thanh toán thành công
            double totalPrice = managmentCart.getTotalFee() + DELIVERY_FEE;
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            intent.putExtra("total_amount", totalPrice);
            intent.putExtra("note", note); // Lưu ghi chú để dùng khi thanh toán
            dialog.dismiss();
            startActivity(intent);
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


    private void initList() {
        ArrayList<FoodDomain> list = managmentCart.getListCart();

        if (list.isEmpty()) {
            emptyCartLayout.setVisibility(View.VISIBLE);
            cartContentLayout.setVisibility(View.GONE);
            return;
        }

        emptyCartLayout.setVisibility(View.GONE);
        cartContentLayout.setVisibility(View.VISIBLE);

        updateItemCount();

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CartAdapter(list, this, () -> {
            calculateTotal();
            updateItemCount();
            if (managmentCart.getListCart().isEmpty()) {
                initList();
            }
        });

        recyclerViewCart.setAdapter(adapter);
    }

    private void updateItemCount() {
        ArrayList<FoodDomain> list = managmentCart.getListCart();
        int totalItems = 0;

        for (FoodDomain item : list) {
            totalItems += item.getNumberInCart();
        }

        itemCountTxt.setText(String.format("%d món", totalItems));
    }

    private void calculateTotal() {
        double subtotal = managmentCart.getTotalFee();
        double total = subtotal + DELIVERY_FEE;

        DecimalFormat formatter = new DecimalFormat("#,###");

        subtotalTxt.setText(String.format("%s₫", formatter.format(subtotal)));
        deliveryFeeTxt.setText(String.format("%s₫", formatter.format(DELIVERY_FEE)));
        totalAmountTxt.setText(String.format("%s₫", formatter.format(total)));

        if (subtotal > 0) {
            checkoutBtn.setEnabled(true);
            // Đổi text cho giống nhân viên tạo đơn
            checkoutBtn.setText(String.format("TẠO ĐƠN (%s₫)", formatter.format(total)));
        } else {
            checkoutBtn.setEnabled(false);
            checkoutBtn.setText("TẠO ĐƠN");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
        calculateTotal();
    }
}