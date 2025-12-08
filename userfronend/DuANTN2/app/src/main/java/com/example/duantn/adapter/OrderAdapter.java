package com.example.duantn.adapter;

import android.app.AlertDialog;
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

    public interface OnOrderActionListener {
        void onViewDetails(OrderDomain order);
    }

    private OnOrderActionListener listener;

    public OrderAdapter(ArrayList<OrderDomain> orders) {
        this.orders = orders;
    }

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

        holder.orderIdText.setText("Đơn hàng: " + order.getOrderId());
        holder.foodNameText.setText(order.getFoodName());
        holder.quantityText.setText("Số lượng: " + order.getQuantity());
        holder.priceText.setText(order.getFormattedTotalPrice());
        holder.statusText.setText("Trạng thái: " + order.getStatus());
        holder.statusText.setTextColor(order.getStatusColor());
        holder.orderDateText.setText(order.getOrderDate());

        String tableInfo = (order.getTableNumber() == null || order.getTableNumber().isEmpty())
                ? "Bàn: Chưa chọn"
                : "Bàn: " + order.getTableNumber();
        if (order.getOrderType() != null && !order.getOrderType().isEmpty()) {
            tableInfo += " · " + order.getOrderType();
        }
        holder.tableText.setText(tableInfo);

        if (order.getNote() != null && !order.getNote().isEmpty()) {
            holder.noteText.setVisibility(View.VISIBLE);
            holder.noteText.setText("Ghi chú: " + order.getNote());
        } else {
            holder.noteText.setVisibility(View.GONE);
        }

        // Nút cập nhật trạng thái
        holder.reorderBtn.setText("Cập nhật trạng thái");
        holder.reorderBtn.setVisibility(View.VISIBLE);
        holder.reorderBtn.setOnClickListener(v -> showStatusDialog(order, holder.getAdapterPosition()));

        // Nút hủy đơn: chỉ hiện nếu chưa hoàn thành / chưa hủy
        if (!order.isCompleted() && !order.isCancelled()) {
            holder.cancelBtn.setVisibility(View.VISIBLE);
            holder.cancelBtn.setOnClickListener(v -> {
                order.setStatus("Đã hủy");
                notifyItemChanged(holder.getAdapterPosition());
            });
        } else {
            holder.cancelBtn.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewDetails(order);
            }
        });
    }

    private void showStatusDialog(OrderDomain order, int position) {
        if (context == null) return;

        String[] options = new String[]{
                "Đang chuẩn bị",
                "Đang phục vụ",
                "Hoàn thành",
                "Đã hủy"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cập nhật trạng thái");
        builder.setItems(options, (dialog, which) -> {
            order.setStatus(options[which]);
            notifyItemChanged(position);
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdText, foodNameText, quantityText, priceText,
                statusText, orderDateText, tableText, noteText;
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
            tableText = itemView.findViewById(R.id.table_text);
            noteText = itemView.findViewById(R.id.note_text);
            reorderBtn = itemView.findViewById(R.id.reorder_btn);
            cancelBtn = itemView.findViewById(R.id.cancel_btn);
            foodImage = itemView.findViewById(R.id.food_image);
        }
    }
}