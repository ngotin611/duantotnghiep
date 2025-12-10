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
import com.example.banhmiviet.model.User;
import com.example.banhmiviet.model.UserRepository;
import com.example.banhmiviet.ui.adapters.EmployeeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
                        // tuỳ bạn có muốn xoá luôn User tương ứng không, mình tạm thời không đụng
                    }
                },
                (position, emp) -> { // click item -> edit
                    openEmployeeDialog(emp, position);
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Thay vì mở dialog add trực tiếp -> mở menu chọn thao tác
        fabAdd.setOnClickListener(v -> showActionDialog());

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

                    // 🔹 Đồng bộ UserRepository để tài khoản này đăng nhập được
                    User u = UserRepository.findByUsername(username);
                    if (u == null) {
                        // chưa có user -> tạo mới ACTIVE
                        u = new User(
                                username,
                                password,
                                "Nhân viên",
                                User.Status.ACTIVE,
                                name,
                                phone,
                                email,
                                age,
                                gender
                        );
                        UserRepository.addUser(u);
                    } else {
                        // đã có (ví dụ từ đăng ký) -> cập nhật info
                        u.updateProfile(name, phone, email, age, gender);
                        u.updatePassword(password);
                        u.setStatus(User.Status.ACTIVE);
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // ====== PHẦN MỚI: menu chọn thao tác ======
    private void showActionDialog() {
        if (getContext() == null) return;

        String[] actions = {"Thêm nhân viên mới", "Duyệt yêu cầu đăng ký"};

        new AlertDialog.Builder(getContext())
                .setTitle("Chọn thao tác")
                .setItems(actions, (dialog, which) -> {
                    if (which == 0) {
                        // Thêm nhân viên như cũ
                        openEmployeeDialog(null, -1);
                    } else if (which == 1) {
                        // Mở màn xét duyệt
                        openPendingApprovalDialog();
                    }
                })
                .show();
    }

    // ====== PHẦN MỚI: dialog duyệt tài khoản đăng ký ======
    private void openPendingApprovalDialog() {
        if (getContext() == null) return;

        List<User> pendingUsers = UserRepository.getPendingUsers();

        if (pendingUsers.isEmpty()) {
            Toast.makeText(getContext(),
                    "Không có yêu cầu đăng ký nào đang chờ duyệt.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String[] items = new String[pendingUsers.size()];
        for (int i = 0; i < pendingUsers.size(); i++) {
            User u = pendingUsers.get(i);
            String displayName = (u.getName() != null && !u.getName().isEmpty())
                    ? u.getName()
                    : "(chưa có tên)";
            items[i] = u.getUsername() + " - " + displayName;
        }

        final int[] selectedIndex = {-1};

        new AlertDialog.Builder(getContext())
                .setTitle("Yêu cầu đăng ký")
                .setSingleChoiceItems(items, -1, (dialog, which) -> {
                    selectedIndex[0] = which;
                })
                .setPositiveButton("Duyệt", (dialog, which) -> {
                    if (selectedIndex[0] == -1) {
                        Toast.makeText(getContext(),
                                "Vui lòng chọn 1 tài khoản để duyệt.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    User u = pendingUsers.get(selectedIndex[0]);
                    UserRepository.updateStatus(u.getUsername(), User.Status.ACTIVE);

                    // 🔹 Tạo Employee mới từ thông tin User và thêm vào danh sách
                    String newId = "E" + String.format("%03d", employeeList.size() + 1);
                    String name  = u.getName();
                    String phone = u.getPhone();
                    String email = u.getEmail();
                    int age      = u.getAge();
                    String gender = (u.getGender() != null && !u.getGender().isEmpty())
                            ? u.getGender()
                            : "Khác";

                    Employee emp = new Employee(
                            newId,
                            name,
                            "Nhân viên",
                            phone,
                            email,
                            age,
                            gender,
                            u.getUsername(),
                            u.getPassword()
                    );

                    employeeList.add(emp);
                    adapter.notifyItemInserted(employeeList.size() - 1);
                    repo.addEmployee(emp);

                    Toast.makeText(getContext(),
                            "Đã duyệt và thêm nhân viên: " + name,
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Từ chối", (dialog, which) -> {
                    if (selectedIndex[0] == -1) {
                        Toast.makeText(getContext(),
                                "Vui lòng chọn 1 tài khoản để từ chối.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    User u = pendingUsers.get(selectedIndex[0]);
                    UserRepository.updateStatus(u.getUsername(), User.Status.REJECTED);

                    Toast.makeText(getContext(),
                            "Đã từ chối tài khoản: " + u.getUsername(),
                            Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("Đóng", null)
                .show();
    }
}
