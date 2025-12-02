package com.example.duantn;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.duantn.R;
import com.example.duantn.adapter.OrderAdapter;
import com.example.duantn.models.OrderDomain;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity implements OrderAdapter.OnOrderActionListener {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ImageView btnBack;
    private TextView btnMarkAllRead;
    private LinearLayout emptyOrdersLayout;
    private TextView emptyOrdersText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        initViews();
        setupTabs();
        setupListeners();
        loadOrders();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        btnBack = findViewById(R.id.btn_back);
        btnMarkAllRead = findViewById(R.id.btn_mark_all_read);
        emptyOrdersLayout = findViewById(R.id.empty_orders_layout);
        emptyOrdersText = findViewById(R.id.empty_orders_text);
    }

    private void setupTabs() {
        OrdersPagerAdapter pagerAdapter = new OrdersPagerAdapter();
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Đang xử lý");
                    break;
                case 1:
                    tab.setText("Đang giao");
                    break;
                case 2:
                    tab.setText("Đã giao");
                    break;
                case 3:
                    tab.setText("Đã hủy");
                    break;
            }
        }).attach();
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnMarkAllRead.setOnClickListener(v ->
                Toast.makeText(this, "Đã đánh dấu tất cả đã đọc", Toast.LENGTH_SHORT).show()
        );
    }

    private void loadOrders() {
        // Tạo dữ liệu mẫu để test
        ArrayList<OrderDomain> sampleOrders = new ArrayList<>();

        // Đơn hàng đang xử lý
        OrderDomain order1 = new OrderDomain();
        order1.setOrderId("DH001");
        order1.setFoodName("Bánh mì thịt nướng");
        order1.setQuantity(2);
        order1.setPrice(45000);
        order1.setStatus("Đang xử lý");
        order1.setOrderDate("2024-01-15");
        sampleOrders.add(order1);

        // Đơn hàng đang giao
        OrderDomain order2 = new OrderDomain();
        order2.setOrderId("DH002");
        order2.setFoodName("Bánh mì gà nướng");
        order2.setQuantity(1);
        order2.setPrice(35000);
        order2.setStatus("Đang giao");
        order2.setOrderDate("2024-01-14");
        sampleOrders.add(order2);

        // Đơn hàng đã giao
        OrderDomain order3 = new OrderDomain();
        order3.setOrderId("DH003");
        order3.setFoodName("Bánh mì bò nướng");
        order3.setQuantity(3);
        order3.setPrice(55000);
        order3.setStatus("Đã giao");
        order3.setOrderDate("2024-01-13");
        sampleOrders.add(order3);

        if (sampleOrders.isEmpty()) {
            showEmptyOrders();
        } else {
            hideEmptyOrders();
            // Cập nhật ViewPager với dữ liệu mẫu
            OrdersPagerAdapter pagerAdapter = new OrdersPagerAdapter(sampleOrders);
            viewPager.setAdapter(pagerAdapter);
        }
    }

    private void showEmptyOrders() {
        emptyOrdersLayout.setVisibility(View.VISIBLE);
        emptyOrdersText.setText("Bạn chưa có đơn hàng nào");
    }

    private void hideEmptyOrders() {
        emptyOrdersLayout.setVisibility(View.GONE);
    }

    // Implement OrderAdapter.OnOrderActionListener
    @Override
    public void onReorder(OrderDomain order) {
        Toast.makeText(this, "Đặt lại: " + order.getFoodName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelOrder(OrderDomain order) {
        Toast.makeText(this, "Hủy đơn: " + order.getOrderId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewDetails(OrderDomain order) {
        Toast.makeText(this, "Xem chi tiết: " + order.getOrderId(), Toast.LENGTH_SHORT).show();
    }

    private class OrdersPagerAdapter extends RecyclerView.Adapter<OrdersPagerAdapter.ViewHolder> {

        private ArrayList<OrderDomain> allOrders;

        public OrdersPagerAdapter() {
            this.allOrders = new ArrayList<>();
        }

        public OrdersPagerAdapter(ArrayList<OrderDomain> orders) {
            this.allOrders = orders;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.fragment_orders_tab, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            setupTabContent(holder.itemView, position);
        }

        @Override
        public int getItemCount() {
            return 4;
        }

        private void setupTabContent(View view, int position) {
            RecyclerView recyclerView = view.findViewById(R.id.recycler_orders);
            recyclerView.setLayoutManager(new LinearLayoutManager(OrdersActivity.this));

            ArrayList<OrderDomain> orders = createSampleOrders(position);

            if (orders.isEmpty()) {
                view.findViewById(R.id.empty_tab_layout).setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.empty_tab_layout).setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                OrderAdapter adapter = new OrderAdapter(orders, OrdersActivity.this, OrdersActivity.this);
                recyclerView.setAdapter(adapter);
            }
        }

        private ArrayList<OrderDomain> createSampleOrders(int tabPosition) {
            ArrayList<OrderDomain> orders = new ArrayList<>();

            // Lọc đơn hàng theo trạng thái
            for (OrderDomain order : allOrders) {
                switch (tabPosition) {
                    case 0: // Đang xử lý
                        if (order.isProcessing()) {
                            orders.add(order);
                        }
                        break;
                    case 1: // Đang giao
                        if (order.isDelivering()) {
                            orders.add(order);
                        }
                        break;
                    case 2: // Đã giao
                        if (order.isDelivered()) {
                            orders.add(order);
                        }
                        break;
                    case 3: // Đã hủy
                        if (order.isCancelled()) {
                            orders.add(order);
                        }
                        break;
                }
            }

            return orders;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}