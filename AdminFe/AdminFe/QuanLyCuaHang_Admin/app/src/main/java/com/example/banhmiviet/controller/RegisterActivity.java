package com.example.banhmiviet.controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.User;
import com.example.banhmiviet.model.UserRepository;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtRegName, edtRegPhone, edtRegEmail, edtRegAge;
    private EditText edtRegUsername, edtRegPassword;
    private RadioGroup radioRegGender;
    private Button btnSubmitRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRegName     = findViewById(R.id.edtRegName);
        edtRegPhone    = findViewById(R.id.edtRegPhone);
        edtRegEmail    = findViewById(R.id.edtRegEmail);
        edtRegAge      = findViewById(R.id.edtRegAge);
        radioRegGender = findViewById(R.id.radioRegGender);
        edtRegUsername = findViewById(R.id.edtRegUsername);
        edtRegPassword = findViewById(R.id.edtRegPassword);
        btnSubmitRegister = findViewById(R.id.btnSubmitRegister);

        btnSubmitRegister.setOnClickListener(v -> handleRegister());
    }

    private void handleRegister() {
        String name     = edtRegName.getText().toString().trim();
        String phone    = edtRegPhone.getText().toString().trim();
        String email    = edtRegEmail.getText().toString().trim();
        String ageStr   = edtRegAge.getText().toString().trim();
        String username = edtRegUsername.getText().toString().trim();
        String password = edtRegPassword.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || ageStr.isEmpty()
                || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Tuổi không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        int genderId = radioRegGender.getCheckedRadioButtonId();
        String gender = "Khác";
        if (genderId != -1) {
            RadioButton rb = findViewById(genderId);
            gender = rb.getText().toString();
        }

        if (UserRepository.findByUsername(username) != null) {
            Toast.makeText(this, "Username đã tồn tại, vui lòng chọn tên khác", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(
                username,
                password,
                "Nhân viên",
                User.Status.PENDING,
                name,
                phone,
                email,
                age,
                gender
        );

        UserRepository.addUser(newUser);

        Toast.makeText(this,
                "Đăng ký thành công! Tài khoản đang chờ Admin xét duyệt.",
                Toast.LENGTH_LONG).show();

        finish();
    }
}
