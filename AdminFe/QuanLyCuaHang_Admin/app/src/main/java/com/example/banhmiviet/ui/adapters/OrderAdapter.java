package com.example.banhmiviet.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtOrderDetails, txtOrderTotal;
        ImageButton btnDeleteOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtOrderDetails = itemView.findViewById(R.id.txtOrderDetails);
            txtOrderTotal = itemView.findViewById(R.id.txtOrderTotal);
            btnDeleteOrder = itemView.findViewById(R.id.btnDeleteOrder);
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtOrderId.setText("Đơn #" + order.getId());
        holder.txtOrderDetails.setText(order.getDescription());

        holder.txtOrderTotal.setText("Tổng: " + order.getTotalPrice() + " đ");

        holder.btnDeleteOrder.setOnClickListener(v -> {
            orderList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, orderList.size());
            Toast.makeText(v.getContext(), "Đã xoá đơn hàng #" + order.getId(), Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Chi tiết: " + order.getDescription(), Toast.LENGTH_LONG).show();

        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
