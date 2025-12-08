package com.example.banhmiviet.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    // callback cho Fragment
    public interface OnCartChangeListener {
        void onCartChanged(List<Map.Entry<Product, Integer>> newEntries);
    }

    private final List<Map.Entry<Product, Integer>> entries;
    private final OnCartChangeListener listener;
    private final NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

    public CartItemAdapter(List<Map.Entry<Product, Integer>> entries,
                           OnCartChangeListener listener) {
        this.entries = entries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_product, parent, false);   // layout item giỏ hàng
        return new CartItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        Map.Entry<Product, Integer> entry = entries.get(position);
        Product p = entry.getKey();
        int qty   = entry.getValue();

        holder.tvName.setText(p.getName());
        holder.tvQty.setText(String.valueOf(qty));

        long lineTotal = (long) (qty * p.getPrice());
        holder.tvLineTotal.setText(vnFormat.format(lineTotal) + " đ");

        holder.btnPlus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            Map.Entry<Product, Integer> e = entries.get(pos);
            int newQty = e.getValue() + 1;
            e.setValue(newQty);
            notifyItemChanged(pos);

            if (listener != null) listener.onCartChanged(entries);
        });

        holder.btnMinus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            Map.Entry<Product, Integer> e = entries.get(pos);
            int current = e.getValue();
            if (current <= 0) return;

            int newQty = current - 1;
            e.setValue(newQty);
            notifyItemChanged(pos);

            if (listener != null) listener.onCartChanged(entries);
        });
    }

    @Override
    public int getItemCount() {
        return entries == null ? 0 : entries.size();
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQty, tvLineTotal;
        ImageButton btnPlus, btnMinus;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName      = itemView.findViewById(R.id.tvCartItemName);
            tvQty       = itemView.findViewById(R.id.tvCartQty);
            tvLineTotal = itemView.findViewById(R.id.tvCartLineTotal);
            btnPlus     = itemView.findViewById(R.id.btnPlus);
            btnMinus    = itemView.findViewById(R.id.btnMinus);
        }
    }
}
