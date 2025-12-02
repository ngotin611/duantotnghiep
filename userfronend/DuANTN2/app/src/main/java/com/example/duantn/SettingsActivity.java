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

public class SettingsActivity extends AppCompatActivity {

    private ImageView btnBack;
    private Switch notificationSwitch, soundSwitch, twoFactorSwitch;
    private LinearLayout languageSetting, passwordSetting, twoFactorSetting;
    private LinearLayout paymentMethods, walletSetting, addressSetting;
    private LinearLayout contactSupport, faqSetting, rateApp;
    private View btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        setupClickListeners();
        loadSettings();
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

        addressSetting.setOnClickListener(v -> showAddressDialog());

        contactSupport.setOnClickListener(v -> showContactSupportDialog());
        faqSetting.setOnClickListener(v -> showFAQDialog());
        rateApp.setOnClickListener(v -> showRateAppDialog());

        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void loadSettings() {
        notificationSwitch.setChecked(true);
        soundSwitch.setChecked(true);
        twoFactorSwitch.setChecked(false);
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
        // TODO: Lưu cài đặt thông báo vào SharedPreferences
    }

    private void saveSoundSetting(boolean enabled) {
        // TODO: Lưu cài đặt âm thanh vào SharedPreferences
    }

    private void saveTwoFactorSetting(boolean enabled) {
        // TODO: Lưu cài đặt xác thực 2 yếu tố vào SharedPreferences
    }
}