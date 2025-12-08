package com.example.duantn;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duantn.helper.OTPManager;

public class VerifyOTPActivity extends AppCompatActivity {

    private EditText editTextOTP;
    private Button btnVerifyOTP;
    private TextView tvResendOTP, tvBackToLogin;
    private String email;
    private OTPManager otpManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        otpManager = new OTPManager(this);
        email = getIntent().getStringExtra("email");

        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Lỗi: Không nhận được email", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        editTextOTP = findViewById(R.id.editTextOTP);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        tvResendOTP = findViewById(R.id.tvResendOTP);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        // Hiển thị email
        TextView tvEmail = findViewById(R.id.tvEmail);
        if (tvEmail != null) {
            tvEmail.setText("Email: " + email);
        }

        btnVerifyOTP.setOnClickListener(v -> handleVerifyOTP());
        tvResendOTP.setOnClickListener(v -> handleResendOTP());
        tvBackToLogin.setOnClickListener(v -> {
            startActivity(new Intent(VerifyOTPActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void handleVerifyOTP() {
        String otp = editTextOTP.getText().toString().trim();

        if (TextUtils.isEmpty(otp)) {
            Toast.makeText(this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        if (otp.length() != 6) {
            Toast.makeText(this, "Mã OTP phải có 6 chữ số", Toast.LENGTH_SHORT).show();
            return;
        }

        // Xác thực mã OTP
        if (otpManager.verifyOTP(email, otp)) {
            Toast.makeText(this, "Xác thực thành công", Toast.LENGTH_SHORT).show();
            
            // Chuyển đến màn hình đặt mật khẩu mới
            Intent intent = new Intent(VerifyOTPActivity.this, ResetPasswordActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Mã OTP không đúng hoặc đã hết hạn", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleResendOTP() {
        // Tạo mã OTP mới
        String newOTP = otpManager.generateOTP();
        otpManager.saveOTP(email, newOTP);
        
        // Gửi lại email
        com.example.duantn.helper.EmailService emailService = new com.example.duantn.helper.EmailService(this);
        emailService.sendOTPEmail(email, newOTP);
        
        Toast.makeText(this, "Mã OTP mới đã được gửi", Toast.LENGTH_SHORT).show();
    }
}



