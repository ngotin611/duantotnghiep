package com.example.duantn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.R;
import com.example.duantn.models.product.ResponseProduct;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private List<ResponseProduct> productList;

    public ProductAdapter(Context context, List<ResponseProduct> productList) {
        this.context = context;
        this.productList = productList;
    }

    public void setProductList(List<ResponseProduct> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng layout item_food.xml có sẵn
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ResponseProduct product = productList.get(position);
        
        // Set tên sản phẩm
        holder.tvName.setText(product.getName() != null ? product.getName() : "");
        
        // Set giá với format đẹp
        if (product.getPrice() != null) {
            BigDecimal price = product.getPrice();
            NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
            holder.tvPrice.setText(formatter.format(price.doubleValue()) + " đ");
        } else {
            holder.tvPrice.setText("0 đ");
        }
        
        // Load image từ URL bằng Picasso
        if (product.getImgProduct() != null && !product.getImgProduct().isEmpty()) {
            // Nếu là URL từ backend
            if (product.getImgProduct().startsWith("http://") || product.getImgProduct().startsWith("https://")) {
                Picasso.get()
                        .load(product.getImgProduct())
                        .placeholder(R.drawable.ic_launcher_bg) // Ảnh placeholder khi đang load
                        .error(R.drawable.ic_launcher_bg) // Ảnh hiển thị khi lỗi
                        .into(holder.ivProduct);
            } else {
                // Nếu là tên resource drawable (ví dụ: "anh_1")
                try {
                    int imageResId = context.getResources().getIdentifier(
                            product.getImgProduct(), 
                            "drawable", 
                            context.getPackageName()
                    );
                    if (imageResId != 0) {
                        holder.ivProduct.setImageResource(imageResId);
                    } else {
                        holder.ivProduct.setImageResource(R.drawable.ic_launcher_bg);
                    }
                } catch (Exception e) {
                    holder.ivProduct.setImageResource(R.drawable.ic_launcher_bg);
                }
            }
        } else {
            // Không có ảnh, dùng placeholder
            holder.ivProduct.setImageResource(R.drawable.ic_launcher_bg);
        }
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice; // Dùng foodTitle và foodPrice từ layout
        ImageView ivProduct; // Dùng foodImage từ layout

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Map với các ID có trong item_food.xml
            tvName = itemView.findViewById(R.id.foodTitle);
            tvPrice = itemView.findViewById(R.id.foodPrice);
            ivProduct = itemView.findViewById(R.id.foodImage);
        }
    }

}
