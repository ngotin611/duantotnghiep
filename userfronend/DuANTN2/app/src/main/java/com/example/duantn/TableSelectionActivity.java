package com.example.duantn;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duantn.helper.TableManager;

public class TableSelectionActivity extends AppCompatActivity {

    private RadioGroup orderTypeGroup;
    private RadioButton rbTable, rbTakeAway;
    private EditText edtTableNumber;
    private Button btnConfirm;
    private TableManager tableManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_selection);

        tableManager = new TableManager(this);

        // Kiểm tra nếu đã chọn bàn rồi thì chuyển thẳng vào MainActivity
        if (tableManager.isTableSelected()) {
            navigateToMain();
            return;
        }

        initViews();
        setupListeners();
    }

    private void initViews() {
        orderTypeGroup = findViewById(R.id.orderTypeGroup);
        rbTable = findViewById(R.id.rbTable);
        rbTakeAway = findViewById(R.id.rbTakeAway);
        edtTableNumber = findViewById(R.id.edtTableNumber);
        btnConfirm = findViewById(R.id.btnConfirm);
    }

    private void setupListeners() {
        // Ẩn/hiện ô nhập số bàn
        orderTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbTable) {
                edtTableNumber.setVisibility(View.VISIBLE);
            } else {
                edtTableNumber.setVisibility(View.GONE);
            }
        });

        btnConfirm.setOnClickListener(v -> handleConfirm());
    }

    private void handleConfirm() {
        String orderType = rbTable.isChecked() ? "Tại quán" : "Mang đi";
        String tableNumber = rbTable.isChecked() ? edtTableNumber.getText().toString().trim() : "Mang đi";

        // Validate
        if (rbTable.isChecked() && TextUtils.isEmpty(tableNumber)) {
            Toast.makeText(this, "⚠️ Vui lòng nhập số bàn!", Toast.LENGTH_SHORT).show();
            edtTableNumber.requestFocus();
            return;
        }

        // Kiểm tra số bàn hợp lệ (1-50)
        if (rbTable.isChecked()) {
            try {
                int tableNum = Integer.parseInt(tableNumber);
                if (tableNum < 1 || tableNum > 50) {
                    Toast.makeText(this, "❌ Số bàn không có! Quán chỉ có bàn từ 1 đến 50", Toast.LENGTH_SHORT).show();
                    edtTableNumber.requestFocus();
                    edtTableNumber.selectAll();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "❌ Số bàn không hợp lệ! Vui lòng nhập số từ 1 đến 50", Toast.LENGTH_SHORT).show();
                edtTableNumber.requestFocus();
                edtTableNumber.selectAll();
                return;
            }
        }

        // Lưu thông tin bàn
        tableManager.saveTableInfo(tableNumber, orderType);

        // Thông báo thành công
        String successMessage = rbTable.isChecked() 
            ? "✅ Đã chọn bàn số " + tableNumber 
            : "✅ Đã chọn đơn mang đi";
        Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show();

        // Chuyển sang MainActivity sau 500ms để người dùng thấy thông báo
        new android.os.Handler().postDelayed(() -> {
            navigateToMain();
        }, 500);
    }

    private void navigateToMain() {
        Intent intent = new Intent(TableSelectionActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}



