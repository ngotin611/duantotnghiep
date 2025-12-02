package com.example.duantn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.adapter.CartAdapter;
import com.example.duantn.helper.ManagmentCart;
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
    private View emptyCartLayout, cartContentLayout;

    // Bạn có thể chỉnh về 0 nếu là order tại quán, không giao hàng
    private static final double DELIVERY_FEE = 0; // trước là 15000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managmentCart = new ManagmentCart(this);

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

            // ❌ BỎ kiểm tra đăng nhập
            // ✅ Nhân viên đã đăng nhập từ đầu app, ở đây chỉ tạo đơn / thanh toán

            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            intent.putExtra("total_amount", managmentCart.getTotalFee() + DELIVERY_FEE);
            startActivity(intent);
        });
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