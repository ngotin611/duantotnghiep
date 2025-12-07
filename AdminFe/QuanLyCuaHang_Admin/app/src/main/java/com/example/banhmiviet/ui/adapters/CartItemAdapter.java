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

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartViewHolder> {

    public interface OnCartChangeListener {
        void onCartChanged(List<Map.Entry<Product, Integer>> newEntries);
    }

    private final List<Map.Entry<Product, Integer>> cartEntries;
    private final OnCartChangeListener listener;
    private final NumberFormat vnFormat =
            NumberFormat.getInstance(new Locale("vi", "VN"));

    public CartItemAdapter(List<Map.Entry<Product, Integer>> cartEntries,
                           OnCartChangeListener listener) {
        this.cartEntries = cartEntries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Map.Entry<Product, Integer> entry = cartEntries.get(position);
        Product p = entry.getKey();
        int qty = entry.getValue();

        holder.tvName.setText(p.getName());
        holder.tvQty.setText(String.valueOf(qty));
        double lineTotal = qty * p.getPrice();
        holder.tvLineTotal.setText(vnFormat.format((long) lineTotal) + " đ");

        holder.btnMinus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;
            Map.Entry<Product, Integer> e = cartEntries.get(pos);
            int q = e.getValue();
            if (q > 1) {
                e.setValue(q - 1);
            } else {
                cartEntries.remove(pos);
                notifyItemRemoved(pos);
            }
            notifyItemChangedSafe(pos);
            notifyChanged();
        });

        holder.btnPlus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;
            Map.Entry<Product, Integer> e = cartEntries.get(pos);
            e.setValue(e.getValue() + 1);
            notifyItemChanged(pos);
            notifyChanged();
        });
    }

    private void notifyItemChangedSafe(int pos) {
        if (pos >= 0 && pos < cartEntries.size()) {
            notifyItemChanged(pos);
        }
    }

    private void notifyChanged() {
        if (listener != null) {
            listener.onCartChanged(cartEntries);
        }
    }

    @Override
    public int getItemCount() {
        return cartEntries.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQty, tvLineTotal;
        ImageButton btnMinus, btnPlus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName      = itemView.findViewById(R.id.tvCartName);
            tvQty       = itemView.findViewById(R.id.tvCartQty);
            tvLineTotal = itemView.findViewById(R.id.tvCartLineTotal);
            btnMinus    = itemView.findViewById(R.id.btnCartMinus);
            btnPlus     = itemView.findViewById(R.id.btnCartPlus);
        }
    }
}
