package com.example.banhmiviet.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Order;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    private final Context context;
    private final List<Order> orders;
    private final OnOrderClickListener listener;

    private final NumberFormat vnFormat =
            NumberFormat.getInstance(new Locale("vi", "VN"));
    private final SimpleDateFormat timeFormat =
            new SimpleDateFormat("HH:mm", Locale.getDefault());

    public OrderListAdapter(Context context, List<Order> orders, OnOrderClickListener listener) {
        this.context = context;
        this.orders = orders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order o = orders.get(position);

        String typeLabel = Order.TYPE_AT_TABLE.equals(o.getType())
                ? ("Tại bàn " + o.getTableNumber())
                : "Mang đi";

        holder.txtOrderTitle.setText("#" + o.getId() + " - " + typeLabel);
        holder.txtOrderCreator.setText("Người order: " + o.getCreatedBy());

        String amount = "Giá: " + vnFormat.format((long) o.getTotalPrice()) + " đ";
        holder.txtOrderAmount.setText(amount);

        long diffMs = System.currentTimeMillis() - o.getCreatedTime();
        long minutes = diffMs / (60 * 1000);
        long hours = minutes / 60;
        long remainMin = minutes % 60;

        String timeLabel = timeFormat.format(o.getCreatedTime()) +
                " - " + hours + " giờ " +
                String.format(Locale.getDefault(), "%02d", remainMin) + " phút trước";
        holder.txtOrderTime.setText(timeLabel);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onOrderClick(o);
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderTitle, txtOrderCreator, txtOrderAmount, txtOrderTime;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderTitle   = itemView.findViewById(R.id.txtOrderTitle);
            txtOrderCreator = itemView.findViewById(R.id.txtOrderCreator);
            txtOrderAmount  = itemView.findViewById(R.id.txtOrderAmount);
            txtOrderTime    = itemView.findViewById(R.id.txtOrderTime);
        }
    }
}
