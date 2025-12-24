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
import com.example.duantn.helper.ChangeNumberItemsListener;
import com.example.duantn.helper.ManagmentCart;
import com.example.duantn.models.FoodDomain;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<FoodDomain> items;
    private Context context;
    private ChangeNumberItemsListener changeNumberItemsListener;
    private ManagmentCart managmentCart;

    public CartAdapter(ArrayList<FoodDomain> items, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.items = items;
        this.context = context;
        this.changeNumberItemsListener = changeNumberItemsListener;
        this.managmentCart = new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        FoodDomain item = items.get(position);

        // Định dạng tiền đúng: 20.000đ
        NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));
        String feeFormatted = format.format(item.getFee()) + "đ";
        String totalFormatted = format.format(item.getFee() * item.getNumberInCart()) + "đ";

        holder.txtTitle.setText(item.getTitle());
        holder.txtFeeEachItem.setText(feeFormatted);
        holder.txtNumberInCart.setText(String.valueOf(item.getNumberInCart()));
        holder.txtTotalEachItem.setText(totalFormatted);

        // Load ảnh - hỗ trợ cả URL và resource drawable
        String imagePath = item.getPic();
        if (imagePath != null && !imagePath.isEmpty()) {
            // Nếu là URL từ backend
            if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                Picasso.get()
                        .load(imagePath)
                        .placeholder(R.drawable.ic_launcher_bg)
                        .error(R.drawable.ic_launcher_bg)
                        .into(holder.imgFood);
            } else {
                // Nếu là tên resource drawable (ví dụ: "anh_1")
                try {
                    int imageId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
                    if (imageId != 0) {
                        Picasso.get().load(imageId).into(holder.imgFood);
                    } else {
                        holder.imgFood.setImageResource(R.drawable.ic_launcher_bg);
                    }
                } catch (Exception e) {
                    holder.imgFood.setImageResource(R.drawable.ic_launcher_bg);
                }
            }
        } else {
            holder.imgFood.setImageResource(R.drawable.ic_launcher_bg);
        }

        holder.btnPlus.setOnClickListener(v -> {
            item.setNumberInCart(item.getNumberInCart() + 1);
            // Lưu lại vào giỏ hàng
            managmentCart.insertFood(item);
            notifyItemChanged(position);
            changeNumberItemsListener.changed();
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getNumberInCart() > 1) {
                item.setNumberInCart(item.getNumberInCart() - 1);
                // Lưu lại vào giỏ hàng
                managmentCart.insertFood(item);
                notifyItemChanged(position);
                changeNumberItemsListener.changed();
            }
        });

        // Nút xóa món
        holder.btnDelete.setOnClickListener(v -> {
            // Xóa món khỏi danh sách
            items.remove(position);
            // Lưu lại giỏ hàng đã cập nhật
            managmentCart.clearCart();
            for (FoodDomain food : items) {
                managmentCart.insertFood(food);
            }
            // Cập nhật UI
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());
            changeNumberItemsListener.changed();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtFeeEachItem, txtTotalEachItem, txtNumberInCart;
        ImageView imgFood, btnPlus, btnMinus, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtFeeEachItem = itemView.findViewById(R.id.txtFeeEachItem);
            txtTotalEachItem = itemView.findViewById(R.id.txtTotalEachItem);
            txtNumberInCart = itemView.findViewById(R.id.txtNumberInCart);
            imgFood = itemView.findViewById(R.id.imgFood);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
