package com.example.banhmiviet.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banhmiviet.MainActivity;
import com.example.banhmiviet.R;
import com.example.banhmiviet.model.User;
import com.example.banhmiviet.model.UserRepository;
import com.example.banhmiviet.util.SharedPrefUtil;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Spinner spinnerRole;
    private EditText edtUsername, edtPassword;
    private CheckBox chkShowPassword;
    private Button btnLogin, btnRegister;

    private List<User> userList; // Lấy từ UserRepository

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // View
        spinnerRole    = findViewById(R.id.spinnerRole);
        edtUsername    = findViewById(R.id.edtUsername);
        edtPassword    = findViewById(R.id.edtPassword);
        chkShowPassword= findViewById(R.id.chkShowPassword);
        btnLogin       = findViewById(R.id.btnLogin);
        btnRegister    = findViewById(R.id.btnRegister);

        // Setup spinner
        String[] roles = {"Admin", "Nhân viên"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        // Khởi tạo dữ liệu mẫu
        UserRepository.initDefaultUsers();
        userList = UserRepository.getAllUsers();

        // Xử lý hiện/ẩn mật khẩu
        chkShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtPassword.setSelection(edtPassword.getText().length());
        });

        // Nút Đăng nhập
        btnLogin.setOnClickListener(v -> handleLogin());

        // Nút Đăng ký tài khoản nhân viên
        btnRegister.setOnClickListener(v -> {
            Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(it);
        });
    }

    private void handleLogin() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String selectedRole = (spinnerRole.getSelectedItem() != null)
                ? spinnerRole.getSelectedItem().toString()
                : "";

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập username và password", Toast.LENGTH_SHORT).show();
            return;
        }

        User matchedUser = null;

        for (User user : userList) {
            if (user.isCredentialMatch(username, password, selectedRole)) {
                matchedUser = user;
                break;
            }
        }

        if (matchedUser == null) {
            Toast.makeText(this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra trạng thái duyệt
        if (matchedUser.isPending()) {
            Toast.makeText(this,
                    "Tài khoản đang chờ Admin xét duyệt. Vui lòng thử lại sau.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (matchedUser.isRejected()) {
            Toast.makeText(this,
                    "Tài khoản đã bị Admin từ chối. Vui lòng liên hệ quản trị.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (matchedUser.isActive()) {
            // Lưu token (hiện tại là token giả)
            String fakeToken = "localtoken_" + username + "_" + System.currentTimeMillis();
            SharedPrefUtil sp = new SharedPrefUtil(LoginActivity.this);
            sp.saveToken(fakeToken);

            Toast.makeText(this,
                    "Đăng nhập thành công với vai trò: " + matchedUser.getRole(),
                    Toast.LENGTH_SHORT).show();

            // 🔹 Phân luồng: Admin -> MainActivity, Nhân viên -> EmployeeMainActivity
            Intent it;
            if (matchedUser.getRole().equalsIgnoreCase("Admin")) {
                it = new Intent(LoginActivity.this, MainActivity.class);
            } else {
                it = new Intent(LoginActivity.this, EmployeeMainActivity.class);
            }

            startActivity(it);
            finish();
        } else {
            Toast.makeText(this, "Tài khoản không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}
