package com.example.duantn;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.adapter.FoodListAdapter;
import com.example.duantn.models.FoodDomain;

import java.util.ArrayList;

public class ListFoodActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<FoodDomain> foodList;
    private FoodListAdapter foodAdapter;
    private Button btnBanhMi, btnHamburger, btnPizza, btnNuocUong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        recyclerView = findViewById(R.id.recyclerViewFood);
        btnBanhMi = findViewById(R.id.btnBanhMi);
        btnHamburger = findViewById(R.id.btnHamburger);
        btnPizza = findViewById(R.id.btnPizza);
        btnNuocUong = findViewById(R.id.btnNuocUong);

        foodList = new ArrayList<>();

        // ✅ Dữ liệu mẫu kèm category
        foodList.add(new FoodDomain("Hamburger Bò", "anh_10", "Bánh hamburger bò thơm ngon", 45000, "Hamburger"));
        foodList.add(new FoodDomain("Hamburger Gà", "anh_12", "Bánh hamburger gà giòn rụm", 40000, "Hamburger"));
        foodList.add(new FoodDomain("Bánh Mì Trứng", "anh_13", "Bánh mì trứng béo ngậy", 20000, "Bánh mì"));
        foodList.add(new FoodDomain("Bánh Mì Chả", "anh_14", "Bánh mì chả Huế thơm ngon", 25000, "Bánh mì"));
        foodList.add(new FoodDomain("Bánh Mì Thịt", "anh_15", "Bánh mì thịt đặc biệt", 30000, "Bánh mì"));
        foodList.add(new FoodDomain("Pizza Hải Sản", "anh_16", "Pizza tươi ngon", 50000, "Pizza"));
        foodList.add(new FoodDomain("Pepsi", "anh_17", "Nước giải khát mát lạnh", 10000, "Nước uống"));

        foodAdapter = new FoodListAdapter(this, foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodAdapter);

        // ✅ Sự kiện lọc
        btnBanhMi.setOnClickListener(v -> foodAdapter.filterByCategory("Bánh mì"));
        btnHamburger.setOnClickListener(v -> foodAdapter.filterByCategory("Hamburger"));
        btnPizza.setOnClickListener(v -> foodAdapter.filterByCategory("Pizza"));
        btnNuocUong.setOnClickListener(v -> foodAdapter.filterByCategory("Nước uống"));
    }
}
