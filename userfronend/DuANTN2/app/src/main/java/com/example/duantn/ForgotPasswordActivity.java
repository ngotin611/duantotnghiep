package com.example.duantn;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duantn.helper.EmailService;
import com.example.duantn.helper.OTPManager;
import com.example.duantn.helper.UserManager;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button btnSendOTP;
    private TextView tvBackToLogin;
    private OTPManager otpManager;
    private UserManager userManager;
    private EmailService emailService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        otpManager = new OTPManager(this);
        userManager = new UserManager(this);
        emailService = new EmailService(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        btnSendOTP.setOnClickListener(v -> handleSendOTP());
        tvBackToLogin.setOnClickListener(v -> finish());
    }

    private void handleSendOTP() {
        String email = editTextEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra định dạng email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra email có tồn tại trong hệ thống không
        if (!userManager.emailExists(email)) {
            Toast.makeText(this, "Email này chưa được đăng ký", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo và gửi mã OTP
        String otp = otpManager.generateOTP();
        otpManager.saveOTP(email, otp);
        
        // Hiển thị mã OTP trong Toast để test (tạm thời)
        Toast.makeText(this, 
            "Đang gửi email...\nMã OTP (để test): " + otp + 
            "\n\nKiểm tra Logcat để xem chi tiết", 
            Toast.LENGTH_LONG).show();
        
        android.util.Log.d("ForgotPassword", "Mã OTP đã tạo: " + otp);
        android.util.Log.d("ForgotPassword", "Email nhận: " + email);
        
        emailService.sendOTPEmail(email, otp);

        // Đợi 2 giây rồi chuyển màn hình (để email có thời gian gửi)
        new android.os.Handler().postDelayed(() -> {
            // Chuyển đến màn hình xác nhận OTP
            Intent intent = new Intent(ForgotPasswordActivity.this, VerifyOTPActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
