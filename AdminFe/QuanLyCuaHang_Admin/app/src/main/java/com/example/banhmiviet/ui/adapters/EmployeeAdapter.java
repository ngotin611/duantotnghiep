package com.example.banhmiviet.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private Context context;
    private ArrayList<Employee> employeeList;
    private OnEmployeeActionListener listener;

    public interface OnEmployeeActionListener {
        void onDelete(int position);
    }

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeList, OnEmployeeActionListener listener) {
        this.context = context;
        this.employeeList = employeeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee emp = employeeList.get(position);
        holder.txtName.setText(emp.getName());
        holder.txtEmail.setText(emp.getEmail());
        holder.txtPhone.setText(emp.getPhone());
        holder.txtAgeGender.setText(emp.getAge() + " tuổi - " + emp.getGender());

        holder.btnDelete.setOnClickListener(v -> listener.onDelete(position));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail, txtPhone, txtAgeGender;
        ImageButton btnDelete;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtAgeGender = itemView.findViewById(R.id.txtAgeGender);
            btnDelete = itemView.findViewById(R.id.btnDeleteEmployee);
        }
    }
}
