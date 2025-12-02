package com.example.duantn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.FoodDetailActivity;
import com.example.duantn.R;
import com.example.duantn.models.FoodDomain;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {
    private final Context context;
    private final List<FoodDomain> originalList;
    private final List<FoodDomain> filteredList;

    public FoodListAdapter(Context context, List<FoodDomain> foodList) {
        this.context = context;
        this.originalList = foodList;
        this.filteredList = new ArrayList<>(foodList);
    }

    // ✅ Lọc danh sách theo category
    public void filterByCategory(String category) {
        filteredList.clear();
        for (FoodDomain food : originalList) {
            if (food.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(food);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDomain food = filteredList.get(position);

        holder.titleText.setText(food.getTitle());
        holder.feeText.setText(String.format("%.0fđ", food.getFee()));
        int imageResId = context.getResources().getIdentifier(food.getPic(), "drawable", context.getPackageName());
        holder.imageView.setImageResource(imageResId);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodDetailActivity.class);
            intent.putExtra("object", food);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleText, feeText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.foodImage);
            titleText = itemView.findViewById(R.id.foodTitle);
            feeText = itemView.findViewById(R.id.foodPrice);
        }
    }
}
