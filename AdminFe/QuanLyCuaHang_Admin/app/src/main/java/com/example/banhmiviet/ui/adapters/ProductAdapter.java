package com.example.banhmiviet.ui.adapters;

import android.content.Context;
import android.net.Uri;
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
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnDeleteClickListener {
        void onDelete(int position);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Product product);
    }

    private final Context context;
    private final List<Product> productList;
    private final OnDeleteClickListener deleteListener;
    private final OnItemClickListener itemClickListener;

    private final NumberFormat vnFormat =
            NumberFormat.getInstance(new Locale("vi", "VN"));

    public ProductAdapter(Context context,
                          List<Product> productList,
                          OnDeleteClickListener deleteListener,
                          OnItemClickListener itemClickListener) {
        this.context = context;
        this.productList = productList;
        this.deleteListener = deleteListener;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = productList.get(position);

        holder.txtName.setText(p.getName());
        holder.txtCategory.setText("Loại: " + (p.getCategory() == null ? "" : p.getCategory()));
        holder.txtPrice.setText(vnFormat.format((long) p.getPrice()) + " đ");
        holder.txtDesc.setText(p.getDescription());

        // Ảnh: ưu tiên imageUri, không có thì dùng imageResId, cuối cùng là default
        if (p.getImageUri() != null && !p.getImageUri().isEmpty()) {
            holder.imgProduct.setImageURI(Uri.parse(p.getImageUri()));
        } else if (p.getImageResId() != 0) {
            holder.imgProduct.setImageResource(p.getImageResId());
        } else {
            holder.imgProduct.setImageResource(R.drawable.ic_launcher_foreground);
        }

        holder.imgDelete.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onDelete(holder.getAdapterPosition());
        });

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null)
                itemClickListener.onItemClick(holder.getAdapterPosition(), p);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, imgDelete;
        TextView txtName, txtCategory, txtPrice, txtDesc;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            imgDelete  = itemView.findViewById(R.id.imgDeleteProduct);
            txtName    = itemView.findViewById(R.id.txtProductName);
            txtCategory= itemView.findViewById(R.id.txtProductCategory);
            txtPrice   = itemView.findViewById(R.id.txtProductPrice);
            txtDesc    = itemView.findViewById(R.id.txtProductDesc);
        }
    }
}
