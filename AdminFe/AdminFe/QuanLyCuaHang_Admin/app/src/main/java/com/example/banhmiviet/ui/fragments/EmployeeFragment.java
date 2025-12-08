package com.example.banhmiviet.ui.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.data.DataRepository;
import com.example.banhmiviet.model.Employee;
import com.example.banhmiviet.ui.adapters.EmployeeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EmployeeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private EmployeeAdapter adapter;
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private DataRepository repo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewEmployees);
        fabAdd       = view.findViewById(R.id.fabAddEmployee);

        repo = DataRepository.getInstance();
        employeeList.clear();
        employeeList.addAll(repo.getEmployees());

        adapter = new EmployeeAdapter(
                getContext(),
                employeeList,
                position -> { // delete
                    if (position >= 0 && position < employeeList.size()) {
                        employeeList.remove(position);
                        adapter.notifyItemRemoved(position);
                        repo.removeEmployee(position);
                    }
                },
                (position, emp) -> { // click item -> edit
                    openEmployeeDialog(emp, position);
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> openEmployeeDialog(null, -1));

        return view;
    }

    private void openEmployeeDialog(@Nullable Employee oldEmp, int editPosition) {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_employee, null);

        EditText edtName     = dialogView.findViewById(R.id.edtName);
        EditText edtEmail    = dialogView.findViewById(R.id.edtEmail);
        EditText edtPhone    = dialogView.findViewById(R.id.edtPhone);
        EditText edtAge      = dialogView.findViewById(R.id.edtAge);
        RadioGroup radioGender = dialogView.findViewById(R.id.radioGender);
        EditText edtUsername = dialogView.findViewById(R.id.edtUsername);
        EditText edtPassword = dialogView.findViewById(R.id.edtPassword);
        ImageView imgTogglePass = dialogView.findViewById(R.id.imgTogglePassword);

        // toggle show / hide password
        imgTogglePass.setOnClickListener(v -> {
            int currentType = edtPassword.getInputType();
            if (currentType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                // đang ẩn -> hiện
                edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                imgTogglePass.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            } else {
                // đang hiện -> ẩn lại
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgTogglePass.setImageResource(android.R.drawable.ic_menu_view);
            }
            // giữ con trỏ ở cuối
            edtPassword.setSelection(edtPassword.getText().length());
        });

        if (oldEmp != null) {
            edtName.setText(oldEmp.getName());
            edtEmail.setText(oldEmp.getEmail());
            edtPhone.setText(oldEmp.getPhone());
            edtAge.setText(String.valueOf(oldEmp.getAge()));
            edtUsername.setText(oldEmp.getUsername());
            edtPassword.setText(oldEmp.getPassword());

            if ("Nam".equalsIgnoreCase(oldEmp.getGender())) {
                radioGender.check(R.id.rbMale);
            } else if ("Nữ".equalsIgnoreCase(oldEmp.getGender())) {
                radioGender.check(R.id.rbFemale);
            }
        }

        new AlertDialog.Builder(getContext())
                .setTitle(editPosition == -1 ? "Thêm nhân viên" : "Sửa nhân viên")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String name     = edtName.getText().toString().trim();
                    String email    = edtEmail.getText().toString().trim();
                    String phone    = edtPhone.getText().toString().trim();
                    String ageStr   = edtAge.getText().toString().trim();
                    String username = edtUsername.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(name) ||
                            TextUtils.isEmpty(phone) ||
                            TextUtils.isEmpty(ageStr) ||
                            TextUtils.isEmpty(username) ||
                            TextUtils.isEmpty(password)) {
                        Toast.makeText(getContext(),
                                "Vui lòng nhập đầy đủ thông tin",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int age;
                    try {
                        age = Integer.parseInt(ageStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(),
                                "Tuổi không hợp lệ",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int genderId = radioGender.getCheckedRadioButtonId();
                    String gender = "Khác";
                    if (genderId != -1) {
                        RadioButton rb = dialogView.findViewById(genderId);
                        gender = rb.getText().toString();
                    }

                    String id;
                    String role;
                    if (oldEmp == null) {
                        id = "E" + String.format("%03d", employeeList.size() + 1);
                        role = "Nhân viên";
                    } else {
                        id = oldEmp.getId();
                        role = oldEmp.getRole();
                    }

                    Employee newEmp = new Employee(
                            id,
                            name,
                            role,
                            phone,
                            email,
                            age,
                            gender,
                            username,
                            password
                    );

                    if (editPosition == -1) {
                        employeeList.add(newEmp);
                        adapter.notifyItemInserted(employeeList.size() - 1);
                        repo.addEmployee(newEmp);
                    } else {
                        employeeList.set(editPosition, newEmp);
                        adapter.notifyItemChanged(editPosition);
                        repo.updateEmployee(editPosition, newEmp);
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
