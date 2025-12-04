package com.example.banhmiviet.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Employee;
import com.example.banhmiviet.ui.adapters.EmployeeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EmployeeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private EmployeeAdapter adapter;
    private ArrayList<Employee> employeeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewEmployees);
        fabAdd = view.findViewById(R.id.fabAddEmployee);

        adapter = new EmployeeAdapter(getContext(), employeeList, position -> {
            employeeList.remove(position);
            adapter.notifyItemRemoved(position);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> openAddDialog());

        return view;
    }

    private void openAddDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_employee, null);
        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtEmail = dialogView.findViewById(R.id.edtEmail);
        EditText edtPhone = dialogView.findViewById(R.id.edtPhone);
        EditText edtAge = dialogView.findViewById(R.id.edtAge);
        RadioGroup radioGender = dialogView.findViewById(R.id.radioGender);
        EditText edtUsername = dialogView.findViewById(R.id.edtUsername);
        EditText edtPassword = dialogView.findViewById(R.id.edtPassword);

        new AlertDialog.Builder(getContext())
                .setTitle("Thêm nhân viên")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String name = edtName.getText().toString();
                    String email = edtEmail.getText().toString();
                    String phone = edtPhone.getText().toString();
                    int age = Integer.parseInt(edtAge.getText().toString());
                    int genderId = radioGender.getCheckedRadioButtonId();
                    String gender = ((RadioButton) dialogView.findViewById(genderId)).getText().toString();
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();

                    Employee newEmp = new Employee(name, email, phone, age, gender, username, password);
                    employeeList.add(newEmp);
                    adapter.notifyItemInserted(employeeList.size() - 1);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
