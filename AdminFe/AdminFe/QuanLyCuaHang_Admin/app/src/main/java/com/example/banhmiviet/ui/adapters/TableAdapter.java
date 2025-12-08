package com.example.banhmiviet.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.data.DataRepository;
import com.example.banhmiviet.model.Order;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    public interface OnTableClickListener {
        void onTableClick(int tableNumber, Order currentOrder);
    }

    private static final int DEFAULT_TABLE_COUNT = 50;

    private final int tableCount;
    private final OnTableClickListener listener;
    private final DataRepository repo;
    private final NumberFormat currencyFormat;

    public TableAdapter(OnTableClickListener listener) {
        this(DEFAULT_TABLE_COUNT, listener);
    }

    public TableAdapter(int tableCount, OnTableClickListener listener) {
        this.tableCount = tableCount;
        this.listener = listener;
        this.repo = DataRepository.getInstance();
        this.currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        int tableNumber = position + 1;
        holder.txtTableName.setText("Bàn " + tableNumber);

        // tìm đơn đang hoạt động tại bàn này
        Order activeOrder = findActiveOrderForTable(tableNumber);

        if (activeOrder != null) {
            // có khách
            holder.layoutRoot.setBackgroundResource(R.drawable.bg_table_active);
            String money = currencyFormat.format((long) activeOrder.getTotalPrice()) + " đ";
            holder.txtTableStatus.setText("Đang phục vụ\n" + money);
            holder.txtTableStatus.setTextColor(holder.itemView.getResources()
                    .getColor(android.R.color.white));
            holder.txtTableName.setTextColor(holder.itemView.getResources()
                    .getColor(android.R.color.white));
        } else {
            // bàn trống
            holder.layoutRoot.setBackgroundResource(R.drawable.bg_table_empty);
            holder.txtTableStatus.setText("Trống");
            holder.txtTableStatus.setTextColor(holder.itemView.getResources()
                    .getColor(android.R.color.darker_gray));
            holder.txtTableName.setTextColor(holder.itemView.getResources()
                    .getColor(android.R.color.black));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTableClick(tableNumber, activeOrder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableCount;
    }

    private Order findActiveOrderForTable(int tableNumber) {
        List<Order> list = repo.getOrders();
        for (Order o : list) {
            if (Order.TYPE_AT_TABLE.equals(o.getType())
                    && o.getTableNumber() != null
                    && o.getTableNumber() == tableNumber
                    && !Order.STATUS_PAID.equals(o.getStatus())
                    && !Order.STATUS_CANCELLED.equals(o.getStatus())) {
                return o;
            }
        }
        return null;
    }

    static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView txtTableName, txtTableStatus;
        LinearLayout layoutRoot;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTableName = itemView.findViewById(R.id.txtTableName);
            txtTableStatus = itemView.findViewById(R.id.txtTableStatus);
            layoutRoot = itemView.findViewById(R.id.layoutTableRoot);
        }
    }
}
