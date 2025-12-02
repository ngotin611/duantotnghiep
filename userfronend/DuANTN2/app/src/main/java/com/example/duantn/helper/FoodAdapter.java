package com.example.duantn.helper;

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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<FoodDomain> foodList;

    public FoodAdapter(ArrayList<FoodDomain> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDomain food = foodList.get(position);

        holder.titleText.setText(food.getTitle());
        holder.feeText.setText(String.format("%.0fđ", food.getFee()));

        // Load image
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
        return foodList.size();
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