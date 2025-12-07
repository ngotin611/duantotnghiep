package com.example.banhmiviet.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NewOrderProductAdapter
        extends RecyclerView.Adapter<NewOrderProductAdapter.ProductViewHolder> {

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    private List<Product> displayList;
    private final OnProductClickListener listener;
    private final HashMap<Product, Integer> cart;
    private final NumberFormat vnFormat =
            NumberFormat.getInstance(new Locale("vi", "VN"));

    public NewOrderProductAdapter(List<Product> source,
                                  OnProductClickListener listener,
                                  HashMap<Product, Integer> cart) {
        this.displayList = new ArrayList<>(source);
        this.listener = listener;
        this.cart = cart;
    }

    public void updateList(List<Product> newList) {
        this.displayList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_order_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = displayList.get(position);

        holder.tvName.setText(p.getName());
        holder.tvPrice.setText(vnFormat.format((long) p.getPrice()) + " đ");
        holder.imgProduct.setImageResource(p.getImageResId());

        Integer qty = cart.get(p);
        if (qty != null && qty > 0) {
            holder.tvBadge.setVisibility(View.VISIBLE);
            holder.tvBadge.setText(String.valueOf(qty));
        } else {
            holder.tvBadge.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(p);
        });
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvPrice, tvBadge;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName     = itemView.findViewById(R.id.tvName);
            tvPrice    = itemView.findViewById(R.id.tvPrice);
            tvBadge    = itemView.findViewById(R.id.tvBadgeQty);
        }
    }
}
