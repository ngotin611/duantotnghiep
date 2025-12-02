package com.example.duantn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duantn.models.FoodDomain;
import com.example.duantn.helper.ManagmentCart;

public class FoodDetailActivity extends AppCompatActivity {

    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, foodPic, backBtn;
    private Button addToCartBtn;

    private FoodDomain foodDomain;
    private int numberOrder = 1;
    private ManagmentCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        managementCart = new ManagmentCart(this);

        initView();
        getBundle();
        setupListeners();
    }

    private void initView() {
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.feeTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        foodPic = findViewById(R.id.foodPic);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        backBtn = findViewById(R.id.backBtn);
    }

    private void getBundle() {
        foodDomain = (FoodDomain) getIntent().getSerializableExtra("object");

        if (foodDomain != null) {
            titleTxt.setText(foodDomain.getTitle());
            feeTxt.setText(String.format("%.0fđ", foodDomain.getFee()));
            descriptionTxt.setText(foodDomain.getDescription());
            numberOrderTxt.setText(String.valueOf(numberOrder));

            int imageResId = getResources().getIdentifier(foodDomain.getPic(), "drawable", getPackageName());
            if (imageResId != 0) {
                foodPic.setImageResource(imageResId);
            }
        }
    }

    private void setupListeners() {
        plusBtn.setOnClickListener(v -> {
            numberOrder++;
            numberOrderTxt.setText(String.valueOf(numberOrder));
        });

        minusBtn.setOnClickListener(v -> {
            if (numberOrder > 1) {
                numberOrder--;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCartBtn.setOnClickListener(v -> {
            if (foodDomain != null) {
                foodDomain.setNumberInCart(numberOrder);
                managementCart.insertFood(foodDomain);
                Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(v -> finish());
    }
}
