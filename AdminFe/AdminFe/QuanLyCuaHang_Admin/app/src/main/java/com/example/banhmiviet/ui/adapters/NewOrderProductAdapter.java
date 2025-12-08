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

public class NewOrderProductAdapter extends RecyclerView.Adapter<NewOrderProductAdapter.ProductHolder> {

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    private List<Product> products;
    private final OnProductClickListener listener;

    private final NumberFormat vnFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

    public NewOrderProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_order_product, parent, false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder h, int position) {
        Product p = products.get(position);

        h.tvName.setText(p.getName());
        h.tvPrice.setText(vnFormat.format((long) p.getPrice()) + " đ");

        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(p);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateList(List<Product> newList) {
        this.products = newList;
        notifyDataSetChanged();
    }

    static class ProductHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageButton btnPlus;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            // nếu cần xử lý tăng số lượng, thêm ID này trong layout
        }
    }
}
