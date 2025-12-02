package com.example.duantn;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class  ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button btnSendCode;
    private TextView tvBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        btnSendCode = findViewById(R.id.btnSendCode);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = editTextEmail.getText().toString().trim();

                if (TextUtils.isEmpty(inputEmail)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }

                String result = checkAccountInFile(inputEmail);
                if (result != null) {
                    new AlertDialog.Builder(ForgotPasswordActivity.this)
                            .setTitle("Tài khoản tìm thấy")
                            .setMessage("Mật khẩu của bạn là: " + result)
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Không tìm thấy tài khoản", Toast.LENGTH_LONG).show();
                }
            }
        });

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Quay lại LoginActivity
            }
        });
    }

    private String checkAccountInFile(String email) {
        try {
            FileInputStream fis = openFileInput("account.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;

            while ((line = reader.readLine()) != null) {
                // Format: tên,email,mật khẩu
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[1].equalsIgnoreCase(email)) {
                    return parts[2]; // mật khẩu
                }
            }

            reader.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
