package com.example.banhmiviet.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    public interface OnDeleteClickListener {
        void onDelete(int position);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Employee emp);
    }

    private final Context context;
    private final List<Employee> employeeList;
    private final OnDeleteClickListener deleteListener;
    private final OnItemClickListener itemClickListener;

    public EmployeeAdapter(Context context,
                           List<Employee> employeeList,
                           OnDeleteClickListener deleteListener,
                           OnItemClickListener itemClickListener) {
        this.context = context;
        this.employeeList = employeeList;
        this.deleteListener = deleteListener;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee emp = employeeList.get(position);

        holder.txtName.setText(emp.getName());
        holder.txtRole.setText(emp.getRole());
        holder.txtPhone.setText(emp.getPhone());
        holder.txtUsername.setText("TK: " + (emp.getUsername() == null ? "" : emp.getUsername()));
        holder.txtAgeGender.setText(emp.getAge() + " tuổi - " + emp.getGender());

        // 🔹 Avatar theo giới tính
        if (emp.getGender() != null &&
                emp.getGender().equalsIgnoreCase("Nam")) {
            holder.imgAvatar.setImageResource(R.drawable.ic_user_male);
        } else if (emp.getGender() != null &&
                emp.getGender().equalsIgnoreCase("Nữ")) {
            holder.imgAvatar.setImageResource(R.drawable.ic_user_female);
        } else {
            // fallback nếu không rõ giới tính
            holder.imgAvatar.setImageResource(R.drawable.ic_user_male);
        }

        holder.imgDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(holder.getAdapterPosition(), emp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRole, txtPhone, txtAgeGender, txtUsername;
        ImageView imgDelete, imgAvatar;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName      = itemView.findViewById(R.id.txtName);
            txtRole      = itemView.findViewById(R.id.txtRole);
            txtPhone     = itemView.findViewById(R.id.txtPhone);
            txtUsername  = itemView.findViewById(R.id.txtUsername);
            txtAgeGender = itemView.findViewById(R.id.txtAgeGender);
            imgDelete    = itemView.findViewById(R.id.imgDelete);
            imgAvatar    = itemView.findViewById(R.id.imgAvatar);
        }
    }
}
