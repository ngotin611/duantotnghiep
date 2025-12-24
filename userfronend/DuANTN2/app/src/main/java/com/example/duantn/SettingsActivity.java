package com.example.duantn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.widget.LinearLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private ImageView btnBack;
    private Switch notificationSwitch, soundSwitch, twoFactorSwitch;
    private LinearLayout languageSetting, passwordSetting, twoFactorSetting;
    private LinearLayout paymentMethods, walletSetting, paymentHistorySetting, addressSetting;
    private LinearLayout contactSupport, faqSetting, rateApp;
    private View btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        setupClickListeners();
        loadSettings();
        setupBottomNavigation();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        notificationSwitch = findViewById(R.id.notification_switch);
        soundSwitch = findViewById(R.id.sound_switch);
        twoFactorSwitch = findViewById(R.id.two_factor_switch);

        languageSetting = findViewById(R.id.language_setting);
        passwordSetting = findViewById(R.id.password_setting);
        twoFactorSetting = findViewById(R.id.two_factor_setting);
        paymentMethods = findViewById(R.id.payment_methods);
        walletSetting = findViewById(R.id.wallet_setting);
        paymentHistorySetting = findViewById(R.id.payment_history_setting);
        addressSetting = findViewById(R.id.address_setting);
        contactSupport = findViewById(R.id.contact_support);
        faqSetting = findViewById(R.id.faq_setting);
        rateApp = findViewById(R.id.rate_app);
        btnLogout = findViewById(R.id.btn_logout);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        languageSetting.setOnClickListener(v -> showLanguageDialog());
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSetting(isChecked);
            Toast.makeText(this, "Thông báo: " + (isChecked ? "Bật" : "Tắt"), Toast.LENGTH_SHORT).show();
        });
        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSoundSetting(isChecked);
            Toast.makeText(this, "Âm thanh: " + (isChecked ? "Bật" : "Tắt"), Toast.LENGTH_SHORT).show();
        });

        passwordSetting.setOnClickListener(v -> showChangePasswordDialog());
        twoFactorSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveTwoFactorSetting(isChecked);
            Toast.makeText(this, "Xác thực 2 yếu tố: " + (isChecked ? "Bật" : "Tắt"), Toast.LENGTH_SHORT).show();
        });

        paymentMethods.setOnClickListener(v -> showPaymentMethodsDialog());
        walletSetting.setOnClickListener(v -> showWalletDialog());
        paymentHistorySetting.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, PaymentHistoryActivity.class);
            startActivity(intent);
        });

        addressSetting.setOnClickListener(v -> showAddressDialog());

        contactSupport.setOnClickListener(v -> showContactSupportDialog());
        faqSetting.setOnClickListener(v -> showFAQDialog());
        rateApp.setOnClickListener(v -> showRateAppDialog());

        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void loadSettings() {
        // Load cài đặt từ SharedPreferences, mặc định nếu chưa có
        android.content.SharedPreferences prefs = getSharedPreferences("app_settings", MODE_PRIVATE);
        notificationSwitch.setChecked(prefs.getBoolean("notifications_enabled", true));
        soundSwitch.setChecked(prefs.getBoolean("sound_enabled", true));
        twoFactorSwitch.setChecked(prefs.getBoolean("two_factor_enabled", false));
    }

    private void showLanguageDialog() {
        Toast.makeText(this, "Chọn ngôn ngữ", Toast.LENGTH_SHORT).show();
    }

    private void showChangePasswordDialog() {
        Toast.makeText(this, "Đổi mật khẩu", Toast.LENGTH_SHORT).show();
    }

    private void showPaymentMethodsDialog() {
        Toast.makeText(this, "Phương thức thanh toán", Toast.LENGTH_SHORT).show();
    }

    private void showWalletDialog() {
        Toast.makeText(this, "Ví điện tử", Toast.LENGTH_SHORT).show();
    }

    private void showAddressDialog() {
        Toast.makeText(this, "Địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
    }

    private void showContactSupportDialog() {
        Toast.makeText(this, "Liên hệ hỗ trợ", Toast.LENGTH_SHORT).show();
    }

    private void showFAQDialog() {
        Toast.makeText(this, "Câu hỏi thường gặp", Toast.LENGTH_SHORT).show();
    }

    private void showRateAppDialog() {
        Toast.makeText(this, "Đánh giá ứng dụng", Toast.LENGTH_SHORT).show();
    }

    // ✅ THÊM METHOD MỚI: Hiển thị dialog đăng xuất
    private void showLogoutDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc muốn đăng xuất?")
                .setPositiveButton("Có", (dialog, which) -> {
                    // Xóa trạng thái đăng nhập
                    getSharedPreferences("app_prefs", MODE_PRIVATE)
                            .edit()
                            .putBoolean("is_logged_in", false)
                            .remove("user_email")
                            .apply();

                    Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng màn hình cài đặt
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void saveNotificationSetting(boolean enabled) {
        getSharedPreferences("app_settings", MODE_PRIVATE)
                .edit()
                .putBoolean("notifications_enabled", enabled)
                .apply();
    }

    private void saveSoundSetting(boolean enabled) {
        getSharedPreferences("app_settings", MODE_PRIVATE)
                .edit()
                .putBoolean("sound_enabled", enabled)
                .apply();
    }

    private void saveTwoFactorSetting(boolean enabled) {
        getSharedPreferences("app_settings", MODE_PRIVATE)
                .edit()
                .putBoolean("two_factor_enabled", enabled)
                .apply();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            // Đánh dấu mục Cài đặt là đang được chọn
            bottomNavigationView.setSelectedItemId(R.id.nav_settings);
            
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.nav_notifications) {
                    Intent intent = new Intent(SettingsActivity.this, NotificationsActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.nav_settings) {
                    // Đã ở màn cài đặt
                    return true;
                }

                return false;
            });
        }
    }
}