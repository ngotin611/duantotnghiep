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
import com.example.duantn.models.FoodDomain;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<FoodDomain> items;
    private Context context;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<FoodDomain> items, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.items = items;
        this.context = context;
        this.changeNumberItemsListener = changeNumberItemsListener;
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

        // Load ảnh từ drawable
        int imageId = context.getResources().getIdentifier(item.getPic(), "drawable", context.getPackageName());
        if (imageId != 0) {
            Picasso.get().load(imageId).into(holder.imgFood);
        }

        holder.btnPlus.setOnClickListener(v -> {
            item.setNumberInCart(item.getNumberInCart() + 1);
            notifyItemChanged(position);
            changeNumberItemsListener.changed();
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getNumberInCart() > 1) {
                item.setNumberInCart(item.getNumberInCart() - 1);
                notifyItemChanged(position);
                changeNumberItemsListener.changed();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtFeeEachItem, txtTotalEachItem, txtNumberInCart;
        ImageView imgFood, btnPlus, btnMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtFeeEachItem = itemView.findViewById(R.id.txtFeeEachItem);
            txtTotalEachItem = itemView.findViewById(R.id.txtTotalEachItem);
            txtNumberInCart = itemView.findViewById(R.id.txtNumberInCart);
            imgFood = itemView.findViewById(R.id.imgFood);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}
