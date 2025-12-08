package com.example.duantn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.adapter.NotificationAdapter;
import com.example.duantn.models.NotificationDomain;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity implements NotificationAdapter.OnNotificationClickListener {

    private RecyclerView recyclerNotifications;
    private ImageView btnBack;
    private TextView btnMarkAllRead;
    private LinearLayout emptyNotifications;
    private NotificationAdapter adapter;
    private ArrayList<NotificationDomain> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        initViews();
        initNotifications();
        setupRecyclerView();
        setupClickListeners();
        setupBottomNavigation();
    }

    private void initViews() {
        recyclerNotifications = findViewById(R.id.recycler_notifications);
        btnBack = findViewById(R.id.btn_back);
        btnMarkAllRead = findViewById(R.id.btn_mark_all_read);
        emptyNotifications = findViewById(R.id.empty_notifications);
    }

    private void initNotifications() {
        notifications = new ArrayList<>();

        // Thêm các thông báo mẫu
        notifications.add(new NotificationDomain(
                1,
                "Đơn hàng #12345 đã được xác nhận",
                "Đơn hàng của bạn đã được xác nhận và đang được chuẩn bị. Dự kiến giao hàng trong 30 phút.",
                "2 giờ trước",
                "order",
                false,
                "ic_notification_order"
        ));

        notifications.add(new NotificationDomain(
                2,
                "Khuyến mãi đặc biệt - Giảm 20%",
                "Chào mừng bạn đến với BanhMiViet! Hôm nay giảm 20% cho tất cả bánh mì. Mã: WELCOME20",
                "5 giờ trước",
                "promotion",
                false,
                "ic_notification_promotion"
        ));

        notifications.add(new NotificationDomain(
                3,
                "Đơn hàng #12344 đã giao thành công",
                "Đơn hàng của bạn đã được giao thành công. Cảm ơn bạn đã sử dụng dịch vụ của BanhMiViet!",
                "1 ngày trước",
                "order",
                true,
                "ic_notification_order"
        ));

        notifications.add(new NotificationDomain(
                4,
                "Cập nhật ứng dụng mới",
                "Phiên bản mới của BanhMiViet đã có sẵn với nhiều tính năng mới và cải thiện hiệu suất.",
                "2 ngày trước",
                "system",
                true,
                "ic_notification_system"
        ));

        notifications.add(new NotificationDomain(
                5,
                "Đơn hàng #12343 đang được giao",
                "Đơn hàng của bạn đang được giao bởi shipper. Dự kiến đến trong 15 phút.",
                "3 ngày trước",
                "order",
                true,
                "ic_notification_order"
        ));
    }

    private void setupRecyclerView() {
        adapter = new NotificationAdapter(notifications, this);
        adapter.setOnNotificationClickListener(this);

        recyclerNotifications.setLayoutManager(new LinearLayoutManager(this));
        recyclerNotifications.setAdapter(adapter);

        updateEmptyState();
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            finish(); // Quay về màn hình trước
        });

        btnMarkAllRead.setOnClickListener(v -> {
            markAllAsRead();
        });
    }

    private void markAllAsRead() {
        for (NotificationDomain notification : notifications) {
            notification.setIsRead(true);
        }
        adapter.notifyDataSetChanged();
        updateEmptyState();
        Toast.makeText(this, "Đã đánh dấu tất cả thông báo là đã đọc", Toast.LENGTH_SHORT).show();
    }

    private void updateEmptyState() {
        if (notifications.isEmpty()) {
            recyclerNotifications.setVisibility(View.GONE);
            emptyNotifications.setVisibility(View.VISIBLE);
        } else {
            recyclerNotifications.setVisibility(View.VISIBLE);
            emptyNotifications.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNotificationClick(NotificationDomain notification) {
        // Xử lý khi click vào thông báo
        if (!notification.isRead()) {
            notification.setIsRead(true);
            adapter.notifyDataSetChanged();
        }

        // Hiển thị thông báo chi tiết hoặc chuyển đến màn hình tương ứng
        Toast.makeText(this, "Đã mở: " + notification.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkAsRead(NotificationDomain notification) {
        notification.setIsRead(true);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Đã đánh dấu đã đọc", Toast.LENGTH_SHORT).show();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            // Đánh dấu mục Thông báo là đang được chọn
            bottomNavigationView.setSelectedItemId(R.id.nav_notifications);
            
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    Intent intent = new Intent(NotificationsActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.nav_notifications) {
                    // Đã ở màn thông báo
                    return true;
                } else if (itemId == R.id.nav_settings) {
                    Intent intent = new Intent(NotificationsActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                return false;
            });
        }
    }
}