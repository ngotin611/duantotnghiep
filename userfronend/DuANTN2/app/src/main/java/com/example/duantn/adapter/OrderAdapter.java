package com.example.duantn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.R;
import com.example.duantn.models.OrderDomain;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<OrderDomain> orders;
    private Context context;
    private OnOrderActionListener listener;

    public interface OnOrderActionListener {
        void onReorder(OrderDomain order);
        void onCancelOrder(OrderDomain order);
        void onViewDetails(OrderDomain order);
    }

    // Constructor đơn giản - chỉ cần orders
    public OrderAdapter(ArrayList<OrderDomain> orders) {
        this.orders = orders;
    }

    // Constructor đầy đủ - với context và listener
    public OrderAdapter(ArrayList<OrderDomain> orders, Context context, OnOrderActionListener listener) {
        this.orders = orders;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDomain order = orders.get(position);

        // Hiển thị thông tin đơn hàng
        holder.orderIdText.setText("Đơn hàng: " + order.getOrderId());
        holder.foodNameText.setText(order.getFoodName());
        holder.quantityText.setText("Số lượng: " + order.getQuantity());
        holder.priceText.setText(order.getFormattedTotalPrice());
        holder.statusText.setText(order.getStatus());
        holder.orderDateText.setText(order.getOrderDate());

        // Set màu trạng thái
        holder.statusText.setTextColor(order.getStatusColor());

        // Hiển thị/ẩn nút tùy theo trạng thái
        if (order.isDelivered()) {
            holder.reorderBtn.setVisibility(View.VISIBLE);
            holder.cancelBtn.setVisibility(View.GONE);
        } else if (order.isProcessing() || order.isDelivering()) {
            holder.reorderBtn.setVisibility(View.GONE);
            holder.cancelBtn.setVisibility(View.VISIBLE);
        } else {
            holder.reorderBtn.setVisibility(View.GONE);
            holder.cancelBtn.setVisibility(View.GONE);
        }

        // Set click listeners
        holder.reorderBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReorder(order);
            }
        });

        holder.cancelBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCancelOrder(order);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewDetails(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdText, foodNameText, quantityText, priceText, statusText, orderDateText;
        Button reorderBtn, cancelBtn;
        ImageView foodImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderIdText = itemView.findViewById(R.id.order_id_text);
            foodNameText = itemView.findViewById(R.id.food_name_text);
            quantityText = itemView.findViewById(R.id.quantity_text);
            priceText = itemView.findViewById(R.id.price_text);
            statusText = itemView.findViewById(R.id.status_text);
            orderDateText = itemView.findViewById(R.id.order_date_text);
            reorderBtn = itemView.findViewById(R.id.reorder_btn);
            cancelBtn = itemView.findViewById(R.id.cancel_btn);
            foodImage = itemView.findViewById(R.id.food_image);
        }
    }
}