package com.example.banhmiviet.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public interface OnOrderActionListener {
        void onViewDetail(Order order);
        void onPay(Order order);
        void onCancel(Order order);
        void onPrint(Order order);
    }

    private final List<Order> orderList;
    private final OnOrderActionListener listener;

    public OrderAdapter(List<Order> orderList, OnOrderActionListener listener) {
        this.orderList = (orderList != null) ? orderList : new ArrayList<>();
        this.listener = listener;
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
        if (order == null) return;

        // ========== Tiêu đề: #ID - loại ==========
        String typeText;
        if (Order.TYPE_AT_TABLE.equals(order.getType())) {
            if (order.getTableNumber() != null) {
                typeText = "Tại bàn " + order.getTableNumber();
            } else {
                typeText = "Tại bàn";
            }
        } else if (Order.TYPE_TAKE_AWAY.equals(order.getType())) {
            typeText = "Mang đi";
        } else {
            typeText = "";
        }
        holder.txtOrderTitle.setText("#" + order.getId() + " - " + typeText);

        // ========== Số món từ description ==========
        String desc = order.getDescription() == null ? "" : order.getDescription();
        int itemCount = 0;
        if (!desc.isEmpty()) {
            String[] parts = desc.split(",");
            for (String p : parts) {
                if (!p.trim().isEmpty()) itemCount++;
            }
        }
        holder.txtOrderAmount.setText(itemCount + " món");

        // ========== Tổng tiền ==========
        NumberFormat vn = NumberFormat.getInstance(new Locale("vi", "VN"));
        holder.txtOrderMoney.setText(vn.format(order.getTotalPrice()) + " đ");

        // ========== Thời gian tạo "X phút trước" ==========
        holder.txtOrderTime.setText("Đã tạo: " + getTimeAgo(order.getCreatedTime()));

        // ========== Trạng thái + màu ==========
        String status = order.getStatus();
        int statusColor = 0xFFFF8C00; // mặc định cam

        if (Order.STATUS_NEW.equals(status)) {
            status = "Chưa thanh toán";
            statusColor = 0xFFFF8C00;
        } else if (Order.STATUS_PAID.equals(status)) {
            status = "Đã thanh toán";
            statusColor = 0xFF4CAF50;
        } else if (Order.STATUS_CANCELLED.equals(status)) {
            status = "Đã hủy";
            statusColor = 0xFF9E9E9E;
        }

        holder.txtOrderStatus.setText(status);
        holder.txtOrderStatus.setTextColor(statusColor);

        // ========== Click cả card -> xem chi tiết ==========
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onViewDetail(order);
        });

        // ========== Nút tất toán ==========
        holder.btnPay.setOnClickListener(v -> {
            if (listener != null) listener.onPay(order);
        });

        // ========== Nút hủy ==========
        holder.btnCancel.setOnClickListener(v -> {
            if (listener != null) listener.onCancel(order);
        });

        // Gợi ý: nếu sau này muốn in nhanh trên item:
        // holder.btnPrint.setOnClickListener(v -> { if (listener != null) listener.onPrint(order); });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateList(List<Order> newList) {
        orderList.clear();
        if (newList != null) orderList.addAll(newList);
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView txtOrderTitle, txtOrderAmount, txtOrderMoney, txtOrderTime, txtOrderStatus;
        Button btnPay, btnCancel;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderTitle  = itemView.findViewById(R.id.txtOrderTitle);
            txtOrderAmount = itemView.findViewById(R.id.txtOrderAmount);
            txtOrderMoney  = itemView.findViewById(R.id.txtOrderMoney);
            txtOrderTime   = itemView.findViewById(R.id.txtOrderTime);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            btnPay         = itemView.findViewById(R.id.btnPay);
            btnCancel      = itemView.findViewById(R.id.btnCancel);
        }
    }

    // ================== Helper: "X phút trước" ==================
    private String getTimeAgo(long createdTime) {
        if (createdTime <= 0) return "không rõ";

        long now = System.currentTimeMillis();
        long diff = now - createdTime;
        if (diff < 0) diff = 0;

        long minutes = diff / 60000;
        long hours = minutes / 60;
        long remainMin = minutes % 60;

        if (minutes < 1) return "vừa xong";
        if (minutes < 60) return minutes + " phút trước";
        if (remainMin == 0) return hours + " giờ trước";
        return hours + " giờ " + remainMin + " phút trước";
    }
}
