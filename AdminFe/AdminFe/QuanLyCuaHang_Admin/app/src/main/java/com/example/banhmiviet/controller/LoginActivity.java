package com.example.banhmiviet.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banhmiviet.MainActivity;
import com.example.banhmiviet.R;
import com.example.banhmiviet.model.User;
import com.example.banhmiviet.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Spinner spinnerRole;
    private EditText edtUsername, edtPassword;
    private CheckBox chkShowPassword;
    private Button btnLogin;

    private List<User> userList; // List người dùng (Model)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // View
        spinnerRole = findViewById(R.id.spinnerRole);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        chkShowPassword = findViewById(R.id.chkShowPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Setup spinner
        String[] roles = {"Admin", "Nhân viên"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        // Setup dữ liệu model
        userList = new ArrayList<>();
        userList.add(new User("admin", "1234", "Admin"));
        userList.add(new User("staff", "5678", "Nhân viên"));

        // Xử lý hiện/ẩn mật khẩu
        chkShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Hiện mật khẩu
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                // Ẩn mật khẩu
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtPassword.setSelection(edtPassword.getText().length());
        });

        // Xử lý login (Controller)
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String selectedRole = (spinnerRole.getSelectedItem() != null) ? spinnerRole.getSelectedItem().toString() : "";

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập username và password", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean found = false;

            for (User user : userList) {
                if (user.checkLogin(username, password, selectedRole)) {
                    // Lưu token (hiện tại là token giả).
                    String fakeToken = "localtoken_" + username + "_" + System.currentTimeMillis();

                    SharedPrefUtil sp = new SharedPrefUtil(LoginActivity.this);
                    sp.saveToken(fakeToken);

                    Toast.makeText(this, "Đăng nhập thành công với vai trò: " + user.getRole(), Toast.LENGTH_SHORT).show();

                    // Chuyển tới MainActivity (thay vì DashboardActivity)
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();
                    found = true;
                    break;
                }
            }

            if (!found) {
                Toast.makeText(this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
