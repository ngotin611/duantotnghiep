package com.example.duantn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.R;
import com.example.duantn.models.OrderDomain;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {

    private ArrayList<OrderDomain> orders;

    public PaymentHistoryAdapter(ArrayList<OrderDomain> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDomain order = orders.get(position);
        
        DecimalFormat formatter = new DecimalFormat("#,###");
        
        holder.orderIdText.setText("Đơn: " + order.getOrderId());
        holder.foodNameText.setText(order.getFoodName());
        holder.quantityText.setText("Số lượng: " + order.getQuantity());
        holder.priceText.setText(formatter.format(order.getPrice()) + "₫");
        holder.dateText.setText(order.getOrderDate());
        
        // Hiển thị thông tin bàn
        String tableInfo = "";
        if (order.getTableNumber() != null && !order.getTableNumber().isEmpty()) {
            tableInfo = "Bàn: " + order.getTableNumber();
            if (order.getOrderType() != null && !order.getOrderType().isEmpty()) {
                tableInfo += " - " + order.getOrderType();
            }
        } else {
            tableInfo = "Mang đi";
        }
        holder.tableText.setText(tableInfo);
        
        // Hiển thị phương thức thanh toán
        if (order.getPaymentMethod() != null && !order.getPaymentMethod().isEmpty()) {
            holder.paymentMethodText.setText("Thanh toán: " + order.getPaymentMethod());
        } else {
            holder.paymentMethodText.setText("Thanh toán: Tiền mặt");
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdText, foodNameText, quantityText, priceText, dateText, tableText, paymentMethodText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdText = itemView.findViewById(R.id.order_id_text);
            foodNameText = itemView.findViewById(R.id.food_name_text);
            quantityText = itemView.findViewById(R.id.quantity_text);
            priceText = itemView.findViewById(R.id.price_text);
            dateText = itemView.findViewById(R.id.date_text);
            tableText = itemView.findViewById(R.id.table_text);
            paymentMethodText = itemView.findViewById(R.id.payment_method_text);
        }
    }
}



